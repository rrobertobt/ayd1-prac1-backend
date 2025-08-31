package ayd.back.taller.service;

import ayd.back.taller.dto.request.orders.CreateOrderRequestDto;
import ayd.back.taller.dto.request.orders.OrderItemRequestDto;
import ayd.back.taller.dto.request.orders.UpdateDeliveryDateRequestDto;
import ayd.back.taller.dto.request.orders.UpdateOrderStatusRequestDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.SessionResponseDto;
import ayd.back.taller.dto.response.orders.OrderResponseDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.mappers.OrderResponseMapper;
import ayd.back.taller.repository.crud.*;
import ayd.back.taller.repository.entities.*;
import ayd.back.taller.repository.enums.PurchaseStatusEnum;
import ayd.back.taller.repository.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final PurchaseOrderRepository poRepo;
    private final PurchaseOrderItemRepository itemRepo;
    private final UserCrud userRepo;
    private final PartRepository partRepo;
    private final PartCatalogRepository catalogRepo;
    private final OrderResponseMapper mapper;
    private final SessionService sessionService;
    private final EmailService emailService;

    @Transactional
    public ResponseSuccessDto create(CreateOrderRequestDto dto, String token) {
        SessionResponseDto sessionDto = sessionService.validateSessionToken(token);

        if (!UserRoleEnum.ADMIN.equals(sessionDto.getRole())) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions");
        }

        if (dto.getSupplierId() == null) throw new BusinessException(HttpStatus.BAD_REQUEST, "supplierId is required");
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "At least one item is required");
        }

        UserEntity supplier = userRepo.findById(dto.getSupplierId())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Supplier not found: " + dto.getSupplierId()));

        // id del usuario dado pertenece a un proveedor?
        if (supplier.getRole() != UserRoleEnum.PROVEEDOR) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "User " + dto.getSupplierId() + " is not a supplier");
        }

        // Validar items y duplicados
        Set<Integer> partIds = new HashSet<>();
        for (OrderItemRequestDto it : dto.getItems()) {
            if (it.getPartId() == null) throw new BusinessException(HttpStatus.BAD_REQUEST, "partId is required");
            if (!partIds.add(it.getPartId())) throw new BusinessException(HttpStatus.BAD_REQUEST, "Duplicated partId: " + it.getPartId());
            if (it.getAmount() == null || it.getAmount() <= 0)
                throw new BusinessException(HttpStatus.BAD_REQUEST, "amount must be > 0, for partId " + it.getPartId());
        }

        // Encabezado
        PurchaseOrderEntity po = new PurchaseOrderEntity();
        po.setSupplier(supplier);
        po.setDescription(dto.getDescription());
        po.setOrderDate(LocalDateTime.now());
        po.setDeliveryDate(dto.getDeliveryDate().atStartOfDay());
        po.setCreatedAt(LocalDateTime.now());
        po.setUpdatedAt(LocalDateTime.now());
        po.setStatus(PurchaseStatusEnum.ORDENADO);
        po.setTotal(0.0);
        PurchaseOrderEntity saved = poRepo.save(po); //se guarda desde aquí para tener id después

        // Crear ítems + calcular total
        double total = 0.0;
        List<PurchaseOrderItemEntity> items = new ArrayList<>();

        for (OrderItemRequestDto it : dto.getItems()) {
            PartEntity part = partRepo.findById(it.getPartId())
                    .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Part not found: " + it.getPartId()));

            PartCatalogEntity catalog = catalogRepo.findBySupplierIdAndPartId(supplier.getId(), it.getPartId())
                    .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Part: " + it.getPartId() + " not found in the given supplier's catalog"));

            double subtotal = catalog.getPrice() * it.getAmount();
            total += subtotal;

            PurchaseOrderItemEntity item = new PurchaseOrderItemEntity();
            item.setId(new PurchaseOrderItemId(saved.getId(), part.getId()));
            item.setPurchaseOrder(saved);
            item.setPart(part);
            item.setUnitPrice(catalog.getPrice());
            item.setAmount(it.getAmount());
            item.setCreatedAt(LocalDateTime.now());
            item.setUpdatedAt(LocalDateTime.now());
            items.add(item);
        }

        // Redondear total a 2 decimales  para coincidir con numeric(14,2)
        total = Math.round(total * 100.0) / 100.0;
        saved.setTotal(total);
        saved.setUpdatedAt(LocalDateTime.now());
        itemRepo.saveAll(items);
        poRepo.save(saved);

        OrderResponseDto body = mapper.toOrderResponse(saved, items);
        return ResponseSuccessDto.builder()
                .code(HttpStatus.CREATED)
                .message("Purchase order created")
                .body(body)
                .build();
    }

    @Transactional(readOnly = true)
    public ResponseSuccessDto list(PurchaseStatusEnum status, String token) {
        SessionResponseDto sessionDto = sessionService.validateSessionToken(token);

        List<PurchaseOrderEntity> orders;

        if (UserRoleEnum.ADMIN.equals(sessionDto.getRole())) {
            orders = (status == null)
                    ? poRepo.findAll()
                    : poRepo.findByStatus(status);
        } else if (UserRoleEnum.PROVEEDOR.equals(sessionDto.getRole())) {
            UserEntity supplier = userRepo.getUserByEmail(sessionDto.getEmail())
                    .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Supplier not found"));

            orders = (status == null)
                    ? poRepo.findBySupplier(supplier)
                    : poRepo.findBySupplierAndStatus(supplier, status);
        } else {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions");
        }

        List<OrderResponseDto> out = new ArrayList<>();
        for (PurchaseOrderEntity po : orders) {
            List<PurchaseOrderItemEntity> items = itemRepo.findByPurchaseOrderId(po.getId());
            out.add(mapper.toOrderResponse(po, items));
        }

        return ResponseSuccessDto.builder()
                .code(HttpStatus.OK)
                .message("Purchase orders retrieved")
                .body(out)
                .build();
    }

    @Transactional
    public ResponseSuccessDto updateStatus(Integer id, UpdateOrderStatusRequestDto dto, String token) {
        SessionResponseDto sessionDto = sessionService.validateSessionToken(token);

        if (dto.getStatus() == null) throw new BusinessException(HttpStatus.BAD_REQUEST, "status is required");

        PurchaseOrderEntity po = poRepo.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Purchase order not found: " + id));

        if (sessionDto.getRole().equals(UserRoleEnum.ADMIN)) {
            if ( !(dto.getStatus().equals(PurchaseStatusEnum.RECIBIDO) || dto.getStatus().equals(PurchaseStatusEnum.CANCELADO)) ){  // solo puede cambiarlo a 'recibido' o 'cancelado'
                throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions to change status to " + dto.getStatus());
            }
        } else if (sessionDto.getRole().equals(UserRoleEnum.PROVEEDOR)) { //si es proveedor solo puede modificar estado de los pedidos que ha recibido
            UserEntity supplier = po.getSupplier();
            if (!(sessionDto.getEmail().equals(supplier.getEmail()) )){
                throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions to change status from " + po.getStatus());
            }
            if (!po.getStatus().equals(PurchaseStatusEnum.ORDENADO)) { // solo puede cambiarlo si el estado inicial es 'ordenado'
                throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions to change status from " + po.getStatus());
            }
            if ( !(dto.getStatus().equals(PurchaseStatusEnum.RECHAZADO) || dto.getStatus().equals(PurchaseStatusEnum.ENVIADO)) ){  // solo puede cambiarlo a 'enviado' o 'rechazado'
                throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions to change status to " + dto.getStatus());
            }
        } else {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions");
        }

        if (po.getStatus() == dto.getStatus()) {
            return ResponseSuccessDto.builder()
                    .code(HttpStatus.OK)
                    .message("No change in status")
                    .body(mapper.toOrderResponse(po, itemRepo.findByPurchaseOrderId(po.getId())))
                    .build();
        }

        po.setStatus(dto.getStatus());
        po.setUpdatedAt(LocalDateTime.now());
        poRepo.save(po);

        return ResponseSuccessDto.builder()
                .code(HttpStatus.OK)
                .message("Status updated")
                .body(mapper.toOrderResponse(po, itemRepo.findByPurchaseOrderId(po.getId())))
                .build();
    }

    @Transactional
    public ResponseSuccessDto updateDeliveryDate(Integer id, UpdateDeliveryDateRequestDto dto, String token) {
        SessionResponseDto sessionDto = sessionService.validateSessionToken(token);

        if ( !sessionDto.getRole().equals(UserRoleEnum.PROVEEDOR)) {
                throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions to change delivery date");
        }

        if (dto.getDeliveryDate() == null) throw new BusinessException(HttpStatus.BAD_REQUEST, "deliveryDate is required");

        PurchaseOrderEntity po = poRepo.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Purchase order not found: " + id));

        UserEntity supplier = po.getSupplier();
        if (!(sessionDto.getEmail().equals(supplier.getEmail()))) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "User does not have the necessary permissions to change this order's delivery date");
        }

        // No permitir fecha de entrega antes de la fecha de pedido
        LocalDateTime orderDay = po.getOrderDate() != null ? po.getOrderDate() : LocalDateTime.now();
        if (dto.getDeliveryDate().isBefore(orderDay.toLocalDate())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "deliveryDate cannot be before orderDate");
        }

        List<UserEntity> admins = userRepo.findByRole(UserRoleEnum.ADMIN.name());
        String message = "El proveedor " + supplier.getName() + " modificó la fecha de entrega del pedido on ID: " + po.getId()
                + " y descripción: " + po.getDescription() + "\n La nueva fecha de entrega es: " + po.getDeliveryDate();
        for (UserEntity user : admins) {
            log.info("notificación de cambio de fecha de entrega enviada a : {}", user.getEmail());
            emailService.sendEmail(user.getEmail(), "Se modificó la fecha de entrega de un pedido", message);
        }

        po.setDeliveryDate(dto.getDeliveryDate().atStartOfDay());
        po.setUpdatedAt(LocalDateTime.now());
        poRepo.save(po);


        return ResponseSuccessDto.builder()
                .code(HttpStatus.OK)
                .message("Delivery date updated")
                .body(mapper.toOrderResponse(po, itemRepo.findByPurchaseOrderId(po.getId())))
                .build();
    }

}

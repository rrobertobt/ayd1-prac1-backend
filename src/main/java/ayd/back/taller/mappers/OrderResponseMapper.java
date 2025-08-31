package ayd.back.taller.mappers;

import ayd.back.taller.dto.response.orders.OrderItemResponseDto;
import ayd.back.taller.dto.response.orders.OrderResponseDto;
import ayd.back.taller.repository.entities.PurchaseOrderEntity;
import ayd.back.taller.repository.entities.PurchaseOrderItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderResponseMapper {

    public OrderResponseDto toOrderResponse(PurchaseOrderEntity po, List<PurchaseOrderItemEntity> items) {
        return OrderResponseDto.builder()
                .id(po.getId())
                .supplier(OrderResponseDto.SupplierDto.builder()
                        .id(po.getSupplier() != null ? po.getSupplier().getId() : null)
                        .name(po.getSupplier() != null ? po.getSupplier().getName() : null)
                        .email(po.getSupplier() != null ? po.getSupplier().getEmail() : null)
                        .build())
                .status(po.getStatus())
                .description(po.getDescription())
                .total(po.getTotal())
                .orderDate(po.getOrderDate())
                .deliveryDate(po.getDeliveryDate().toLocalDate())
                .createdAt(po.getCreatedAt())
                .updatedAt(po.getUpdatedAt())
                .items(items.stream().map(this::toItemResponse).toList())
                .build();
    }

    private OrderItemResponseDto toItemResponse(PurchaseOrderItemEntity it) {
        return OrderItemResponseDto.builder()
                .partId(it.getPart().getId())
                .partCode(it.getPart().getCode())
                .partName(it.getPart().getName())
                .unitPrice(it.getUnitPrice())
                .amount(it.getAmount())
                .subtotal(Math.round((it.getUnitPrice() * it.getAmount()) * 100.0) / 100.0)
                .build();
    }
}

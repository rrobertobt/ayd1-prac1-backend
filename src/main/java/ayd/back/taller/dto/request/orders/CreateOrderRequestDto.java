package ayd.back.taller.dto.request.orders;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class CreateOrderRequestDto {
    private Integer supplierId;
    private String description;                 // opcional
    private LocalDate deliveryDate;         // opcional
    private List<OrderItemRequestDto> items;    // requerido (>=1)
}

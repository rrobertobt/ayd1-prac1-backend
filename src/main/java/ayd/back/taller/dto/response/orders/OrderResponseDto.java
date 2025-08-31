package ayd.back.taller.dto.response.orders;

import ayd.back.taller.repository.enums.PurchaseStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @Builder
public class OrderResponseDto {
    private Integer id;
    private SupplierDto supplier;

    private PurchaseStatusEnum status;
    private String description;
    private Double total;

    private LocalDateTime orderDate;
    private LocalDate deliveryDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<OrderItemResponseDto> items;

    @Getter @Setter @Builder
    public static class SupplierDto {
        private Integer id;
        private String name;
        private String email;
    }
}

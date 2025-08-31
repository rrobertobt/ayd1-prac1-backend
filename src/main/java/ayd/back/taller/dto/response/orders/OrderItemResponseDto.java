package ayd.back.taller.dto.response.orders;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class OrderItemResponseDto {
    private Integer partId;
    private String partCode;
    private String partName;
    private Double unitPrice;
    private Integer amount;
    private Double subtotal; // unitPrice * amount
}

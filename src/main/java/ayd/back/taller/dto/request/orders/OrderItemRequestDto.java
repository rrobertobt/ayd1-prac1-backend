package ayd.back.taller.dto.request.orders;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemRequestDto {
    private Integer partId;
    private Integer amount;    // > 0
}

package ayd.back.taller.dto.request.orders;

import ayd.back.taller.repository.enums.PurchaseStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateOrderStatusRequestDto {
    private PurchaseStatusEnum status;
}

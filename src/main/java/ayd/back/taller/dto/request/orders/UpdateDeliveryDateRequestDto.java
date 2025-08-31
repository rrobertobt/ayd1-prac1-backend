package ayd.back.taller.dto.request.orders;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class UpdateDeliveryDateRequestDto {
    private LocalDate deliveryDate;
}

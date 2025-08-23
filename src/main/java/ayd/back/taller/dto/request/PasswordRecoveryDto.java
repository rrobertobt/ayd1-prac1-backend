package ayd.back.taller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PasswordRecoveryDto {

    private String userEmail;

}

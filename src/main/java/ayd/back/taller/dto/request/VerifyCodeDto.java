package ayd.back.taller.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VerifyCodeDto {

    private String userEmail;

    private String code;

}

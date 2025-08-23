package ayd.back.taller.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogInDto {

    private String email;

    private String password;

}

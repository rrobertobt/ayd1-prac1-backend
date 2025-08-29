package ayd.back.taller.dto.response;

import ayd.back.taller.repository.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class SessionResponseDto {

    private String userEmail;

    private UserRoleEnum role;

    private Date expiredAt;


}

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

    private String email;

    private Integer nit;

    private String name;

    private String address;

    private String phoneNumber;

    private UserRoleEnum role;

    private Date expiredAt;


}

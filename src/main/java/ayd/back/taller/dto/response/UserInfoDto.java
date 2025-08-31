package ayd.back.taller.dto.response;

import ayd.back.taller.repository.enums.UserRoleEnum;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserInfoDto {

    private String email;

    private Integer nit;

    private String name;

    private String address;

    private String phoneNumber;

    private String sessionToken;

    private UserRoleEnum role;

}

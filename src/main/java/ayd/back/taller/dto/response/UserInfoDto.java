package ayd.back.taller.dto.response;

import ayd.back.taller.repository.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserInfoDto {

    private String email;

    private UserRoleEnum role;

}

package ayd.back.taller.dto.request;

import ayd.back.taller.repository.enums.TwofaMethodEnum;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewUserDto {

    private String role;

    private String email;

    private Integer nit;

    private String name;

    private String address;

    private String phoneNumber;

    private String password;

    private String twofaMethod;

}

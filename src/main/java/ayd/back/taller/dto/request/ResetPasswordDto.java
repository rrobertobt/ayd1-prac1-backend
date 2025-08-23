package ayd.back.taller.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {

    private String userEmail;

    private String newPassword;

    private String confirmPassword;
}

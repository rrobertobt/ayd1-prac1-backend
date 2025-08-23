package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.LogInDto;
import ayd.back.taller.dto.request.PasswordRecoveryDto;
import ayd.back.taller.dto.request.ResetPasswordDto;
import ayd.back.taller.dto.request.VerifyCodeDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthApi {


    @PostMapping("/login")
    ResponseEntity<ResponseSuccessDto> login(@RequestBody LogInDto logInDto);


    @PostMapping("/code/verify")
    ResponseEntity<ResponseSuccessDto> verifyCode(@RequestBody VerifyCodeDto verifyCodeDto);

    @PostMapping("/password/recovery")
    ResponseEntity<ResponseSuccessDto> passwordrecovery(@RequestBody PasswordRecoveryDto passwordRecoveryDto);

    @PostMapping("/password/reset")
    ResponseEntity<ResponseSuccessDto> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto);

}

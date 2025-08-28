package ayd.back.taller.controller;

import ayd.back.taller.controller.api.AuthApi;
import ayd.back.taller.dto.request.LogInDto;
import ayd.back.taller.dto.request.PasswordRecoveryDto;
import ayd.back.taller.dto.request.ResetPasswordDto;
import ayd.back.taller.dto.request.VerifyCodeDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<ResponseSuccessDto> login(LogInDto logInDto) {
        log.info("POST /auth/login");
        ResponseSuccessDto responseSuccessDto = authService.logIn(logInDto);
        return new ResponseEntity<>(responseSuccessDto,responseSuccessDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> verifyCode(VerifyCodeDto verifyCodeDto) {
        log.info("POST auth/code/verify");
        ResponseSuccessDto responseSuccessDto = authService.verifyCode(verifyCodeDto);
        return new ResponseEntity<>(responseSuccessDto,responseSuccessDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> passwordrecovery(PasswordRecoveryDto passwordRecoveryDto) {
        log.info("POST auth/password/recovery");
        ResponseSuccessDto passwordRecovery = authService.passwordRecovery(passwordRecoveryDto);
        ResponseSuccessDto responseSuccessDto = authService.passwordRecovery(passwordRecoveryDto);
        return new ResponseEntity<>(responseSuccessDto, responseSuccessDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> resetPassword(ResetPasswordDto resetPasswordDto) {
        log.info("POST auth/password/reset");
        ResponseSuccessDto responseSuccessDto = authService.resetPassword(resetPasswordDto);
        return new ResponseEntity<>(responseSuccessDto,responseSuccessDto.getCode());
    }


}

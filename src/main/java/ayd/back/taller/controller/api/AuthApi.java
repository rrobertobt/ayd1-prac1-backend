package ayd.back.taller.controller.api;

import ayd.back.taller.dto.request.LogInDto;
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

}

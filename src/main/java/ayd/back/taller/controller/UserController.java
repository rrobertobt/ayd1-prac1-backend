package ayd.back.taller.controller;

import ayd.back.taller.controller.api.UserApi;
import ayd.back.taller.dto.request.NewUserDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.UserInfoDto;
import ayd.back.taller.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {


    private final UserService userService;

    @Override
    public ResponseEntity<ResponseSuccessDto> saveUser(NewUserDto newUserDto, String sessionToken) {
        log.info("POST /user with header session-token");
        ResponseSuccessDto responseSuccessDto = userService.createUser(newUserDto, sessionToken);
        return new ResponseEntity<>(responseSuccessDto, responseSuccessDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> userRegistry(NewUserDto newUserDto) {
        log.info("POST user/account");
        ResponseSuccessDto responseSuccessDto = userService.userRegistry(newUserDto);
        return new ResponseEntity<>(responseSuccessDto,responseSuccessDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> getAllUsers(String sessionToken) {
        ArrayList<UserInfoDto> users = userService.getAllUsers(sessionToken);
        ResponseSuccessDto responseSuccessDto = ResponseSuccessDto.builder().code(HttpStatus.OK).body(users).build();
        return new ResponseEntity<>(responseSuccessDto, responseSuccessDto.getCode());
    }
}

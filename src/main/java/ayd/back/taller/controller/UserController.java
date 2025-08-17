package ayd.back.taller.controller;

import ayd.back.taller.controller.api.UserApi;
import ayd.back.taller.dto.request.NewUserDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {


    private final UserService userService;

    @Override
    public ResponseEntity<ResponseSuccessDto> saveUser(NewUserDto newUserDto) {
        log.info("POST /user");
        ResponseSuccessDto responseSuccessDto = userService.saveUser(newUserDto);
        return new ResponseEntity<>(responseSuccessDto, responseSuccessDto.getCode());
    }
}

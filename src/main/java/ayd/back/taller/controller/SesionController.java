package ayd.back.taller.controller;

import ayd.back.taller.controller.api.SessionApi;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class SesionController implements SessionApi {

    private final SessionService sessionService;


    @Override
    public ResponseEntity<ResponseSuccessDto> validateSessionToken(String token) {
        log.info("GET /session");
        ResponseSuccessDto responseSuccessDto = sessionService.getSessionInfo(token);
        return new ResponseEntity<>(responseSuccessDto, responseSuccessDto.getCode());
    }
}

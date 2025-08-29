package ayd.back.taller.controller.api;

import ayd.back.taller.dto.response.ResponseSuccessDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/session")
public interface SessionApi {

    @GetMapping
    ResponseEntity<ResponseSuccessDto> validateSessionToken(@RequestHeader(value = "session-token") String token);

}

package ayd.back.taller.controller.api;


import ayd.back.taller.dto.response.ResponseSuccessDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/specialties")
public interface SpecialtiesApi {



    @GetMapping
    ResponseEntity<ResponseSuccessDto> getAllSpecialties();


}

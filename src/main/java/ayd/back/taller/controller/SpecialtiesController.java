package ayd.back.taller.controller;

import ayd.back.taller.controller.api.SpecialtiesApi;
import ayd.back.taller.dto.request.NewSpecialtiesDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.service.SpecialtiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SpecialtiesController implements SpecialtiesApi {

    private final SpecialtiesService specialtiesService;


    @Override
    public ResponseEntity<ResponseSuccessDto> getAllSpecialties() {
        ResponseSuccessDto response = specialtiesService.getAllSpecialties();
        return new ResponseEntity<>(response, response.getCode());
    }
}

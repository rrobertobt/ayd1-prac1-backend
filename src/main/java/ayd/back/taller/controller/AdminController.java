package ayd.back.taller.controller;

import ayd.back.taller.controller.api.AdminApi;
import ayd.back.taller.dto.request.CreateJobDto;
import ayd.back.taller.dto.request.NewServiceTypeDto;
import ayd.back.taller.dto.request.NewSpecialtiesDto;
import ayd.back.taller.dto.request.UpdateServicePriceDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AdminController implements AdminApi {


    private final AdminService adminService;

    private final SessionService sessionService;

    private final SpecialtiesService specialtiesService;

    private final JobService jobService;

    private final ServiceTypeService serviceTypeService;

    @Override
    public ResponseEntity<ResponseSuccessDto> getAllVehicles(String token) {
        log.info("GET /admin/vehicles");
        ResponseSuccessDto responseSuccessDto = adminService.getAllVehicles(token);
        return new ResponseEntity<>(responseSuccessDto, responseSuccessDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> GetVehicleOwner(String token, String plate) {
        log.info("GERT admin/vehicle/{id}/owner");
        ResponseSuccessDto responseSuccessDto = adminService.getOwnerByVehiclePlate(token, plate);
        return new ResponseEntity<>(responseSuccessDto, responseSuccessDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> updateServicePrice(UpdateServicePriceDto updateServicePriceDto, String token) {
        log.info("POST admin/service/price");
        sessionService.validateSessionToken(token);
        ResponseSuccessDto responseSuccessDto = adminService.updateServicePrice(updateServicePriceDto);
        return new ResponseEntity<>(responseSuccessDto,responseSuccessDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> createSpecialties(NewSpecialtiesDto newSpecialtiesDto, String sessionToken) {
        log.info("POST admin/specialties");
        sessionService.isAdmin(sessionToken);
        ResponseSuccessDto responseSuccessDto = specialtiesService.createSpecialties(newSpecialtiesDto.getName());
        return new ResponseEntity<>(responseSuccessDto, responseSuccessDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> createJob(CreateJobDto createJobDto, String token) {
        log.info("POST admin/job");
        jobService.createNewJob(createJobDto,token);
        ResponseSuccessDto responseSuccessDto = ResponseSuccessDto.builder().code(HttpStatus.CREATED).message("The job was created successfully").build();
        return new ResponseEntity<>(responseSuccessDto, responseSuccessDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessDto> createServiceType(NewServiceTypeDto newServiceTypeDto, String token) {
        log.info("POST admin/service_type");
        sessionService.isAdmin(token);
        serviceTypeService.createServiceType(newServiceTypeDto);
        ResponseSuccessDto response = ResponseSuccessDto.builder().code(HttpStatus.CREATED).message("The service was created successfully").build();
        return new ResponseEntity<>(response, response.getCode());
    }
}

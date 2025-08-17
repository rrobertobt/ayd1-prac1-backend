package ayd.back.taller.controller.api;


import ayd.back.taller.dto.request.NewUserDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jdk.jfr.ContentType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserApi {



    @Operation(
            summary = "Crear nuevo usuario",
            description = "Creación de un nuevo usuario al sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuario creado correctamente",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseSuccessDto.class))
                    )
            }
    )
    @PostMapping
    ResponseEntity<ResponseSuccessDto> saveUser(@RequestBody NewUserDto newUserDto);


}

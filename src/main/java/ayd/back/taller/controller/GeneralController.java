package ayd.back.taller.controller;

import ayd.back.taller.controller.api.GeneralApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class GeneralController implements GeneralApi {


    @Override
    public String getGeneral() {
        return "Hola Mundo";
    }
}

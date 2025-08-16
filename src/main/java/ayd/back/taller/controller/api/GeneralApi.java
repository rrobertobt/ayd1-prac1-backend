package ayd.back.taller.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("general")
public interface GeneralApi {


    @GetMapping()
    String getGeneral();


}

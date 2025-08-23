package ayd.back.taller.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ResponseSuccessDto {

    private HttpStatus code;

    private String message;

    private Object body;

}

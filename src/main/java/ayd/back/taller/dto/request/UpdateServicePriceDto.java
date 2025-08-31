package ayd.back.taller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdateServicePriceDto {

    @JsonProperty("service_id")
    private Integer id;

    @JsonProperty("new_price")
    private Double newPrice;

}

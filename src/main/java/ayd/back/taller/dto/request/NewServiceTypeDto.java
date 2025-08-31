package ayd.back.taller.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewServiceTypeDto {

    private Integer specialitiesId;

    private String name;

    private String description;

    private Double price;

    private Integer estimatedTime;
}

package ayd.back.taller.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Setter
@Getter
@AllArgsConstructor
@Embeddable
public class EmployeeSpecializationId{

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "specialty_id")
    private Integer specialtyId;


}

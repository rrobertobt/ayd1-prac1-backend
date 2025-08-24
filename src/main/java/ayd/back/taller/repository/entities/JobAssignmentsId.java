package ayd.back.taller.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class JobAssignmentsId {

    @Column(name = "job_id")
    private Integer jobId;

    @Column(name = "user_id")
    private Integer userId;


}

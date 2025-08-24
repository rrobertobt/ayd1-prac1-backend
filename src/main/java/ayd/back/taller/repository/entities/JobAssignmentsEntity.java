package ayd.back.taller.repository.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job_assignments")
public class JobAssignmentsEntity implements Serializable {


    @EmbeddedId
    private JobAssignmentsId id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    @MapsId("jobId")
    private JobEntity job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private UserEntity user;

    @Column(name = "created_at",insertable = true, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "update_at",insertable = true, updatable = true)
    @CreationTimestamp
    private LocalDateTime updateAt;

}

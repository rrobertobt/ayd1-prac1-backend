package ayd.back.taller.repository.entities;

import ayd.back.taller.repository.enums.JobStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class JobEntity {

    public JobEntity(VehicleEntity vehicle, String description, JobStatusEnum status, LocalDateTime authorizedAt, Integer estimatedTime, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.vehicle = vehicle;
        this.description = description;
        this.status = status;
        this.authorizedAt = authorizedAt;
        this.estimatedTime = estimatedTime;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id",referencedColumnName = "id")
    private VehicleEntity vehicle;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "job_status_t")
    private JobStatusEnum status;

    @Column(name = "authorized_at")
    private LocalDateTime authorizedAt;

    @Column(name = "estimated_time")
    private Integer estimatedTime;

    @Column(name = "created_at", insertable = true, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true)
    @CreationTimestamp
    private LocalDateTime updateAt;


    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<JobAssignmentsEntity> assignments;

    // Relación con tasks
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<JobTaskEntity> tasks;

}

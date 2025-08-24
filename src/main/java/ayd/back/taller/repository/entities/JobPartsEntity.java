package ayd.back.taller.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job_parts")
public class JobPartsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id",referencedColumnName = "id")
    private JobEntity job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id",referencedColumnName = "id")
    private PartEntity part;

    @Column(name = "unit_price")
    private Double unitPrice;

    private Integer amount;

    @Column(name = "created_at", insertable = true, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true)
    private LocalDateTime updatedAt;



}

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
@Table(name = "parts_catalogs")
public class PartCatalogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id",referencedColumnName = "id")
    private UserEntity supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id",referencedColumnName = "id")
    private PartEntity part;

    private Integer stock;

    private Double price;

    @Column(name = "created_at", insertable = true, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = true, updatable = true)
    @CreationTimestamp
    private LocalDateTime updatedAt;

}

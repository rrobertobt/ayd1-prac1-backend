package ayd.back.taller.repository.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mfa_codes")
public class CodeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    private String code;

    @Column(name = "expires_at")
    private Date expiresAt;

    @Column(name = "is_used")
    private Boolean isUsed;

    @Column(name = "twofa_method")
    private String twofaMethod;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}

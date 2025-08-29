package ayd.back.taller.repository.entities;

import ayd.back.taller.repository.enums.TwofaMethodEnum;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
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

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "twofa_method", columnDefinition = "twofa_method_t")
    private TwofaMethodEnum twofaMethod;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}

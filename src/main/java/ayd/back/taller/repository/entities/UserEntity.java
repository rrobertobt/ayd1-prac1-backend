package ayd.back.taller.repository.entities;


import ayd.back.taller.repository.enums.TwofaMethodEnum;
import ayd.back.taller.repository.enums.UserRoleEnum;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "role", nullable = false, columnDefinition = "user_role_t")
    private UserRoleEnum role;

    private String email;

    private Integer nit;

    private String name;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password_hash")
    private String password;

    @Column(name = "is_active")
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "twofa_method", columnDefinition = "twofa_method_t")
    private TwofaMethodEnum twofaMethod;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


}

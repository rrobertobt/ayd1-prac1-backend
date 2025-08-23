package ayd.back.taller.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class ApplicationProperties {


    @Value("${spring.mail.issue}")
    private String issue;

    @Value("${spring.mail.message}")
    private String message;

    @Value("${mail.recovery_subject}")
    private String issueRecoveryPassword;


}

package ayd.back.taller.service;


import ayd.back.taller.config.ApplicationProperties;
import ayd.back.taller.dto.request.*;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.UserInfoDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.crud.CodeRepository;
import ayd.back.taller.repository.entities.CodeEntity;
import ayd.back.taller.repository.entities.SessionEntity;
import ayd.back.taller.repository.entities.UserEntity;
import ayd.back.taller.repository.enums.UserRoleEnum;
import ayd.back.taller.utils.AuthUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final ApplicationProperties applicationProperties;

    private final UserService userService;

    private final AuthUtils authUtils;

    private final CodeRepository codeRepository;

    private final EmailService emailService;

    private final SessionService sessionService;

    public ResponseSuccessDto logIn(LogInDto logInDto){
        UserEntity user = userService.getUserByEmail(logInDto.getEmail());

        if(!authUtils.validatePassword(logInDto.getPassword(), user.getPassword())){
            throw new BusinessException(HttpStatus.NOT_FOUND,"user not found");
        }

        String code = authUtils.generateVerificationCode();
        emailService.sendEmail(user.getEmail(), applicationProperties.getIssue(),code);
        saveOrUpdateAuthCode(code,user);

        return ResponseSuccessDto.builder().code(HttpStatus.OK).message("A email was sending for get verification code").build();
    }

    public void saveOrUpdateAuthCode(String code, UserEntity user){
        if(Objects.isNull(user)){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"The user must not be null.");
        }

        if(Objects.isNull(code)){
            throw new BusinessException(HttpStatus.BAD_REQUEST, "the code must not be null.");
        }

        Optional<CodeEntity> optionalCodeEntity = codeRepository.findByUserId(user.getId());
        CodeEntity codeEntity = new CodeEntity();
        if(!optionalCodeEntity.isEmpty()){
            codeEntity = optionalCodeEntity.get();
        }else{
            codeEntity.setUser(user);
            codeEntity.setTwofaMethod(user.getTwofaMethod().name());
        }

        codeEntity.setCode(code);
        codeEntity.setExpiresAt(authUtils.createExpirationDate(1));
        codeEntity.setIsUsed(Boolean.FALSE);
        codeRepository.save(codeEntity);
        log.info("The code was saved successfully");
    }

    public ResponseSuccessDto verifyCode(VerifyCodeDto verifyCodeDto){
        if(Objects.isNull(verifyCodeDto.getCode())){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"The code must not be null");
        }

        Optional<CodeEntity> optionalCodeEntity = codeRepository.findByCode(verifyCodeDto.getCode());

        if(optionalCodeEntity.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"The code value is incorrect.");
        }

        CodeEntity codeEntity = optionalCodeEntity.get();

        if(codeEntity.getExpiresAt().before(new Date())){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"The code has expired");
        }

        if(codeEntity.getIsUsed()){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"The code has already been used");
        }

        if(!codeEntity.getUser().getEmail().equals(verifyCodeDto.getUserEmail())){
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "The code is not valid for the user");
        }

        codeEntity.setIsUsed(Boolean.TRUE);
        codeRepository.save(codeEntity);

        sessionService.saveOrUpdateSessionEntity(new SessionEntity());

        UserEntity userEntity = codeEntity.getUser();

        SessionEntity sessionEntity = new SessionEntity();
        String token = authUtils.generateVerificationCode();
        sessionEntity.setToken(token);
        sessionEntity.setUser(codeEntity.getUser());
        sessionEntity.setExpiredAt(authUtils.createExpirationDate(3600));


        UserInfoDto userInfoDto = UserInfoDto.builder()
                .email(codeEntity.getUser().getEmail())
                .role(codeEntity.getUser().getRole())
                .sessionToken(sessionEntity.getToken())
                .build();

        return ResponseSuccessDto.builder().code(HttpStatus.ACCEPTED).message("The code is valid").body(userInfoDto).build();

    }


    public ResponseSuccessDto passwordRecovery(PasswordRecoveryDto passwordRecoveryDto){
        if(Objects.isNull(passwordRecoveryDto.getUserEmail())){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"The user email must not be null");
        }

        UserEntity userEntity = userService.getUserByEmail(passwordRecoveryDto.getUserEmail());
        String code = authUtils.generateVerificationCode();
        saveOrUpdateAuthCode(code,userEntity);
        emailService.sendEmail(userEntity.getEmail(), applicationProperties.getIssueRecoveryPassword(),code);

        return ResponseSuccessDto.builder().code(HttpStatus.OK).message("A code has been sent to recover the password.").build();
    }


    public ResponseSuccessDto resetPassword(ResetPasswordDto resetPasswordDto){

        UserEntity userEntity = userService.getUserByEmail(resetPasswordDto.getUserEmail());

        if(!resetPasswordDto.getNewPassword().equals(resetPasswordDto.getConfirmPassword())){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"the passwords do not match");
        }

        String newPasswordHash = authUtils.hashPassword(resetPasswordDto.getNewPassword());
        userEntity.setPassword(newPasswordHash);
        userService.updateUser(userEntity);

        return ResponseSuccessDto.builder().code(HttpStatus.OK).message("The password has been reset").build();
    }

}

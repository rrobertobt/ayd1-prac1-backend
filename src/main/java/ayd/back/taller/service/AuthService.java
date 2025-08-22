package ayd.back.taller.service;


import ayd.back.taller.config.ApplicationProperties;
import ayd.back.taller.dto.request.LogInDto;
import ayd.back.taller.dto.request.VerifyCodeDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.crud.CodeRepository;
import ayd.back.taller.repository.entities.CodeEntity;
import ayd.back.taller.repository.entities.UserEntity;
import ayd.back.taller.utils.AuthUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
        codeEntity.setExpiresAt(authUtils.createExpirationDate());
        codeEntity.setIsUsed(Boolean.FALSE);
        codeRepository.save(codeEntity);
        log.info("The code was saved successfully");
    }

    public void verifyCode(VerifyCodeDto verifyCodeDto){
        if(Objects.isNull(verifyCodeDto.getCode())){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"The code must not be null");
        }

        Optional<CodeEntity> optionalCodeEntity = codeRepository.findByCode(verifyCodeDto.getCode());

        if(optionalCodeEntity.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"The code value is incorrect.");
        }

        CodeEntity codeEntity = optionalCodeEntity.get();

        if(codeEntity.getIsUsed()){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"The code has already been used");
        }
    }
}

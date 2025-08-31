package ayd.back.taller.service;

import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.SessionResponseDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.crud.SessionRepository;
import ayd.back.taller.repository.entities.SessionEntity;
import ayd.back.taller.repository.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SessionService {


    private final SessionRepository sessionRepository;

    public void saveOrUpdateSessionEntity(SessionEntity sessionEntity){
        Optional<SessionEntity> optionalSessionEntity = sessionRepository.findByUser(sessionEntity.getUser().getId());

        if(optionalSessionEntity.isEmpty()){
            sessionRepository.save(sessionEntity);
        }else{
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            SessionEntity actualSession = optionalSessionEntity.get();
            sessionRepository.updateToken(sessionEntity.getToken(),calendar.getTime(), actualSession.getToken());
        }
    }

    public ResponseSuccessDto getSessionInfo(String sessionToken){
        SessionResponseDto sessionResponseDto = validateSessionToken(sessionToken);
        return ResponseSuccessDto.builder().code(HttpStatus.ACCEPTED).message("The session token is valid").body(sessionResponseDto).build();
    }

    public SessionResponseDto validateSessionToken(String sessionToken){
        if(Objects.isNull(sessionToken)){
            throw new BusinessException(HttpStatus.BAD_REQUEST, "The session token must not be null");
        }

        Optional<SessionEntity> optionalSessionEntity = sessionRepository.findById(sessionToken);

        if(optionalSessionEntity.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"The session with this token not exist");
        }

        SessionEntity sessionEntity = optionalSessionEntity.get();

        if(sessionEntity.getExpiredAt().before(new Date())){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"The tokes has been expired");
        }

        SessionResponseDto sessionResponseDto = SessionResponseDto.builder()
                .email(sessionEntity.getUser().getEmail())
                .role(sessionEntity.getUser().getRole()).expiredAt(sessionEntity.getExpiredAt())
                .nit(sessionEntity.getUser().getNit())
                .address(sessionEntity.getUser().getAddress())
                .name(sessionEntity.getUser().getName())
                .phoneNumber(sessionEntity.getUser().getPhoneNumber())
                .expiredAt(sessionEntity.getExpiredAt())
                .build();

        return sessionResponseDto;
    }

    public void isAdmin(String sessionToken){

        SessionResponseDto sessionResponseDto = validateSessionToken(sessionToken);

        if(!sessionResponseDto.getRole().equals(UserRoleEnum.ADMIN)){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"The token does not belong to an administrator user.");
        }
    }


}

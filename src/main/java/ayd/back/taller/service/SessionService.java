package ayd.back.taller.service;

import ayd.back.taller.dto.response.SessionResponseDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.repository.crud.SessionRepository;
import ayd.back.taller.repository.entities.SessionEntity;
import ayd.back.taller.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.nio.charset.StandardCharsets;
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
            SessionEntity actualSession = optionalSessionEntity.get();
            sessionRepository.updateToken(sessionEntity.getToken(),actualSession.getToken());
        }
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

        SessionResponseDto sessionResponseDto = SessionResponseDto.builder().userEmail(sessionEntity.getUser().getEmail())
                .role(sessionEntity.getUser().getRole()).expiredAt(sessionEntity.getExpiredAt()).build();

        return sessionResponseDto;
    }


}

package ayd.back.taller.service;

import ayd.back.taller.dto.request.NewUserDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.dto.response.SessionResponseDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.mappers.UserMapper;
import ayd.back.taller.repository.crud.UserCrud;
import ayd.back.taller.repository.entities.SessionEntity;
import ayd.back.taller.repository.entities.UserEntity;
import ayd.back.taller.repository.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {


    private final UserCrud userCrud;

    private final UserMapper userMapper;

    private final SessionService sessionService;


    public ResponseSuccessDto createUser(NewUserDto newUserDto, String sessionToken){

        if(Objects.isNull(newUserDto)){
            throw new BusinessException(HttpStatus.NOT_FOUND,"The new user must not be null");
        }

        if(Objects.isNull(newUserDto.getEmail()) || Objects.isNull(newUserDto.getNit()) || Objects.isNull(newUserDto.getPassword())){
            throw new BusinessException(HttpStatus.BAD_REQUEST, "There are fields that should not be null");
        }

        if(Objects.isNull(sessionToken)){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"The session token must not be null");
        }

        SessionResponseDto sessionResponseDto = sessionService.validateSessionToken(sessionToken);

        if(!sessionResponseDto.getRole().equals(UserRoleEnum.ADMIN)){
            throw new BusinessException(HttpStatus.UNAUTHORIZED,"The user does not have permission to add new users");
        }

        userCrud.save(userMapper.toUserEntity(newUserDto));
        log.info("the user was saved successfully");
        return ResponseSuccessDto.builder().code(HttpStatus.CREATED).message("The user was saved").build();
    }

    public ResponseSuccessDto userRegistry(NewUserDto newUserDto){
        newUserDto.setRole(UserRoleEnum.CLIENTE.name());

        if(Objects.isNull(newUserDto)){
            throw new BusinessException(HttpStatus.NOT_FOUND,"The new user must not be null");
        }

        if(Objects.isNull(newUserDto.getEmail()) || Objects.isNull(newUserDto.getNit()) || Objects.isNull(newUserDto.getPassword())){
            throw new BusinessException(HttpStatus.BAD_REQUEST, "There are fields that should not be null");
        }

        userCrud.save(userMapper.toUserEntity(newUserDto));
        log.info("Account was saved successfully");
        return ResponseSuccessDto.builder().code(HttpStatus.CREATED).message("Account was created").build();
    }


    public UserEntity getUserByEmail(String email){
        Optional<UserEntity> optionalUserEntity = userCrud.getUserByEmail(email);

        if(optionalUserEntity.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"user not found");
        }

        return optionalUserEntity.get();
    }

    public UserEntity getUserByNit(Integer nit){
        Optional<UserEntity> optionalUserEntity = userCrud.getUserByNit(nit);

        if(optionalUserEntity.isEmpty()){
            throw new BusinessException(HttpStatus.NOT_FOUND,"user not found");
        }

        return optionalUserEntity.get();
    }

    public void updateUser(UserEntity user){
        if(Objects.isNull(user)){
            throw new BusinessException(HttpStatus.BAD_REQUEST,"the user must not be null");
        }
        userCrud.save(user);
    }


}

package ayd.back.taller.service;

import ayd.back.taller.dto.request.NewUserDto;
import ayd.back.taller.dto.response.ResponseSuccessDto;
import ayd.back.taller.exception.BusinessException;
import ayd.back.taller.mappers.UserMapper;
import ayd.back.taller.repository.crud_repository.UserCrud;
import ayd.back.taller.repository.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {


    private final UserCrud userCrud;

    private final UserMapper userMapper;


    public ResponseSuccessDto saveUser(NewUserDto newUserDto){

        if(Objects.isNull(newUserDto)){
            throw new BusinessException(HttpStatus.NOT_FOUND,"The new user must not be null");
        }

        if(Objects.isNull(newUserDto.getEmail()) || Objects.isNull(newUserDto.getNit()) || Objects.isNull(newUserDto.getPassword())){
            throw new BusinessException(HttpStatus.BAD_REQUEST, "There are fields that should not be null");
        }

        userCrud.save(userMapper.toUserEntity(newUserDto));
        log.info("the user was saved successfully");

        return ResponseSuccessDto.builder().code(HttpStatus.CREATED).message("The user was saved").build();
    }




}

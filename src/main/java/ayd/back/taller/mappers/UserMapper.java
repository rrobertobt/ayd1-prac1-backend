package ayd.back.taller.mappers;

import ayd.back.taller.dto.request.NewUserDto;
import ayd.back.taller.repository.entities.UserEntity;
import ayd.back.taller.repository.enums.TwofaMethodEnum;
import ayd.back.taller.repository.enums.UserRoleEnum;
import ayd.back.taller.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserMapper {


    private final AuthUtils authUtils;

    public UserEntity toUserEntity(NewUserDto userDto){

        String passwordHashed = authUtils.hashPassword(userDto.getPassword());
        return UserEntity.builder().role(userDto.getRole()).email(userDto.getEmail()).nit(userDto.getNit()).name(userDto.getName())
                .address(userDto.getAddress()).phoneNumber(userDto.getPhoneNumber()).password(passwordHashed)
                .isActive(Boolean.TRUE).twofaMethod(TwofaMethodEnum.valueOf(userDto.getTwofaMethod()))
                .createdAt(LocalDateTime.now()).build();
    }

}

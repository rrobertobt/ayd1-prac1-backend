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
        UserEntity user = new UserEntity();
        user.setRole(userDto.getRole());
        user.setEmail(userDto.getEmail());
        user.setNit(userDto.getNit());
        user.setName(userDto.getName());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(passwordHashed);
        user.setIsActive(Boolean.TRUE);
        user.setTwofaMethod(TwofaMethodEnum.valueOf(userDto.getTwofaMethod()));
        user.setCreatedAt(LocalDateTime.now());

        return user;
    }

}

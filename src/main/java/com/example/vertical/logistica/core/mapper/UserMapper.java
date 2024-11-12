package com.example.vertical.logistica.core.mapper;

import com.example.vertical.logistica.core.domain.model.User;
import com.example.vertical.logistica.core.adapters.controller.dto.UserDTO;
import com.example.vertical.logistica.core.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserEntity toEntity(User user){
        return UserEntity.builder()
                .userFileId(user.getUserId())
                .name(user.getName())
                .build();

    }
    public static UserDTO toDto(UserEntity user){
        return UserDTO.builder()
                .id(user.getId())
                .userId(user.getUserFileId())
                .name(user.getName())
                .build();
    }
    public static UserDTO toDto(User user){
        return UserDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .build();
    }
}

package com.example.vertical.logistica.core.adapters.controller.dto;

import com.example.vertical.logistica.core.domain.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private UUID id;
    private Long userId;
    private String name;

    public static UserDTO fromEntity(UserEntity user){
        return UserDTO.builder().userId(user.getUserFileId()).name(user.getName()).build();
    }
}

package com.example.vertical.logistica.core.usecase;

import com.example.vertical.logistica.core.adapters.controller.dto.UserDTO;
import com.example.vertical.logistica.core.domain.entities.UserEntity;
import com.example.vertical.logistica.core.domain.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserDTO> saveAll(List<User> users);
    List<UserEntity> findAllByFileUserId(List<Long> id);
    Map<String, UserEntity> saveMapUsersByName(List<User> users);
}

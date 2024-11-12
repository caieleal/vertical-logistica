package com.example.vertical.logistica.core.service;

import com.example.vertical.logistica.core.usecase.UserService;
import com.example.vertical.logistica.core.adapters.controller.dto.UserDTO;
import com.example.vertical.logistica.core.domain.model.User;
import com.example.vertical.logistica.core.domain.entities.UserEntity;
import com.example.vertical.logistica.core.mapper.UserMapper;
import com.example.vertical.logistica.core.adapters.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> saveAll(List<User> users) {
        List<UserEntity> usersToSave = getUsersNonPersisted(users);

        if(usersToSave.isEmpty()){
            return users.stream().map(UserMapper::toDto).toList();
        }
        return userRepository.saveAll(usersToSave).stream().map(UserMapper::toDto).toList();
    }
    @Override
    public List<UserEntity> findAllByFileUserId(List<Long> id) {
        return userRepository.findAllByUserFileIdIn(id).stream().toList();
    }
    @Override
    public Map<String, UserEntity> saveMapUsersByName(List<User> users) {
        List<UserDTO> usersDto = saveAll(users);
        return findAllByFileUserId(usersDto.stream().map(UserDTO::getUserId).toList())
                .stream()
                .collect(Collectors.toMap(UserEntity::getName, user -> user));
    }
    private List<UserEntity> getUsersNonPersisted(List<User> users){
        users = users.stream().distinct().toList();
        List<Long> userFileId = users.stream()
                .map(User::getUserId)
                .collect(Collectors.toList());

        Map<String, UserEntity> persistedUsers = getUserMappedByName(userFileId);

        return users.stream()
                .filter(user -> {
                    UserEntity entity = persistedUsers.get(user.getName());
                    return entity == null ||
                            (!entity.getName().equals(user.getName()));
                })
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }
    private Map<String, UserEntity> getUserMappedByName(List<Long> ids){
        return userRepository.findAllByUserFileIdIn(ids)
                .stream()
                .collect(Collectors.toMap(
                        UserEntity::getName,
                        usuario -> usuario,
                        (existing, replacement) -> existing
                ));
    }

}

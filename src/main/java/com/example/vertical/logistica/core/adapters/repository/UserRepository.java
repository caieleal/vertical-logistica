package com.example.vertical.logistica.core.adapters.repository;

import com.example.vertical.logistica.core.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findAllByUserFileIdIn(List<Long> userFileId);
}

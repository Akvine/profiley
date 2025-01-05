package ru.akvine.profiley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.akvine.profiley.repository.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("from UserEntity ue where " +
            "ue.email = :email " +
            "and " +
            "ue.deleted = false")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query("from UserEntity ue where " +
            "ue.uuid = :uuid " +
            "and " +
            "ue.deleted = false")
    Optional<UserEntity> findByUuid(@Param("uuid") String uuid);
}

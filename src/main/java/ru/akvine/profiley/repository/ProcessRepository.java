package ru.akvine.profiley.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.akvine.profiley.repository.entity.ProcessEntity;

import java.util.List;
import java.util.Optional;

public interface ProcessRepository extends JpaRepository<ProcessEntity, Long> {
    @Query("from ProcessEntity pe where " +
            "pe.pid = :pid " +
            "and " +
            "pe.user.uuid = :userUuid " +
            "and " +
            "pe.deleted = false and pe.user.deleted = false")
    Optional<ProcessEntity> find(@Param("pid") String byPid,
                                 @Param("userUuid") String byUserUuid);

    @Query("from ProcessEntity pe where " +
            "pe.user.uuid = :userUuid " +
            "and " +
            "pe.deleted = false")
    List<ProcessEntity> findAll(@Param("userUuid") String userUuid, Pageable pageable);
}

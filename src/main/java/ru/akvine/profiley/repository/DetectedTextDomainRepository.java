package ru.akvine.profiley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.akvine.profiley.repository.entity.domain.DetectedTextDomainEntity;

import java.util.List;

public interface DetectedTextDomainRepository extends JpaRepository<DetectedTextDomainEntity, Long> {

    @Query("from DetectedTextDomainEntity dtde where " +
            "dtde.process.pid = :pid " +
            "and " +
            "dtde.process.user.uuid = :userUuid " +
            "and " +
            "dtde.process.deleted = false and dtde.process.user.deleted = false")
    List<DetectedTextDomainEntity> list(@Param("pid") String pid,
                                        @Param("userUuid") String userUuid);
}

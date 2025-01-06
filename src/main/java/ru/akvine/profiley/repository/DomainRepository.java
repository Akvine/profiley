package ru.akvine.profiley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.akvine.profiley.repository.entity.DomainEntity;

import java.util.List;
import java.util.Optional;

public interface DomainRepository extends JpaRepository<DomainEntity, Long> {
    @Query("from DomainEntity de " +
            "where de.user.id = :userId " +
            "and " +
            "de.deleted = false")
    List<DomainEntity> findAll(@Param("userId") long userId);

    @Query("from DomainEntity de " +
            "where de.name = :name " +
            "and " +
            "de.user.uuid = :uuid " +
            "and " +
            "de.deleted = false and de.user.deleted = false")
    Optional<DomainEntity> findByNameAndUserUuid(@Param("name") String name,
                                                 @Param("uuid") String userUuid);
}

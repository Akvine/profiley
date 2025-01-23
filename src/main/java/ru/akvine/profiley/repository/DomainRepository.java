package ru.akvine.profiley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.akvine.profiley.repository.entity.DomainEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DomainRepository extends JpaRepository<DomainEntity, Long> {
    @Query("from DomainEntity de " +
            "where de.user.uuid = :userUuid " +
            "and " +
            "de.deleted = false")
    List<DomainEntity> findAll(@Param("userUuid") String userUuid);

    @Query("from DomainEntity de " +
            "where de.user is null " +
            "and " +
            "de.deleted = false")
    List<DomainEntity> findSystem();

    @Query("from DomainEntity de " +
            "where de.name = :name " +
            "and " +
            "de.user.uuid = :uuid " +
            "and " +
            "de.deleted = false and de.user.deleted = false")
    Optional<DomainEntity> findByNameAndUserUuid(@Param("name") String name,
                                                 @Param("uuid") String userUuid);

    @Query("from DomainEntity de " +
            "where de.name in :names " +
            "and " +
            "de.user.uuid = :uuid " +
            "and " +
            "de.deleted = false and de.user.deleted = false")
    List<DomainEntity> findByNamesAndUserUuid(@Param("names") Collection<String> names,
                                              @Param("uuid") String userUuid);
}

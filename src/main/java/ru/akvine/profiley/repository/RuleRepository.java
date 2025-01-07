package ru.akvine.profiley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.akvine.profiley.repository.entity.RuleEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RuleRepository extends JpaRepository<RuleEntity, Long> {
    @Query("from RuleEntity re where " +
            "re.deleted = false " +
            "and " +
            "re.domain.id in :domains")
    Set<RuleEntity> findAll(@Param("domains") Collection<Long> domainsIds);

    @Query("from RuleEntity re where " +
            "re.deleted = false " +
            "and " +
            "re.domain.user is null")
    Set<RuleEntity> findSystem();

    @Query("from RuleEntity re where " +
            "re.domain.name = :domainName " +
            "and " +
            "re.domain.user.uuid = :userUuid " +
            "and " +
            "re.deleted = false and re.domain.deleted = false and re.domain.user.deleted = false")
    List<RuleEntity> findBy(@Param("domainName") String domainName,
                            @Param("userUuid") String userUuid);

    @Query("from RuleEntity re where " +
            "re.deleted = false " +
            "and " +
            "re.uuid = :uuid " +
            "and " +
            "re.domain.user.uuid = :userUuid")
    Optional<RuleEntity> findByUuid(@Param("uuid") String uuid,
                                    @Param("userUuid") String userUuid);
}

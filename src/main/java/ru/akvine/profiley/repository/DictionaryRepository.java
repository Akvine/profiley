package ru.akvine.profiley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.akvine.profiley.repository.entity.DictionaryEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface DictionaryRepository extends JpaRepository<DictionaryEntity, Long> {
    @Query("from DictionaryEntity de where " +
            "de.domain.id in :domains")
    Set<DictionaryEntity> findAll(@Param("domains") Collection<Long> domainsIds);

    @Query("from DictionaryEntity de where " +
            "de.uuid = :uuid " +
            "and " +
            "de.domain.user.uuid = :userUuid and de.domain.user.deleted = false")
    Optional<DictionaryEntity> findBy(@Param("uuid") String uuid,
                                      @Param("userUuid") String userUuid);

    @Query("select count(*) from DictionaryEntity de where " +
            "de.domain.user.uuid = :userUuid and de.domain.user.deleted = false")
    long count(@Param("userUuid") String byUserUuid);
}

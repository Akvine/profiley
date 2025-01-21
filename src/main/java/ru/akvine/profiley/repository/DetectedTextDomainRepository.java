package ru.akvine.profiley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.akvine.profiley.repository.entity.domain.DetectedTextDomainEntity;

public interface DetectedTextDomainRepository extends JpaRepository<DetectedTextDomainEntity, Long> {
}

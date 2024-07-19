package kz.runtime.spring.repository;

import kz.runtime.spring.entity.CharacteristicDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacteristicDescriptionRepository extends JpaRepository<CharacteristicDescription, Long> {
}

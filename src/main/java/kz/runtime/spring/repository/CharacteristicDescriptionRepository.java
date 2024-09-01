package kz.runtime.spring.repository;

import kz.runtime.spring.entity.Characteristic;
import kz.runtime.spring.entity.CharacteristicDescription;
import kz.runtime.spring.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacteristicDescriptionRepository extends JpaRepository<CharacteristicDescription, Long> {
    List<CharacteristicDescription> findAllByCharacteristic(Characteristic characteristic);
}

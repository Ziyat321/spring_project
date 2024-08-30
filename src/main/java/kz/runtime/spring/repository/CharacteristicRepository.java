package kz.runtime.spring.repository;

import kz.runtime.spring.entity.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {
    List<Characteristic> findAllByCategoryId(long categoryId);
}

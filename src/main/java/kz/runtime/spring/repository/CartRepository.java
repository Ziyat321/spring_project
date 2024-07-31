package kz.runtime.spring.repository;

import kz.runtime.spring.entity.Cart;
import kz.runtime.spring.entity.Product;
import kz.runtime.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserAndProduct(User user, Product product);
}

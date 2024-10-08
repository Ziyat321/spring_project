package kz.runtime.spring.repository;

import kz.runtime.spring.entity.Cart;
import kz.runtime.spring.entity.Product;
import kz.runtime.spring.entity.Review;
import kz.runtime.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUserAndProduct(User user, Product product);
    List<Review> findAllByPublished(Boolean published);
    List<Review> findAllByProductAndPublished(Product product, Boolean published);
}

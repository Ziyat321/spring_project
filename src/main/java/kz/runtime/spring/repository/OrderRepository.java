package kz.runtime.spring.repository;

import kz.runtime.spring.entity.Order;
import kz.runtime.spring.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Statement;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findAllByStatus(Status status);
}

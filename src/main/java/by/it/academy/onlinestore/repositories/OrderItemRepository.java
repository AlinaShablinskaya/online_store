package by.it.academy.onlinestore.repositories;

import by.it.academy.onlinestore.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}

package by.it.academy.onlinestore.repositories;

import by.it.academy.onlinestore.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}

package by.it.academy.onlinestore.repositories;

import by.it.academy.onlinestore.entities.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Integer> {
}

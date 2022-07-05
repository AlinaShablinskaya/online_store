package by.it.academy.onlinestore.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "customer_address", schema = "online_store")
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "country")
    private String country;
    @Column(name = "street")
    private String street;
}

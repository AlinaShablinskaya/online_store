package by.it.academy.onlinestore.entities;

import by.it.academy.onlinestore.annotation.NotNull;
import by.it.academy.onlinestore.annotation.ValidBean;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Table(name = "product", schema = "online_store")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "brand")
    private String brand;
    @Column(name = "photo")
    private String photo;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToMany
    @JoinTable(
            name = "catalog_product", schema = "online_store",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "catalog_id")}
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Catalog> catalogs = new ArrayList<>();
}

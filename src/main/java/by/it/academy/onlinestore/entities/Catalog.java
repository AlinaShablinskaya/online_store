package by.it.academy.onlinestore.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "catalog", schema = "online_store")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "group_name")
    private String groupName;
    @ManyToMany
    @JoinTable(
            name = "catalog_product", schema = "online_store",
            joinColumns = {@JoinColumn(name = "catalog_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Product> products = new ArrayList<>();

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void deleteProduct(Product product) {
        this.products.remove(product);
    }
}

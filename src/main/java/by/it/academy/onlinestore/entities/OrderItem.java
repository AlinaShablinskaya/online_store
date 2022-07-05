package by.it.academy.onlinestore.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item", schema = "online_store")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "amount")
    private Integer amount;
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}

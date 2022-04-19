package by.it.academy.onlinestore.entities;

import java.util.Objects;

public class OrderItem {
    private final Integer id;
    private final Integer amount;
    private final Product product;

    private OrderItem(Builder builder) {
        this.id = builder.id;
        this.amount = builder.amount;
        this.product = builder.product;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id)
                && Objects.equals(amount, orderItem.amount)
                && Objects.equals(product, orderItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, product);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", amount=" + amount +
                ", product=" + product +
                '}';
    }

    public static class Builder {
        private Integer id;
        private Integer amount;
        private Product product;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public Builder withProduct(Product product) {
            this.product = product;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}

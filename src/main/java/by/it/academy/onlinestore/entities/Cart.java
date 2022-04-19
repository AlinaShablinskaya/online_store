package by.it.academy.onlinestore.entities;

import java.util.List;
import java.util.Objects;

public class Cart {
    private final Integer id;
    private final List<OrderItem> orderItems;
    private final User user;

    private Cart(Builder builder) {
        this.id = builder.id;
        this.orderItems = builder.orderItems;
        this.user = builder.user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id)
                && Objects.equals(orderItems, cart.orderItems)
                && Objects.equals(user, cart.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderItems, user);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", orderItems=" + orderItems +
                ", user=" + user +
                '}';
    }

    public static class Builder {
        private Integer id;
        private List<OrderItem> orderItems;
        private User user;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withOrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }
}

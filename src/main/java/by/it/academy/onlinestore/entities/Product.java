package by.it.academy.onlinestore.entities;

import java.util.Objects;

public class Product {
    private final Integer id;
    private final String productName;
    private final String productDescription;
    private final Integer price;

    private Product(Builder builder) {
        this.id = builder.id;
        this.productName = builder.productName;
        this.productDescription = builder.productDescription;
        this.price = builder.price;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id)
                && Objects.equals(productName, product.productName)
                && Objects.equals(productDescription, product.productDescription)
                && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productDescription, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
                '}';
    }

    public static class Builder {
        private Integer id;
        private String productName;
        private String productDescription;
        private Integer price;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withProductName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder withProductDescription(String productDescription) {
            this.productDescription = productDescription;
            return this;
        }

        public Builder withPrice(Integer price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}

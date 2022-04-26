package by.it.academy.onlinestore.entities;

import java.util.Objects;

public class Product {
    private final Integer id;
    private final String productName;
    private final String brand;
    private final String photo;
    private final Integer price;

    private Product(Builder builder) {
        this.id = builder.id;
        this.productName = builder.productName;
        this.brand = builder.brand;
        this.price = builder.price;
        this.photo = builder.photo;
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

    public String getBrand() {
        return brand;
    }

    public String getPhoto() {
        return photo;
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
                && Objects.equals(brand, product.brand)
                && Objects.equals(photo, product.photo)
                && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, brand, photo, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", brand='" + brand + '\'' +
                ", photo='" + photo + '\'' +
                ", price=" + price +
                '}';
    }

    public static class Builder {
        private Integer id;
        private String productName;
        private String brand;
        private String photo;
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

        public Builder withBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder withPhoto(String photo) {
            this.photo = photo;
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

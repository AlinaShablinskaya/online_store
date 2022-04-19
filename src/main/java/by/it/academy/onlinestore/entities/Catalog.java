package by.it.academy.onlinestore.entities;

import java.util.List;
import java.util.Objects;

public class Catalog {
    private final Integer id;
    private final String groupName;
    private final List<Product> products;

    private Catalog(Builder builder) {
        this.id = builder.id;
        this.groupName = builder.groupName;
        this.products = builder.products;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Catalog catalog = (Catalog) o;
        return Objects.equals(id, catalog.id)
                && Objects.equals(groupName, catalog.groupName)
                && Objects.equals(products, catalog.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupName, products);
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", products=" + products +
                '}';
    }

    public static class Builder {
        private Integer id;
        private String groupName;
        private List<Product> products;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public Builder withProducts(List<Product> products) {
            this.products = products;
            return this;
        }

        public Catalog build() {
            return new Catalog(this);
        }
    }
}

package by.it.academy.onlinestore.entities;

import java.util.Objects;

public class CustomerAddress {
    private final Integer id;
    private final String zipcode;
    private final String country;
    private final String street;

    private CustomerAddress(Builder builder) {
        this.id = builder.id;
        this.zipcode = builder.zipcode;
        this.country = builder.country;
        this.street = builder.street;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCountry() {
        return country;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerAddress that = (CustomerAddress) o;
        return Objects.equals(id, that.id)
                && Objects.equals(zipcode, that.zipcode)
                && Objects.equals(country, that.country)
                && Objects.equals(street, that.street);
    }

    @Override
    public String toString() {
        return "CustomerAddress{" +
                "id=" + id +
                ", zipcode='" + zipcode + '\'' +
                ", country='" + country + '\'' +
                ", street='" + street + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zipcode, country, street);
    }

    public static class Builder {
        private Integer id;
        private String zipcode;
        private String country;
        private String street;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withZipcode(String zipcode) {
            this.zipcode = zipcode;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withStreet(String street) {
            this.street = street;
            return this;
        }

        public CustomerAddress build() {
            return new CustomerAddress(this);
        }
    }
}

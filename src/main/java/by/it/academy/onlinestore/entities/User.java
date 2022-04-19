package by.it.academy.onlinestore.entities;

import java.util.Objects;

public class User {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String login;
    private final String password;
    private final CustomerAddress address;

    private User(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.login = builder.login;
        this.password = builder.password;
        this.address = builder.address;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public CustomerAddress getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(login, user.login) && Objects.equals(password, user.password)
                && Objects.equals(address, user.address);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, login, password, address);
    }

    public static class Builder {
        private Integer id;
        private String firstName;
        private String lastName;
        private String login;
        private String password;
        private CustomerAddress address;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withAddress(CustomerAddress address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

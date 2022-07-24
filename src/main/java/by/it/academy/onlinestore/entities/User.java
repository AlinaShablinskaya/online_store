package by.it.academy.onlinestore.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "online_store")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private CustomerAddress address;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}

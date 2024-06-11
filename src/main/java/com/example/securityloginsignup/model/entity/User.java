package com.example.securityloginsignup.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "user-authorities", attributeNodes = @NamedAttributeNode("authorities"))
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String username;

    private String password;

    private Integer verificationCode;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    @ManyToMany
    @JoinTable(name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_name", referencedColumnName = "authority"))
    private List<Authority> authorities;

    @PrePersist
    public void autoFill() {
        this.accountNonExpired = false;
        this.accountNonLocked = false;
        this.credentialsNonExpired = false;
        this.enabled = false;
    }
}

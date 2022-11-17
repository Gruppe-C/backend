package de.backend.features.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "`user`",
        uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class User implements UserDetails {

    @Id
    @GenericGenerator(name = "id", strategy = "de.backend.IdGenerator")
    @GeneratedValue(generator = "id")
    private String id;

    private String username;

    private String password;

    @CreationTimestamp
    private Timestamp createdAt;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

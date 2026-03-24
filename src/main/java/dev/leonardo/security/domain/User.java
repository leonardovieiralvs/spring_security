package dev.leonardo.security.domain;

import dev.leonardo.security.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String email;
    private String password;
    private UserRole role;


    // (SOMENTE CASO SEJA UM VALOR UNICO)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER"));
        return List.of(new SimpleGrantedAuthority("USER"));
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return role.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//                .toList();
//    }

    @Override
    public String getUsername() {
        return "";
    }
}

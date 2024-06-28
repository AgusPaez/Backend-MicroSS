package com.example.products.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //busca de user
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var usuario = getById(username); // busca el user

        if (usuario == null) {
            throw new UsernameNotFoundException(username); // error si no se encuentra
        }

        return User // construye userDetail
                .withUsername(username)
                .password(usuario.password())
                .roles(usuario.roles().toArray(new String[0]))
                .build();
    }

    public record Usuario(String username, String password, Set<String> roles) {};

    // trae user
    public static Usuario getById(String username) {

        var password = "$2a$10$NNyC2luXbuKnoSOiyHTubeeQAYgwo5mmZzrw/Yd20RGDFbsyq4XYe";

        Usuario user = new Usuario(
                "user",
                password,
                Set.of("USER") // crea un usuario de ejemplo
        );

        var usuarios = List.of(user); // lista de usuarios

        return usuarios  // Busca y retorna el usuario que coincide con el nombre de usuario
                .stream()
                .filter(e -> e.username().equals(username))
                .findFirst()
                .orElse(null);
    }

}


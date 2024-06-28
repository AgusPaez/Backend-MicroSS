package com.example.orders.security;

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

        var usuario = getById(username); // Busca el usuario por nombre de usuario.

        if (usuario == null) {
            throw new UsernameNotFoundException(username); // Si el usuario no se encuentra, lanza una excepción.
        }

        return User // Construye y retorna un objeto UserDetails con los datos del usuario.
                .withUsername(username)
                .password(usuario.password())
                .roles(usuario.roles().toArray(new String[0]))
                .build();
    }

    public record Usuario(String username, String password, Set<String> roles) {};

    /********** BÚSQUEDA DE USUARIO POR ID **********/
    public static Usuario getById(String username) {

        var password = "$2a$10$NNyC2luXbuKnoSOiyHTubeeQAYgwo5mmZzrw/Yd20RGDFbsyq4XYe"; // Contraseña encriptada del usuario.

        Usuario user = new Usuario(
                "user",
                password,
                Set.of("USER") // Crea un usuario de ejemplo.
        );

        var usuarios = List.of(user); // Lista de usuarios.

        return usuarios  // Busca y retorna el usuario que coincide con el nombre de usuario.
                .stream()
                .filter(e -> e.username().equals(username))
                .findFirst()
                .orElse(null);
    }

}


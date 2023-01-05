package com.cursojava.project.controllers;

import com.cursojava.project.models.Usuario;
import com.cursojava.project.repositories.UsuarioRepository;
import com.cursojava.project.utils.JWTUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping(value = "login")
    public String login(@RequestBody Usuario user) {
        List<Usuario> lista = usuarioRepository.findByUsername(user.getUsername());

        if (!lista.isEmpty()) {
            String password = lista.get(0).getPassword();

            Argon2 argon2 = Argon2Factory.create();
            boolean correctPassword = argon2.verify(password, user.getPassword());

            if (correctPassword) {
                String idUsuario = lista.get(0).getId().toString();
                String emailUsuario = lista.get(0).getEmail();

                String tokenUsuario = jwtUtil.create(idUsuario, emailUsuario);

                return tokenUsuario;
            }
        }

        return "Invalid username or password!";
    }
}

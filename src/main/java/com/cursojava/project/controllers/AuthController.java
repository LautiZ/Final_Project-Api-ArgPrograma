package com.cursojava.project.controllers;

import com.cursojava.project.models.Usuario;
import com.cursojava.project.repositories.UsuarioRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping(value = "login")
    public String login(@RequestBody Usuario user) {
        List<Usuario> lista = usuarioRepository.findByUsername(user.getUsername());

        if (!lista.isEmpty()) {
            String password = lista.get(0).getPassword();

            Argon2 argon2 = Argon2Factory.create();
            boolean correctPassword = argon2.verify(password, user.getPassword());

            if (correctPassword) {
                return "OK";
            }
        }

        return "Invalid username or password!";
    }
}

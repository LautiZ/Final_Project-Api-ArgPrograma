package com.cursojava.project.controllers;

import com.cursojava.project.models.Usuario;
import com.cursojava.project.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping(value = "login")
    public String login(@RequestBody Usuario user) {
        List<Usuario> lista = usuarioRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

        if (!lista.isEmpty()) {
            return "OK";
        } else {
            return "Invalid username or password!";
        }
    }
}

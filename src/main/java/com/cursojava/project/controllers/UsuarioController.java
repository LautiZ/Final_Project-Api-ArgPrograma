package com.cursojava.project.controllers;

import com.cursojava.project.models.Usuario;
import com.cursojava.project.repositories.UsuarioRepository;

import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @RequestMapping(value = "prueba")
    public String prueba() {
        return "Esto es una ruta de prueba";
    }

    @GetMapping(value = "usuarios")
    public List<Usuario> getPersona() {
        return usuarioRepository.findAll();
    }

    @RequestMapping(value = "usuario/edit")
    public Usuario editUser() {
        Usuario user = new Usuario();
        user.setNombre("Lautaro");
        user.setApellido("Zullo");
        user.setEmail("zullolau@gmail.com");
        user.setTelefono(3512583767L);
        user.setUsername("Lauti");
        user.setPassword("156149356");
        return user;
    }

    @DeleteMapping(value = "usuario/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        Usuario deletedPersona = usuarioRepository.findById(id).get();

        usuarioRepository.delete(deletedPersona);
        return "Deleted persona!";
    }

    @PostMapping(value = "usuario/create")
    public void createUser(@RequestBody Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @GetMapping(value = "usuario/search/{id}")
    public Usuario findUser(@PathVariable Long id) {
        return usuarioRepository.findById(id).get();
    }
}

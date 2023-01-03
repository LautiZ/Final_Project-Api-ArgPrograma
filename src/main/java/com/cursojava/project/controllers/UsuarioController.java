package com.cursojava.project.controllers;

import com.cursojava.project.models.Usuario;
import com.cursojava.project.repositories.UsuarioRepository;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Ruta de prueba
    @RequestMapping(value = "prueba")
    public String prueba() {
        return "Esto es una ruta de prueba";
    }

    // Ruta para ver todos los usuarios
    @GetMapping(value = "usuarios")
    public List<Usuario> getPersona() {
        return usuarioRepository.findAll();
    }

    // Ruta para editar un usuario
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

    // Ruta para eliminar un usuario
    @DeleteMapping(value = "usuario/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        Usuario deletedPersona = usuarioRepository.findById(id).get();

        usuarioRepository.delete(deletedPersona);
        return "Deleted persona!";
    }

    // Ruta para registrar un usuario
    @PostMapping(value = "usuario/create")
    public void createUser(@RequestBody Usuario usuario) {

        Argon2 argon2 = Argon2Factory.create();
        String hash = argon2.hash(10, 65536, 1, usuario.getPassword());
        usuario.setPassword(hash);

        usuarioRepository.save(usuario);
    }

    // Ruta para buscar un usuario por id
    @GetMapping(value = "usuario/search/{id}")
    public Usuario findUser(@PathVariable Long id) {
        return usuarioRepository.findById(id).get();
    }
}

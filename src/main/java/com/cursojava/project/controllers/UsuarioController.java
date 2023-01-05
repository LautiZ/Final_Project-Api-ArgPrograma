package com.cursojava.project.controllers;

import com.cursojava.project.models.Usuario;
import com.cursojava.project.repositories.UsuarioRepository;

import com.cursojava.project.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JWTUtil jwtUtil;

    // Ruta de prueba
    @RequestMapping(value = "prueba")
    public String prueba() {
        return "Esto es una ruta de prueba";
    }

    // Ruta para ver todos los usuarios
    @GetMapping(value = "usuarios")
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token) {

        if (!validarToken(token)) {
            return null;
        }

        return usuarioRepository.findAll();
    }

    private boolean validarToken(String token) {
        String idUsuario = jwtUtil.getKey(token);
        return idUsuario != null;
    }

    // Ruta para editar un usuario
    @PutMapping(value = "usuario/edit")
    public Usuario editUser(@RequestHeader(value = "Authorization") String token, @RequestBody @NotNull Usuario usuario) {
        if (!validarToken(token)) {
            return null;
        } else {
            String id = jwtUtil.getKey(token);
            Long idLong = Long.parseLong(id);

            Usuario updatedUsuario = usuarioRepository.findById(idLong).get();

            if (!Objects.equals(usuario.getNombre(), "")) {
                updatedUsuario.setNombre(usuario.getNombre());
            }
            if (!Objects.equals(usuario.getApellido(), "")) {
                updatedUsuario.setApellido(usuario.getApellido());
            }
            if (!Objects.equals(usuario.getEmail(), "")) {
                updatedUsuario.setEmail(usuario.getEmail());
            }
            if (usuario.getTelefono() != null) {
                updatedUsuario.setTelefono(usuario.getTelefono());
            }
            if (!Objects.equals(usuario.getUsername(), "")) {
                updatedUsuario.setUsername(usuario.getUsername());
            }

            usuarioRepository.save(updatedUsuario);
            return updatedUsuario;
        }

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

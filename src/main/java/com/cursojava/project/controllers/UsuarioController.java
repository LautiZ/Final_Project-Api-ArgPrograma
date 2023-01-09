package com.cursojava.project.controllers;

import com.cursojava.project.models.Aboutme;
import com.cursojava.project.models.Domicilio;
import com.cursojava.project.models.Usuario;
import com.cursojava.project.repositories.AboutmeRepository;
import com.cursojava.project.repositories.DomicilioRepository;
import com.cursojava.project.repositories.UsuarioRepository;

import com.cursojava.project.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private AboutmeRepository aboutmeRepository;

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

        try {
            String idUsuario = jwtUtil.getKey(token);
            return idUsuario != null;
        } catch (Exception e) {
            return false;
        }
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

    // Editar domicilio de un usuario
    @PutMapping(value = "usuario/edit/domicilio")
    public Usuario editUserDomicilio(@RequestHeader(value = "Authorization") String token, @RequestBody @NotNull Domicilio domicilio) {
        if (!validarToken(token)) {
            return null;
        } else {
            String id = jwtUtil.getKey(token);
            Long idLong = Long.parseLong(id);

            Usuario user = usuarioRepository.findById(idLong).get();

            Long domicilioID = user.getDomicilio().getDomicilioId();
            Domicilio updatedDomicilio = domicilioRepository.findById(domicilioID).get();

            if (domicilio.getCity() != "") {
                updatedDomicilio.setCity(domicilio.getCity());
            }
            if (domicilio.getAddress() != "") {
                updatedDomicilio.setAddress(domicilio.getAddress());
            }
            if (domicilio.getCountry() != "") {
                updatedDomicilio.setCountry(domicilio.getCountry());
            }
            if (domicilio.getProvince() != "") {
                updatedDomicilio.setProvince(domicilio.getProvince());
            }


            domicilioRepository.save(updatedDomicilio);
            return user;
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

    // Modificar seccion about me
    @PutMapping(value = "usuario/edit/aboutme")
    public Usuario editUserAboutme(@RequestHeader(value = "Authorization") String token, @RequestBody @NotNull Aboutme aboutme) {
        if (!validarToken(token)) {
            return null;
        } else {
            String id = jwtUtil.getKey(token);
            Long idLong = Long.parseLong(id);

            Usuario user = usuarioRepository.findById(idLong).get();

            Long aboutmeID = user.getAboutme().getAboutmeId();
            Aboutme updatedAboutme = aboutmeRepository.findById(aboutmeID).get();

            if (aboutme.getText() != "") {
                updatedAboutme.setText(aboutme.getText());
            }
            if (aboutme.getLinklinkedin() != "") {
                updatedAboutme.setLinklinkedin(aboutme.getLinklinkedin());
            }

            aboutmeRepository.save(updatedAboutme);
            return user;
        }

    }
}

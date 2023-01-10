package com.cursojava.project.controllers;

import com.cursojava.project.models.*;
import com.cursojava.project.repositories.*;

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
    private ProjectsRepository projectsRepository;

    @Autowired
    private EducationRepository educationRepository;

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

    // Funcion para validar el token de usuario
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

    // Ruta para editar domicilio de un usuario
    @PatchMapping(value = "usuario/edit/domicilio")
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

    // Ruta para modificar informacion seccion about me
    @PatchMapping(value = "usuario/edit/aboutme")
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

    // Ruta para editar algun proyecto
    @PatchMapping(value = "usuario/edit/project/{idProject}")
    public Projects editUserProject(@RequestHeader(value = "Authorization") String token, @RequestBody @NotNull Projects projects, @PathVariable Long idProject) {
        if (!validarToken(token)) {
            return null;
        } else {
            String id = jwtUtil.getKey(token);
            Long idLong = Long.parseLong(id);

            Usuario user = usuarioRepository.findById(idLong).get();

            // List<Projects> projectsList = user.getProjectsList();

            try {
                Projects editedProject = projectsRepository.findById(idProject).get();

                if (editedProject.getUser().getId() == user.getId()){
                    if (projects.getTitle() != "") {
                        editedProject.setTitle(projects.getTitle());
                    }
                    if (projects.getText() != "") {
                        editedProject.setText(projects.getText());
                    }
                    if (projects.getLink() != "") {
                        editedProject.setLink(projects.getLink());
                    }
                    projectsRepository.save(editedProject);
                    return editedProject;
                }
                return null;
            } catch (Exception e) {
                return null;
            }
        }
    }

    // Ruta para agregar proyectos al usuario
    @PutMapping(value = "usuario/create/project")
    public Usuario createUserProject(@RequestHeader(value = "Authorization") String token, @RequestBody @NotNull Projects project) {
        if (!validarToken(token)) {
            return null;
        } else {
            String id = jwtUtil.getKey(token);
            Long idLong = Long.parseLong(id);

            Usuario user = usuarioRepository.findById(idLong).get();
            List<Projects> listaProjects = user.getProjectsList();

            project.setUser(user);

            projectsRepository.save(project);

            listaProjects.add(project);

            user.setProjectsList(listaProjects);

            usuarioRepository.save(user);
            return user;
        }
    }

    // Ruta para editar un curso
    @PatchMapping(value = "usuario/edit/education/{idEducation}")
    public Education editUserEducation(@RequestHeader(value = "Authorization") String token, @RequestBody @NotNull Education education, @PathVariable Long idEducation) {
        if (!validarToken(token)) {
            return null;
        } else {
            String id = jwtUtil.getKey(token);
            Long idLong = Long.parseLong(id);

            Usuario user = usuarioRepository.findById(idLong).get();

            // List<Education> educationList = user.getEducationList();

            try {
                Education editedEducation = educationRepository.findById(idEducation).get();

                if (editedEducation.getUser().getId() == user.getId()) {
                    if (education.getTitle() != "") {
                        editedEducation.setTitle(education.getTitle());
                    }
                    if (education.getText() != "") {
                        editedEducation.setText(education.getText());
                    }
                    if (education.getSubtitle() != "") {
                        editedEducation.setSubtitle(education.getSubtitle());
                    }
                    if (education.getDate() != "") {
                        editedEducation.setDate(education.getDate());
                    }
                    educationRepository.save(editedEducation);
                    return editedEducation;
                }
                return null;
            } catch (Exception e) {
                return null;
            }
        }
    }

    // Ruta para agregar cursos al usuario
    @PutMapping(value = "usuario/create/education")
    public Usuario createUserEducation(@RequestHeader(value = "Authorization") String token, @RequestBody @NotNull Education education) {
        if (!validarToken(token)) {
            return null;
        } else {
            String id = jwtUtil.getKey(token);
            Long idLong = Long.parseLong(id);

            Usuario user = usuarioRepository.findById(idLong).get();
            List<Education> listaEducations = user.getEducationList();

            education.setUser(user);

            educationRepository.save(education);

            listaEducations.add(education);

            user.setEducationList(listaEducations);

            usuarioRepository.save(user);
            return user;
        }
    }
}

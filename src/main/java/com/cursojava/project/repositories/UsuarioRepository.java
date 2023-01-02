package com.cursojava.project.repositories;

import com.cursojava.project.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByUsernameAndPassword(String username, String password);
}

package com.cursojava.project.repositories;

import com.cursojava.project.models.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {
}

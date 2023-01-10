package com.cursojava.project.repositories;

import com.cursojava.project.models.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}

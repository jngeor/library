package com.clasemodelo.libros.repository;

import com.clasemodelo.libros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data genera automáticamente findAll, findById, save, deleteById, etc.
 */
public interface LibroRepository extends JpaRepository<Libro, Long> {
}

package com.clasemodelo.libros.controller;

import com.clasemodelo.libros.model.Libro;
import com.clasemodelo.libros.repository.LibroRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroRepository repository;

    public LibroController(LibroRepository repository) {
        this.repository = repository;
    }

    // GET /api/libros  -> 200 OK con la lista completa
    @GetMapping
    public List<Libro> listar() {
        return repository.findAll();
    }

    // GET /api/libros/{id}  -> 200 OK si existe, 404 Not Found si no
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtener(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/libros  -> 201 Created (400 Bad Request si faltan datos)
    @PostMapping
    public ResponseEntity<Libro> crear(@Valid @RequestBody Libro libro) {
        libro.setId(null); // el id lo genera la base de datos
        Libro creado = repository.save(libro);
        URI ubicacion = URI.create("/api/libros/" + creado.getId());
        return ResponseEntity.created(ubicacion).body(creado);
    }

    // PUT /api/libros/{id}  -> 200 OK si existe, 404 Not Found si no
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Long id,
                                            @Valid @RequestBody Libro datos) {
        return repository.findById(id)
                .map(libro -> {
                    libro.setTitulo(datos.getTitulo());
                    libro.setAutor(datos.getAutor());
                    return ResponseEntity.ok(repository.save(libro));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/libros/{id}  -> 204 No Content si existe, 404 Not Found si no
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.clasemodelo.libros;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LibroControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void listarDevuelve200ConLosLibrosIniciales() throws Exception {
        mvc.perform(get("/api/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void obtenerLibroInexistenteDevuelve404() throws Exception {
        mvc.perform(get("/api/libros/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearDevuelve201ConLocation() throws Exception {
        mvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Rayuela\",\"autor\":\"Julio Cortázar\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/libros/3"))
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void crearSinTituloDevuelve400() throws Exception {
        mvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"autor\":\"Julio Cortázar\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizarDevuelve200() throws Exception {
        mvc.perform(put("/api/libros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Nuevo título\",\"autor\":\"Gabriel García Márquez\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Nuevo título"));
    }

    @Test
    void eliminarDevuelve204YLuego404() throws Exception {
        mvc.perform(delete("/api/libros/2")).andExpect(status().isNoContent());
        mvc.perform(delete("/api/libros/2")).andExpect(status().isNotFound());
    }
}

# API REST de Libros · Java 21 + Spring Boot

Proyecto de apoyo para la sesión **"Diseño e implementación de una API REST con Spring Boot"**.
Expone una sola tabla, `libros`, con las cuatro operaciones básicas: **GET, POST, PUT y DELETE**.

**Docente:** Mgs. Jorge Pantigoso

## 🎯 Logro de la sesión

Al finalizar la sesión, el estudiante **diseña e implementa endpoints REST en Java con Spring Boot**, aplicando correctamente los verbos HTTP y los códigos de estado, y los **prueba con Postman**.

## 📚 Antes de la clase (trabajo autónomo · ~60 min)

| Paso | Actividad | Tiempo |
|------|-----------|--------|
| 1 | Lee las secciones 1 a 5 del material: [`docs/Material_APIs_REST_Java.pdf`](docs/Material_APIs_REST_Java.pdf) | 25 min |
| 2 | Clona este repositorio (instrucciones abajo) | 5 min |
| 3 | Ejecuta el proyecto y revisa la clase `LibroController` (sección 6 del PDF) | 15 min |
| 4 | Prueba los endpoints con la colección de Postman (sección 7 del PDF) | 10 min |
| 5 | Responde la autoevaluación (sección 8 del PDF) y anota tus dudas | 5 min |

> En clase **no** repetiremos la teoría desde cero: la usaremos para analizar casos, corregir errores y trabajar juntos con el código.

## 🛠️ Requisitos

- **JDK 21**
- **Maven 3.9** o superior (IntelliJ IDEA ya lo trae integrado)
- Un IDE: IntelliJ IDEA Community, VS Code con *Extension Pack for Java* o Eclipse
- **Postman** (escritorio o web)
- **Git**

No necesitas instalar ninguna base de datos: el proyecto usa **H2 en memoria**.

## ▶️ Cómo ejecutarlo

```bash
git clone https://github.com/jngeor/library.git
cd library
mvn spring-boot:run
```

**Desde IntelliJ IDEA:** abre la carpeta `library`, espera a que Maven descargue las dependencias y ejecuta la clase `ApiLibrosApplication`.

La API queda disponible en `http://localhost:8080/api/libros`.

## 📁 Estructura

```
library/
├── docs/
│   └── Material_APIs_REST_Java.pdf      
├── postman/
│   └── API_Libros.postman_collection.json
├── src/main/java/com/clasemodelo/libros/
│   ├── ApiLibrosApplication.java       
│   ├── model/Libro.java                 
│   ├── repository/LibroRepository.java  
│   └── controller/LibroController.java  
├── src/main/resources/
│   ├── application.properties          
│   └── data.sql                         
└── src/test/java/...                    
```

## 🔗 Endpoints

| Verbo  | Ruta               | Acción                  | Respuestas posibles |
|--------|--------------------|-------------------------|---------------------|
| GET    | `/api/libros`      | Listar todos los libros | 200 |
| GET    | `/api/libros/{id}` | Obtener un libro        | 200, 404 |
| POST   | `/api/libros`      | Crear un libro          | 201, 400 |
| PUT    | `/api/libros/{id}` | Actualizar un libro     | 200, 400, 404 |
| DELETE | `/api/libros/{id}` | Eliminar un libro       | 204, 404 |

Ejemplo de cuerpo para POST y PUT:

```json
{
  "titulo": "Rayuela",
  "autor": "Julio Cortázar"
}
```

Si falta el `titulo` o el `autor`, la API responde **400 Bad Request**.

## 📮 Probar con Postman

1. Con la aplicación en ejecución, abre Postman.
2. Usa **Import** y selecciona `postman/API_Libros.postman_collection.json`.
3. La colección trae, para cada verbo, un caso de éxito y uno de error. Observa en cada respuesta el **código de estado** y el **cuerpo JSON**.

## 🗄️ Ver la tabla en la base de datos

Entra a `http://localhost:8080/h2-console` y usa:

| Campo    | Valor                    |
|----------|--------------------------|
| JDBC URL | `jdbc:h2:mem:biblioteca` |
| User     | `sa`                     |
| Password | *(vacío)*                |

Ejecuta `SELECT * FROM libros;` y repite la consulta después de un POST o un DELETE para ver el cambio.

> Los datos se reinician cada vez que vuelves a ejecutar la aplicación, así que puedes experimentar sin miedo.

## ✅ Pruebas automáticas

```bash
mvn test
```

Verifican los códigos de estado de cada endpoint (200, 201, 204, 400 y 404).

## 🤔 Para pensar e indagar

Tal como está, cualquiera que conozca la URL puede eliminar libros. ¿Cómo lo evitarías?
Investiga qué es la **autenticación** en una API REST y tráelo a la próxima clase.

## 📖 Referencias

- Fielding, R. T. (2000). *Architectural styles and the design of network-based software architectures* [Tesis doctoral, University of California, Irvine].
- Fielding, R., Nottingham, M., & Reschke, J. (Eds.). (2022). *HTTP semantics* (RFC 9110). IETF. https://doi.org/10.17487/RFC9110
- Massé, M. (2011). *REST API design rulebook*. O'Reilly Media.
- Spring. (s. f.). *Building a RESTful web service*. https://spring.io/guides/gs/rest-service
- Walls, C. (2022). *Spring in action* (6.ª ed.). Manning.
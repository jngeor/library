# API REST de Libros · Java 21 + Spring Boot

Proyecto de apoyo para la sesión **"Diseño e implementación de una API REST con Spring Boot"**.
Expone una sola tabla, `libros`, con las cuatro operaciones básicas: **GET, POST, PUT y DELETE**.

## Antes de la clase (trabajo autónomo)

1. Lee el PDF de material de la sesión.
2. Clona este repositorio y ejecuta el proyecto (instrucciones abajo).
3. Revisa la clase `LibroController` e identifica qué verbo HTTP y qué código de estado usa cada método.
4. Prueba los endpoints con la colección de Postman de la carpeta `postman/`.
5. Anota tus dudas para la clase.

## Requisitos

- JDK 21
- Maven 3.9 o superior (o el que trae integrado IntelliJ IDEA)
- Postman

No necesitas instalar ninguna base de datos: el proyecto usa **H2 en memoria**.

## Cómo ejecutarlo

**Desde IntelliJ IDEA:** abre la carpeta del proyecto, espera a que descargue las dependencias de Maven y ejecuta la clase `ApiLibrosApplication`.

**Desde la terminal:**

```bash
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080/api/libros`.

## Estructura

```
src/main/java/com/clasemodelo/libros/
├── ApiLibrosApplication.java    → punto de entrada
├── model/Libro.java             → entidad (tabla "libros")
├── repository/LibroRepository   → acceso a la tabla (Spring Data JPA)
└── controller/LibroController   → endpoints REST
src/main/resources/
├── application.properties       → configuración de H2
└── data.sql                     → 2 libros iniciales
postman/
└── API_Libros.postman_collection.json
```

## Endpoints

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

## Ver la tabla en la base de datos

Con la aplicación en ejecución, entra a `http://localhost:8080/h2-console` y usa:

- **JDBC URL:** `jdbc:h2:mem:biblioteca`
- **User:** `sa`
- **Password:** (vacío)

Luego ejecuta `SELECT * FROM libros;`.

> Los datos se reinician cada vez que vuelves a ejecutar la aplicación.

## Pruebas automáticas

```bash
mvn test
```

Verifican los códigos de estado de cada endpoint (200, 201, 204, 400 y 404).

## Para pensar

Tal como está, cualquiera que conozca la URL puede eliminar libros. ¿Cómo lo evitarías?
Investiga qué es la **autenticación** en una API REST y tráelo a la próxima clase.

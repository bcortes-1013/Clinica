# 🏥 Clínica – API REST con Spring Boot

Proyecto desarrollado en **Spring Boot** para la gestión de una clínica.  
Incluye controladores, validaciones, manejo de excepciones, registro de logs y pruebas en Postman.

---

## 🚀 Características principales

- **Arquitectura RESTful** con endpoints organizados por entidad.  
- **Entidades principales:**
  - 👤 `Usuario`: manejo de datos de usuarios, roles y credenciales.
  - 🧪 `Laboratorio`: gestión de laboratorios, su estado, tipo y capacidad.
- **Validaciones automáticas** mediante anotaciones de `Jakarta Validation` (`@NotNull`, `@Size`, `@Min`, `@Max`, etc.).
- **Control global de excepciones** con `@ControllerAdvice` y respuestas consistentes en formato JSON.
- **Respuestas personalizadas** usando `ResponseEntity`.
- **Logs** implementados con `SLF4J` para el seguimiento de eventos e información de depuración.
- **Excepciones personalizadas** para mejorar la trazabilidad de errores en tiempo de ejecución.
- **Pruebas en Postman** con colecciones que cubren todos los endpoints CRUD.

---

## 🧩 Estructura del proyecto

### 👤 **Usuarios**
| Método | Endpoint | Descripción |
|:-------|:----------|:------------|
| `GET` | `/api/usuarios` | Listar todos los usuarios |
| `GET` | `/api/usuarios/{id}` | Buscar usuario por ID |
| `POST` | `/api/usuarios` | Crear nuevo usuario |
| `PUT` | `/api/usuarios/{id}` | Actualizar usuario existente |
| `DELETE` | `/api/usuarios/{id}` | Eliminar usuario |

---

### 🧪 **Laboratorios**
| Método | Endpoint | Descripción |
|:-------|:----------|:------------|
| `GET` | `/api/laboratorios` | Listar todos los laboratorios |
| `GET` | `/api/laboratorios/{id}` | Buscar laboratorio por ID |
| `POST` | `/api/laboratorios` | Crear nuevo laboratorio |
| `PUT` | `/api/laboratorios/{id}` | Actualizar laboratorio |
| `DELETE` | `/api/laboratorios/{id}` | Eliminar laboratorio |

---

## ⚙️ Tecnologías utilizadas

- ☕ **Java 17+**
- 🌱 **Spring Boot 3.x**
- 🧰 **Maven**
- 🧩 **Spring Web**
- 🛠️ **Spring Validation**
- 🪵 **SLF4J / Logback**
- 🧪 **Postman** (para pruebas de API)

---

✨ Autor

- Bastián Cortés
- 📧 Desarrollador Backend - Spring Boot & Java
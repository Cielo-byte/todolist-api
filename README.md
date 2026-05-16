# To-Do List API REST — Spring Boot + JWT + PostgreSQL
 
API REST para gestión de tareas personales con autenticación mediante JWT, donde cada usuario solo puede ver y administrar sus propias tareas.

---

## Tecnologías usadas

- Java | 17 o 21
- Spring Boot | 3.2.x
- Spring Security | 6.x
- JWT (jjwt) | 0.11.5 
- PostgreSQL | 15+ 
- Lombok | Latest 
- Maven | 3.9+ 

---

## Endpoints disponibles

### 🔓 Autenticación — públicos (no requieren token)

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auth/registro` | Registrar nuevo usuario |
| POST | `/api/auth/login` | Iniciar sesión |

### 🔒 Tareas — requieren token JWT

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/tareas` | Listar tareas del usuario |
| POST | `/api/tareas` | Crear nueva tarea |
| PUT | `/api/tareas/{id}/completar` | Marcar tarea como completada |
| DELETE | `/api/tareas/{id}` | Eliminar una tarea |

---

## Pruebas en Postman

### 1. Registro

**POST** `http://localhost:8080/api/auth/registro`

```json
{
  "nombre": "Maricielo",
  "email": "maricielo@test.com",
  "password": "123456"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### 2. Login

**POST** `http://localhost:8080/api/auth/login`

```json
{
  "email": "maricielo@test.com",
  "password": "123456"
}
```

---

### 3. Crear tarea (con token)

**POST** `http://localhost:8080/api/tareas`  
**Header:** `Authorization: Bearer <token>`

```json
{
  "titulo": "Tarea 01",
  "descripcion": "Terminar el proyecto To-Do List"
}
```

---

### 4. Listar tareas (con token)

**GET** `http://localhost:8080/api/tareas`  
**Header:** `Authorization: Bearer <token>`

---

### 5. Completar tarea (con token)

**PUT** `http://localhost:8080/api/tareas/1/completar`  
**Header:** `Authorization: Bearer <token>`

---

### 6. Eliminar tarea (con token)

**DELETE** `http://localhost:8080/api/tareas/1`  
**Header:** `Authorization: Bearer <token>`

---

## Estructura del proyecto

```
src/main/java/com/maricielo/todolist/
│
├── config/
│   ├── JwtUtil.java          # Generación y validación de tokens
│   ├── JwtFilter.java        # Interceptor de requests
│   └── SecurityConfig.java   # Configuración de Spring Security
│
├── controller/
│   ├── AuthController.java   # Endpoints de registro y login
│   └── TareaController.java  # Endpoints CRUD de tareas
│
├── dto/
│   ├── AuthRequest.java
│   └── TareaRequest.java
│
├── entity/
│   ├── Usuario.java
│   └── Tarea.java
│
├── repository/
│   ├── UsuarioRepository.java
│   └── TareaRepository.java
│
└── service/
    └── UsuarioService.java
```

---

## La autenticación funciona de esta manera:

1. El usuario se registra o hace login → recibe un **token JWT**
2. Ese token se envía en cada request como header: `Authorization: Bearer <token>`
3. El `JwtFilter` intercepta el request, valida el token y autentica al usuario
4. Cada usuario solo accede a **sus propias tareas**

# Software Engineer API 🚀

Una aplicación REST desarrollada con **Spring Boot 3.5.7** para gestionar ingenieros de software y sus tecnologías asociadas. Proporciona un API completo para crear, consultar y gestionar datos de ingenieros de software, utilizando PostgreSQL como base de datos.

## 📋 Tabla de Contenidos

- [Requisitos Previos](#requisitos-previos)
- [Instalación y Configuración Local](#instalación-y-configuración-local)
- [Ejecución de la Aplicación](#ejecución-de-la-aplicación)
- [Conexión a la Base de Datos](#conexión-a-la-base-de-datos)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Endpoints API](#endpoints-api)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Arquitectura](#arquitectura)
- [Testing](#testing)
- [Solución de Problemas](#solución-de-problemas)

## 🔧 Requisitos Previos

- **Java**: 21 o superior
- **Maven**: 3.6 o superior
- **Docker y Docker Compose**: Para ejecutar PostgreSQL
- **Git**: Para clonar el repositorio
- **Editor/IDE**: IntelliJ IDEA, VS Code o tu editor de preferencia

Verifica la instalación con los siguientes comandos:

```bash
java --version
mvn --version
docker --version
docker-compose --version
```

## 📦 Instalación y Configuración Local

### 1. Clonar el repositorio

```bash
git clone https://github.com/pacool84/Spring-Boot-AmigosCode-Course.git
cd spring-boot
```

### 2. Instalar dependencias Maven

```bash
mvn clean install
```

Este comando descargará todas las dependencias necesarias definidas en `pom.xml`.

### 3. Iniciar los servicios con Docker Compose

Antes de ejecutar la aplicación, asegúrate de que tienes Docker Compose ejecutándose. Desde la raíz del proyecto:

```bash
docker-compose up -d
```

Este comando inicia un contenedor PostgreSQL con las siguientes características:

- **Contenedor**: `postgres-spring-boot`
- **Base de datos**: `amigos`
- **Usuario**: `amigoscode`
- **Contraseña**: `password`
- **Puerto**: `5332` (mapeado desde el puerto `5432` del contenedor)

Para verificar que el contenedor está ejecutándose:

```bash
docker-compose ps
```

## 🚀 Ejecución de la Aplicación

### Opción 1: Usando Maven desde la terminal

```bash
mvn spring-boot:run
```

### Opción 2: Compilar y ejecutar directamente

```bash
mvn clean package
java -jar target/spring-boot-0.0.1-SNAPSHOT.jar
```

### Opción 3: Desde tu IDE (IntelliJ IDEA)

1. Abre el proyecto en IntelliJ
2. Haz clic derecho en la clase `Application.java`
3. Selecciona **Run 'Application'**

Una vez que la aplicación se inicie, deberías ver un mensaje similar a:

```
Started Application in X.XXX seconds
```

La aplicación estará disponible en: `http://localhost:8080`

## 🗄️ Conexión a la Base de Datos

### Verificar que PostgreSQL está ejecutándose

```bash
docker-compose ps
```

Deberías ver el contenedor `postgres-spring-boot` en estado `Up`.

### Acceder a PostgreSQL desde la terminal

Si necesitas acceder directamente a la base de datos PostgreSQL para ejecutar consultas SQL o administrar datos:

#### Paso 1: Acceder al contenedor PostgreSQL

```bash
docker exec -it postgres-spring-boot bash
```

#### Paso 2: Conectar a psql (PostgreSQL CLI)

```bash
psql -U amigoscode
```

#### Paso 3: Conectar a la base de datos 'amigos'

```bash
\c amigos
```

Ahora puedes ejecutar comandos SQL. Algunos ejemplos útiles:

```sql
-- Ver todas las tablas
\dt

-- Ver el contenido de la tabla software_engineer
SELECT * FROM software_engineer;

-- Ver estructura de una tabla
\d software_engineer

-- Insertar un registro de prueba
INSERT INTO software_engineer (name, tech_stack) VALUES ('Juan Pérez', 'Java, Spring Boot, PostgreSQL');

-- Actualizar un registro
UPDATE software_engineer SET tech_stack = 'Java, Spring, Docker' WHERE id = 1;

-- Salir de psql
\q
```

#### Salir del contenedor

```bash
exit
```

### Configuración de conexión

La aplicación se conecta automáticamente a PostgreSQL mediante la configuración en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5332/amigos
spring.datasource.username=amigoscode
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA - Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true
```

**Explicación de las propiedades**:

- `spring.datasource.url`: URL de conexión a PostgreSQL (localhost:5332 es el puerto expuesto)
- `spring.datasource.username`: Usuario de base de datos
- `spring.datasource.password`: Contraseña de base de datos
- `spring.jpa.hibernate.ddl-auto=create-drop`: Crea las tablas al iniciar y las elimina al cerrar (útil para desarrollo)
- `spring.jpa.show-sql=true`: Muestra las queries SQL en los logs

### Detener los servicios Docker

```bash
docker-compose down
```

Si deseas eliminar también los volúmenes (borrará los datos):

```bash
docker-compose down -v
```

## 📁 Estructura del Proyecto

```
spring-boot/
├── src/
│   ├── main/
│   │   ├── java/com/amigoscode/
│   │   │   ├── Application.java              # Clase principal - punto de entrada
│   │   │   ├── SoftwareEngineer.java         # Entidad JPA
│   │   │   ├── SoftwareEngineerController.java # Controlador REST
│   │   │   ├── SoftwareEngineerService.java  # Lógica de negocio
│   │   │   └── SoftwareEngineerRepository.java # Acceso a datos
│   │   └── resources/
│   │       ├── application.properties         # Configuración de la aplicación
│   │       ├── static/                        # Archivos estáticos (CSS, JS)
│   │       └── templates/                     # Plantillas HTML
│   │
│   └── test/
│       └── java/com/amigoscode/
│           └── ApplicationTests.java          # Tests unitarios
│
├── docker-compose.yml                         # Configuración de Docker
├── pom.xml                                    # Dependencias Maven
├── mvnw y mvnw.cmd                            # Maven Wrapper
└── Readme.md                                  # Este archivo
```

### Descripción de los componentes principales

#### 🏢 SoftwareEngineer.java (Entidad JPA)

Representa a un ingeniero de software en la base de datos.

**Atributos**:

- `id`: Identificador único (generado automáticamente con `IDENTITY`)
- `name`: Nombre del ingeniero
- `techStack`: Tecnologías que domina

#### 🎮 SoftwareEngineerController.java (Controlador REST)

Gestiona las peticiones HTTP y las mapea a los métodos del servicio.

**Ruta base**: `/api/v1/software-engineers`

**Métodos**:

- `GET /` - Obtener todos los ingenieros
- `GET /{id}` - Obtener un ingeniero por ID
- `POST /` - Crear un nuevo ingeniero

#### ⚙️ SoftwareEngineerService.java (Servicio)

Contiene la lógica de negocio de la aplicación.

**Responsabilidades**:

- Validaciones de negocio
- Llamadas a la base de datos a través del repositorio
- Manejo de excepciones
- Transformación de datos

#### 💾 SoftwareEngineerRepository.java (Repositorio)

Interfaz que extiende `JpaRepository` para operaciones CRUD automáticas con la base de datos.

#### 🌐 Application.java (Aplicación Principal)

Punto de entrada de la aplicación con anotación `@SpringBootApplication`.

**Endpoints**:

- `GET /` - Retorna un saludo con la hora actual del servidor

## 🔌 Endpoints API

La API proporciona los siguientes endpoints para gestionar ingenieros de software:

### 1. Obtener todos los ingenieros

```http
GET http://localhost:8080/api/v1/software-engineers
```

**Respuesta (200 OK)**:

```json
[
  {
    "id": 1,
    "name": "Juan Pérez",
    "techStack": "Java, Spring Boot, PostgreSQL"
  },
  {
    "id": 2,
    "name": "María García",
    "techStack": "Python, Django, MySQL"
  }
]
```

### 2. Obtener un ingeniero por ID

```http
GET http://localhost:8080/api/v1/software-engineers/{id}
```

**Ejemplo**:

```http
GET http://localhost:8080/api/v1/software-engineers/1
```

**Respuesta (200 OK)**:

```json
{
  "id": 1,
  "name": "Juan Pérez",
  "techStack": "Java, Spring Boot, PostgreSQL"
}
```

**Respuesta (500 Error)**: Si el ingeniero no existe

```
Software Engineer with id 1 does not exist.
```

### 3. Crear un nuevo ingeniero

```http
POST http://localhost:8080/api/v1/software-engineers
Content-Type: application/json

{
  "name": "Carlos López",
  "techStack": "JavaScript, React, Node.js"
}
```

**Respuesta (200 OK)**: Sin contenido (se guarda en la BD)

### 4. Endpoint de prueba

```http
GET http://localhost:8080/
```

**Respuesta (200 OK)**:

```
Hello — My Friend Spring Boot service. Server time: 2025-11-30 14:23:45 CET
```

### Ejemplos con curl

```bash
# Obtener todos los ingenieros
curl http://localhost:8080/api/v1/software-engineers

# Obtener un ingeniero específico
curl http://localhost:8080/api/v1/software-engineers/1

# Crear un nuevo ingeniero
curl -X POST http://localhost:8080/api/v1/software-engineers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ana Martínez",
    "techStack": "Go, Rust, Docker"
  }'

# Endpoint de prueba
curl http://localhost:8080/
```

### Usar el archivo requests.http

El proyecto incluye un archivo `requests.http` que puedes usar en:

- **IntelliJ IDEA**: REST Client integrado
- **VS Code**: Extensión "REST Client" de Huachao Mao

Abre el archivo y haz clic en "Send Request" para probar cada endpoint.

## 🛠️ Tecnologías Utilizadas

| Tecnología              | Versión | Descripción                                             |
| ----------------------- | ------- | ------------------------------------------------------- |
| **Java**                | 21      | Lenguaje de programación compilado                      |
| **Spring Boot**         | 3.5.7   | Framework para crear aplicaciones Java productivas      |
| **Spring Data JPA**     | 3.5.7   | Abstracción de alto nivel para operaciones con BD       |
| **PostgreSQL**          | Latest  | Sistema gestor de bases de datos relacional robusto     |
| **Maven**               | 3.6+    | Gestor de dependencias y automatización de construcción |
| **Jakarta Persistence** | 3.5.7   | Especificación JPA estándar de Java                     |
| **Docker**              | Latest  | Containerización para ejecutar servicios aislados       |
| **Docker Compose**      | Latest  | Orquestación de múltiples contenedores                  |

### Dependencias Maven incluidas

```xml
<!-- Spring Boot Starter Web: incluye Spring MVC y Tomcat -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Data JPA: ORM y repositorios -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- PostgreSQL JDBC Driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Spring Boot Test: soporte para testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

## 🏗️ Arquitectura

La aplicación sigue una **arquitectura de capas** (Layered Architecture):

```
┌─────────────────────────────────────┐
│   REST Controller                   │
│ (SoftwareEngineerController)        │  ← Capa de Presentación
│   ↕ Maneja peticiones HTTP          │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   Service Layer                     │
│ (SoftwareEngineerService)           │  ← Capa de Lógica de Negocio
│   ↕ Lógica, validaciones, reglas    │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   Repository Layer                  │
│ (SoftwareEngineerRepository)        │  ← Capa de Persistencia
│   ↕ Operaciones CRUD                │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   PostgreSQL Database               │
│   (Docker Container)                │  ← Capa de Datos
└─────────────────────────────────────┘
```

### Flujo de una petición HTTP

1. **Cliente** → Envía una petición HTTP al endpoint (ej: POST /api/v1/software-engineers)
2. **Controller** → Recibe la petición, valida parámetros
3. **Service** → Procesa la lógica de negocio, validaciones adicionales
4. **Repository** → Interactúa con JPA/Hibernate
5. **Database** → Ejecuta la operación SQL (INSERT, SELECT, UPDATE, DELETE)
6. **Response** → Se devuelve la respuesta al cliente (JSON)

### Ventajas de esta arquitectura

- **Separación de responsabilidades**: Cada capa tiene un rol específico
- **Reutilización de código**: El Service puede ser usado por múltiples Controllers
- **Testeable**: Fácil escribir tests unitarios en cada capa
- **Mantenible**: Cambios en BD no afectan al Controller
- **Escalable**: Fácil agregar nuevas funcionalidades

## ✅ Testing

### Ejecutar todos los tests

```bash
mvn test
```

### Ejecutar un test específico

```bash
mvn test -Dtest=ApplicationTests
```

### Tests disponibles

El proyecto incluye `ApplicationTests.java` en `src/test/java/com/amigoscode/` con tests unitarios.

Ejemplo de test adicional que puedes crear:

```java
@SpringBootTest
public class SoftwareEngineerServiceTests {

    @Autowired
    private SoftwareEngineerService service;

    @Autowired
    private SoftwareEngineerRepository repository;

    @Test
    void testGetAllSoftwareEngineers() {
        List<SoftwareEngineer> engineers = service.getAllSoftwareEngineers();
        assertNotNull(engineers);
    }

    @Test
    void testGetSoftwareEngineerById() {
        SoftwareEngineer engineer = new SoftwareEngineer();
        engineer.setName("Test Engineer");
        engineer.setTechStack("Test Stack");
        repository.save(engineer);

        SoftwareEngineer found = service.getSoftwareEngineerById(engineer.getId());
        assertEquals("Test Engineer", found.getName());
    }
}
```

## 🔍 Solución de Problemas

### Problema: Puerto 5332 ya está en uso

**Síntoma**: Error `Address already in use` cuando intentas ejecutar `docker-compose up`

**Solución**:

```bash
# Encontrar qué proceso usa el puerto (macOS/Linux)
lsof -i :5332

# Matar el proceso
kill -9 <PID>

# O cambiar el puerto en docker-compose.yml:
# ports:
#   - "5333:5432"  # Cambia 5332 a otro puerto disponible
```

### Problema: Base de datos no se conecta

**Síntoma**: Error `Connection refused` o `Connection timeout`

**Solución**:

1. Verifica que Docker está ejecutándose:

```bash
docker --version
```

2. Verifica que el contenedor está arriba:

```bash
docker-compose ps
```

3. Revisa los logs del contenedor:

```bash
docker-compose logs db
```

4. Reinicia los servicios:

```bash
docker-compose restart
```

### Problema: Error de compilación en Java

**Síntoma**: `error: incompatible Java versions` o `Cannot find symbol`

**Solución**:

```bash
# Verifica que usas Java 21
java --version

# Limpia el proyecto
mvn clean

# Reinstala las dependencias
mvn install
```

### Problema: La aplicación se inicia pero no puede conectarse a BD

**Síntoma**: Logs muestran errores de conexión a PostgreSQL

**Solución**: Verifica la configuración en `application.properties`:

- URL correcta: `jdbc:postgresql://localhost:5332/amigos`
- Usuario correcto: `amigoscode`
- Contraseña correcta: `password`
- PostgreSQL está corriendo: `docker-compose ps`

### Problema: Error "Software Engineer with id X does not exist"

**Síntoma**: Al llamar a `GET /api/v1/software-engineers/1`, retorna error 500

**Causa**: Es un comportamiento esperado cuando el ID no existe en la BD.

**Solución**:

1. Verifica que el ingeniero existe en la BD
2. O crea uno nuevo primero:

```bash
curl -X POST http://localhost:8080/api/v1/software-engineers \
  -H "Content-Type: application/json" \
  -d '{"name": "Test", "techStack": "Java"}'
```

### Problema: Los datos desaparecen al reiniciar la app

**Síntoma**: Las filas insertadas no persisten

**Causa**: `spring.jpa.hibernate.ddl-auto=create-drop` recrea las tablas cada inicio

**Solución**: Para producción, cambia en `application.properties`:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

## 📝 Notas adicionales

### Desarrollo vs Producción

Configuración actual (desarrollo):

- `ddl-auto=create-drop`: Tablas se crean y eliminan en cada inicio
- Logs SQL habilitados: Verás todos los queries
- BD se reinicia: Datos no persisten

Para producción:

- `ddl-auto=validate`: Solo valida la estructura
- Logs SQL deshabilitados
- BD persistente con backups

### Mejoras futuras

- [ ] Agregar validaciones más robustas en el Service
- [ ] Implementar endpoints DELETE y PUT
- [ ] Agregar paginación en GET /software-engineers
- [ ] Implementar autenticación y autorización
- [ ] Agregar documentación Swagger/OpenAPI
- [ ] Crear más tests unitarios e integración
- [ ] Implementar manejo global de excepciones

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto es parte del curso de AmigosCode de Spring Boot.

## 👨‍💻 Autor

- **Usuario**: pacool84
- **Repositorio**: [Spring-Boot-AmigosCode-Course](https://github.com/pacool84/Spring-Boot-AmigosCode-Course)

---

**Última actualización**: 30 de noviembre de 2025

**Estado**: ✅ En desarrollo y funcionando correctamente

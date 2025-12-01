# 🚀 Guía Rápida - Quick Start

Esta es la guía más concisa para empezar rápidamente.

## ⚡ En 5 minutos

### 1. Inicia Docker (1 min)

```bash
cd ~/Desktop/spring-boot
docker-compose up -d
```

### 2. Inicia la app (2 min)

```bash
mvn spring-boot:run
```

Espera a ver: `Started Application in X seconds`

### 3. Prueba (2 min)

```bash
# Endpoint básico
curl http://localhost:8080/

# Ver datos
curl http://localhost:8080/api/v1/software-engineers

# Crear ingeniero
curl -X POST http://localhost:8080/api/v1/software-engineers \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","techStack":"Java"}'
```

---

## 🗂️ Comandos más usados

### PostgreSQL

```bash
# Acceder a la BD
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos

# Ver datos
docker exec postgres-spring-boot psql -U amigoscode -d amigos -c "SELECT * FROM software_engineer;"

# Salir de psql
\q

# Salir del contenedor
exit
```

### Docker Compose

```bash
# Ver estado
docker-compose ps

# Ver logs
docker-compose logs db

# Reiniciar
docker-compose restart

# Detener
docker-compose down

# Iniciar
docker-compose up -d
```

### Maven

```bash
# Ejecutar
mvn spring-boot:run

# Compilar
mvn clean package

# Tests
mvn test

# Limpiar
mvn clean
```

### curl (API)

```bash
# GET todos
curl http://localhost:8080/api/v1/software-engineers | jq

# GET por ID
curl http://localhost:8080/api/v1/software-engineers/1 | jq

# POST
curl -X POST http://localhost:8080/api/v1/software-engineers \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","techStack":"Java,Spring"}'

# Pretty print (requiere jq)
curl URL | jq
```

---

## 📍 URLs importantes

| Recurso  | URL                                                  |
| -------- | ---------------------------------------------------- |
| App      | http://localhost:8080                                |
| API base | http://localhost:8080/api/v1/software-engineers      |
| Todos    | http://localhost:8080/api/v1/software-engineers      |
| Por ID   | http://localhost:8080/api/v1/software-engineers/{id} |
| BD       | localhost:5332 (user: amigoscode, pass: password)    |

---

## 🔐 Credenciales

- **Usuario BD**: amigoscode
- **Contraseña BD**: password
- **Base de datos**: amigos
- **Host**: localhost
- **Puerto**: 5332

---

## 📁 Archivos clave

| Archivo                           | Propósito                |
| --------------------------------- | ------------------------ |
| `application.properties`          | Config de BD y Hibernate |
| `docker-compose.yml`              | Config de PostgreSQL     |
| `pom.xml`                         | Dependencias Maven       |
| `Application.java`                | Punto de entrada         |
| `SoftwareEngineer.java`           | Entidad                  |
| `SoftwareEngineerController.java` | API endpoints            |
| `SoftwareEngineerService.java`    | Lógica                   |
| `SoftwareEngineerRepository.java` | BD acceso                |

---

## ⚠️ Problemas comunes

| Problema             | Solución                                      |
| -------------------- | --------------------------------------------- |
| "Connection refused" | `docker-compose up -d`                        |
| "Port 5332 in use"   | `lsof -i :5332` y `kill -9 PID`               |
| "Cannot find psql"   | `docker exec -it postgres-spring-boot bash`   |
| Datos desaparecen    | Normal con `create-drop`. Cambiar a `update`  |
| Cambios no aparecen  | Reiniciar app: Ctrl+C + `mvn spring-boot:run` |

---

## 📋 Checklist de startup

- [ ] Docker corre: `docker --version`
- [ ] PostgreSQL arriba: `docker-compose ps`
- [ ] App se inicia: `mvn spring-boot:run`
- [ ] API responde: `curl http://localhost:8080/`

---

## 💡 Tips

- **Con jq**: `curl URL | jq` para pretty print
- **Copiar comando**: Usa el botón copiar en los ejemplos
- **Persistencia**: Cambiar `create-drop` a `update` en application.properties
- **Logs**: `docker-compose logs -f db` para ver en tiempo real

---

## 📚 Documentación completa

- `README.md` - Documentación completa
- `DATABASE_SETUP.md` - Detalles de BD y conexión
- `PRACTICAL_EXAMPLES.md` - Ejemplos paso a paso

---

**¿Primer vez?** Lee este archivo primero. Luego consulta README.md para detalles.

**Última actualización**: 30 de noviembre de 2025

# Guía Completa: Configuración y Conexión a PostgreSQL 🗄️

Esta guía detalla todo lo que necesitas saber sobre cómo ejecutar y conectarte a la base de datos PostgreSQL que viene con este proyecto Spring Boot.

## 📋 Índice

1. [Requisitos](#requisitos)
2. [Iniciar PostgreSQL con Docker](#iniciar-postgresql-con-docker)
3. [Verificar la conexión](#verificar-la-conexión)
4. [Acceder a la base de datos](#acceder-a-la-base-de-datos)
5. [Comandos SQL útiles](#comandos-sql-útiles)
6. [Configuración de la aplicación](#configuración-de-la-aplicación)
7. [Troubleshooting](#troubleshooting)

## ✅ Requisitos

- Docker instalado y ejecutándose
- Docker Compose instalado
- Terminal/Bash
- El archivo `docker-compose.yml` en la raíz del proyecto

Verifica que Docker está instalado:

```bash
docker --version
docker-compose --version
```

## 🚀 Iniciar PostgreSQL con Docker

### Paso 1: Levanta los servicios

Desde la raíz del proyecto (donde está `docker-compose.yml`):

```bash
docker-compose up -d
```

**¿Qué hace este comando?**

- `-d`: Ejecuta en modo "detached" (background)
- Crea un contenedor PostgreSQL basado en la configuración de `docker-compose.yml`

### Paso 2: Verifica que el contenedor está ejecutándose

```bash
docker-compose ps
```

Deberías ver algo como:

```
NAME                COMMAND              SERVICE     STATUS      PORTS
postgres-spring-boot   "docker-entrypoint..."   db          Up 4 seconds   0.0.0.0:5332->5432/tcp
```

**Detalles importantes**:

- NAME: `postgres-spring-boot` (nombre del contenedor)
- SERVICE: `db` (nombre del servicio en docker-compose.yml)
- STATUS: `Up` (debería estar ejecutándose)
- PORTS: `0.0.0.0:5332->5432/tcp` (puerto local 5332 mapea al puerto interno 5432)

### Ver logs del contenedor (opcional)

Si algo no funciona, revisa los logs:

```bash
docker-compose logs db
```

## ✔️ Verificar la conexión

### Método 1: Desde la terminal usando Docker

```bash
# Acceder al contenedor
docker exec -it postgres-spring-boot bash

# Verificar que psql está disponible
psql --version
```

### Método 2: Usando un cliente SQL (opcional)

Puedes usar herramientas gráficas como:

- **DBeaver** (recomendado)
- **PgAdmin**
- **DataGrip**

Conexión:

- **Host**: localhost
- **Puerto**: 5332
- **Base de datos**: amigos
- **Usuario**: amigoscode
- **Contraseña**: password

## 📂 Acceder a la Base de Datos

### Acceso por línea de comandos

#### Paso 1: Abre una sesión bash en el contenedor

```bash
docker exec -it postgres-spring-boot bash
```

Ahora estás dentro del contenedor. El prompt cambiará algo como:

```
root@a1b2c3d4e5f6:/#
```

#### Paso 2: Conecta a PostgreSQL usando psql

```bash
psql -U amigoscode
```

Deberías ver el prompt de psql:

```
psql (15.5 (Debian 15.5-1.pgdg120+1))
Type "help" for help.

amigoscode=#
```

#### Paso 3: Selecciona la base de datos 'amigos'

```bash
\c amigos
```

Verás:

```
You are now connected to database "amigos" as user "amigoscode".
amigoscode=#
```

¡Ahora estás conectado a la BD! 🎉

#### Paso 4: Salir de psql

```bash
\q
```

Volverás al bash del contenedor.

#### Paso 5: Salir del contenedor

```bash
exit
```

Volverás a tu terminal local.

## 💻 Comandos SQL útiles

Una vez dentro de `psql` en la base de datos `amigos`:

### Ver tablas

```sql
\dt
```

Lista todas las tablas. Deberías ver `software_engineer` u otras si existen.

### Ver estructura de una tabla

```sql
\d software_engineer
```

Muestra los campos, tipos de datos e índices de la tabla.

### Ver todas las bases de datos

```sql
\l
```

Muestra todas las bases de datos disponibles.

### Ver usuarios

```sql
\du
```

Muestra los usuarios de PostgreSQL.

### Consultar datos

```sql
-- Ver todos los ingenieros
SELECT * FROM software_engineer;

-- Ver solo nombres
SELECT name FROM software_engineer;

-- Ver con filtro
SELECT * FROM software_engineer WHERE id = 1;

-- Contar registros
SELECT COUNT(*) FROM software_engineer;
```

### Insertar datos

```sql
INSERT INTO software_engineer (name, tech_stack)
VALUES ('Juan Pérez', 'Java, Spring Boot, PostgreSQL');
```

### Actualizar datos

```sql
UPDATE software_engineer
SET tech_stack = 'Java, Spring, Docker, Kubernetes'
WHERE id = 1;
```

### Eliminar datos

```sql
DELETE FROM software_engineer WHERE id = 1;
```

### Crear consultas complejas

```sql
-- Buscar ingenieros que dominen Java
SELECT * FROM software_engineer
WHERE tech_stack LIKE '%Java%';

-- Contar por longitud de nombre
SELECT length(name) as nombre_length, COUNT(*) as cantidad
FROM software_engineer
GROUP BY length(name);
```

### Ver el historial de comandos

```sql
\s
```

### Guardar una consulta en un archivo

```sql
\o /tmp/resultado.txt
SELECT * FROM software_engineer;
\o
```

## ⚙️ Configuración de la aplicación

La conexión de Spring Boot a PostgreSQL está configurada en:

**Archivo**: `src/main/resources/application.properties`

```properties
# Conexión a la base de datos
spring.datasource.url=jdbc:postgresql://localhost:5332/amigos
spring.datasource.username=amigoscode
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true
```

### Explicación de cada propiedad:

| Propiedad                                    | Valor                                     | Descripción                                                |
| -------------------------------------------- | ----------------------------------------- | ---------------------------------------------------------- |
| `spring.datasource.url`                      | `jdbc:postgresql://localhost:5332/amigos` | URL de conexión. El puerto 5332 es el expuesto por Docker  |
| `spring.datasource.username`                 | `amigoscode`                              | Usuario de BD definido en docker-compose.yml               |
| `spring.datasource.password`                 | `password`                                | Contraseña definida en docker-compose.yml                  |
| `spring.datasource.driver-class-name`        | `org.postgresql.Driver`                   | Driver JDBC para PostgreSQL                                |
| `spring.jpa.hibernate.ddl-auto`              | `create-drop`                             | Crea tablas al iniciar, las elimina al cerrar (desarrollo) |
| `spring.jpa.properties.hibernate.format_sql` | `true`                                    | Formatea SQL en los logs para mejor legibilidad            |
| `spring.jpa.show-sql`                        | `true`                                    | Muestra todas las queries SQL ejecutadas                   |

## 🔧 Troubleshooting

### Problema 1: "Error: Connection refused"

**Síntoma**: La aplicación no puede conectarse a la BD

**Causas posibles**:

1. Docker no está ejecutándose
2. El contenedor PostgreSQL no se inició
3. El puerto 5332 está en uso

**Soluciones**:

```bash
# Verifica que Docker está corriendo
docker --version

# Verifica que el contenedor está arriba
docker-compose ps

# Reinicia Docker Compose
docker-compose restart

# Verifica que el puerto 5332 no está en uso
lsof -i :5332

# Si está en uso, puedes cambiar el puerto en docker-compose.yml:
# ports:
#   - "5333:5432"  # Cambia a otro puerto
```

### Problema 2: "Error: Cannot create a new instance of principal"

**Síntoma**: Error con las credenciales

**Solución**: Verifica que las credenciales en `application.properties` coinciden con las de `docker-compose.yml`:

- Usuario: `amigoscode` ✓
- Contraseña: `password` ✓
- BD: `amigos` ✓

### Problema 3: Datos desaparecen al reiniciar la app

**Síntoma**: Las filas que inserté desaparecieron

**Causa**: La configuración `spring.jpa.hibernate.ddl-auto=create-drop` recrea las tablas en cada inicio

**Solución para desarrollo**: Es comportamiento normal. Si quieres persistencia, cambia en `application.properties`:

```properties
spring.jpa.hibernate.ddl-auto=update
```

### Problema 4: "FATAL: role 'amigoscode' does not exist"

**Solución**: Recrea los contenedores:

```bash
docker-compose down
docker-compose up -d
```

### Problema 5: Puerto 5332 ya está en uso

**Solución**:

```bash
# Identifica qué está usando el puerto
lsof -i :5332

# Opción A: Matar el proceso
kill -9 <PID>

# Opción B: Cambiar puerto en docker-compose.yml
# De:
#   ports:
#     - "5332:5432"
# A:
#   ports:
#     - "5333:5432"
# Y actualizar application.properties
```

### Problema 6: "Access denied for user"

**Síntoma**: Error al conectar con credenciales incorrectas

**Verificación**:

```bash
# Conecta con las credenciales correctas desde el contenedor
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos
```

### Problema 7: Ver logs detallados

```bash
# Ver logs de Docker Compose
docker-compose logs db

# Ver logs en tiempo real
docker-compose logs -f db

# Ver logs de la aplicación Spring Boot
# (busca líneas con "datasource" o "postgresql")
```

## 📊 Monitoreo de la BD

### Ver conexiones activas

```bash
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos -c "SELECT * FROM pg_stat_activity;"
```

### Ver tamaño de la BD

```bash
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos -c "SELECT pg_size_pretty(pg_database_size('amigos'));"
```

### Backup de la BD

```bash
# Crear backup
docker exec -it postgres-spring-boot pg_dump -U amigoscode -d amigos > backup.sql

# Restaurar backup
cat backup.sql | docker exec -i postgres-spring-boot psql -U amigoscode -d amigos
```

## 🛑 Detener y limpiar

### Detener los contenedores (sin eliminar datos)

```bash
docker-compose stop
```

### Reiniciar los contenedores

```bash
docker-compose start
```

### Detener y eliminar contenedores (SIN eliminar volúmenes)

```bash
docker-compose down
```

### Detener, eliminar contenedores Y eliminar datos (⚠️ CUIDADO)

```bash
docker-compose down -v
```

Este comando elimina TODO, incluyendo datos persistidos en la BD.

## 📚 Referencias útiles

### Documentación oficial

- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Docker PostgreSQL Image](https://hub.docker.com/_/postgres)
- [Spring Boot Database Initialization](https://spring.io/guides/gs/accessing-data-jpa/)

### Comandos psql frecuentes

| Comando        | Descripción                 |
| -------------- | --------------------------- |
| `\l`           | Listar bases de datos       |
| `\c nombre_bd` | Conectar a una BD           |
| `\dt`          | Listar tablas               |
| `\d tabla`     | Ver estructura de una tabla |
| `\q`           | Salir de psql               |
| `\h`           | Ayuda SQL                   |
| `\?`           | Ayuda de comandos psql      |

---

**Última actualización**: 30 de noviembre de 2025

**¿Preguntas?** Consulta el README.md principal o el archivo Troubleshooting arriba.

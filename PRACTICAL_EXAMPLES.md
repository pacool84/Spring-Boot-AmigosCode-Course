# Ejemplos Prácticos de Uso 📚

Este documento contiene ejemplos prácticos para ejecutar el proyecto y trabajar con la API y la base de datos.

## 📋 Índice

1. [Iniciar el Proyecto Completo](#iniciar-el-proyecto-completo)
2. [Ejemplos de API REST](#ejemplos-de-api-rest)
3. [Ejemplos con Base de Datos](#ejemplos-con-base-de-datos)
4. [Flujos de Trabajo Comunes](#flujos-de-trabajo-comunes)

## 🚀 Iniciar el Proyecto Completo

### Secuencia de inicio recomendada

**Terminal 1: Inicia PostgreSQL**

```bash
cd ~/Desktop/spring-boot
docker-compose up -d
docker-compose ps  # Verifica que está arriba
```

**Terminal 2: Inicia la aplicación Spring Boot**

```bash
cd ~/Desktop/spring-boot
mvn spring-boot:run
```

Espera a ver:

```
Started Application in X.XXX seconds
```

**Terminal 3: Prueba la API** (opcional)

```bash
# Endpoint de prueba
curl http://localhost:8080/

# Deberías ver algo como:
# Hello — My Friend Spring Boot service. Server time: 2025-11-30 14:23:45 CET
```

**Terminal 4: Acceso a Base de Datos** (opcional)

```bash
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos
```

Ahora tienes todo funcionando en paralelo. 🎉

---

## 🔌 Ejemplos de API REST

### Ejemplo 1: Crear un ingeniero

```bash
curl -X POST http://localhost:8080/api/v1/software-engineers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan Pérez García",
    "techStack": "Java, Spring Boot, PostgreSQL, Docker"
  }'
```

**Respuesta esperada**: 200 OK (sin cuerpo)

### Ejemplo 2: Obtener todos los ingenieros

```bash
curl http://localhost:8080/api/v1/software-engineers | jq
```

**Respuesta esperada**:

```json
[
  {
    "id": 1,
    "name": "Juan Pérez García",
    "techStack": "Java, Spring Boot, PostgreSQL, Docker"
  }
]
```

### Ejemplo 3: Obtener un ingeniero específico

```bash
curl http://localhost:8080/api/v1/software-engineers/1 | jq
```

**Respuesta esperada**:

```json
{
  "id": 1,
  "name": "Juan Pérez García",
  "techStack": "Java, Spring Boot, PostgreSQL, Docker"
}
```

### Ejemplo 4: Crear múltiples ingenieros

```bash
# Ingeniero 1
curl -X POST http://localhost:8080/api/v1/software-engineers \
  -H "Content-Type: application/json" \
  -d '{"name": "María García López", "techStack": "Python, Django, MySQL"}'

# Ingeniero 2
curl -X POST http://localhost:8080/api/v1/software-engineers \
  -H "Content-Type: application/json" \
  -d '{"name": "Carlos Rodríguez", "techStack": "JavaScript, React, Node.js"}'

# Ingeniero 3
curl -X POST http://localhost:8080/api/v1/software-engineers \
  -H "Content-Type: application/json" \
  -d '{"name": "Ana Martínez", "techStack": "Go, Rust, Kubernetes"}'

# Ingeniero 4
curl -X POST http://localhost:8080/api/v1/software-engineers \
  -H "Content-Type: application/json" \
  -d '{"name": "Luis Fernández", "techStack": "C++, Embedded Systems, IoT"}'
```

### Ejemplo 5: Script de carga inicial

Crea un archivo `load-data.sh`:

```bash
#!/bin/bash

BASE_URL="http://localhost:8080/api/v1/software-engineers"

# Array de ingenieros
declare -a ENGINEERS=(
  '{"name":"Juan Pérez","techStack":"Java, Spring Boot, PostgreSQL"}'
  '{"name":"María García","techStack":"Python, Django, MySQL"}'
  '{"name":"Carlos Rodríguez","techStack":"JavaScript, React, Node.js"}'
  '{"name":"Ana Martínez","techStack":"Go, Rust, Docker"}'
  '{"name":"Luis Fernández","techStack":"C#, .NET, Azure"}'
  '{"name":"Elena López","techStack":"R, Python, Data Science"}'
  '{"name":"Miguel Santos","techStack":"PHP, Laravel, PostgreSQL"}'
)

echo "Cargando ingenieros..."

for engineer in "${ENGINEERS[@]}"; do
  curl -s -X POST "$BASE_URL" \
    -H "Content-Type: application/json" \
    -d "$engineer"
  echo "✓ Ingeniero creado: $engineer"
done

echo "Carga completada!"

# Mostrar todos
echo -e "\nTodos los ingenieros:"
curl -s "$BASE_URL" | jq
```

Ejecuta el script:

```bash
chmod +x load-data.sh
./load-data.sh
```

### Ejemplo 6: Usando el archivo requests.http

Crea o edita el archivo `requests.http`:

```http
### Variables
@baseUrl = http://localhost:8080
@contentType = application/json

### Endpoint de prueba
GET {{baseUrl}}/

### Obtener todos los ingenieros
GET {{baseUrl}}/api/v1/software-engineers

### Obtener ingeniero por ID
GET {{baseUrl}}/api/v1/software-engineers/1

### Crear nuevo ingeniero
POST {{baseUrl}}/api/v1/software-engineers
Content-Type: {{contentType}}

{
  "name": "Nuevo Ingeniero",
  "techStack": "Java, Spring, Docker"
}

### Crear ingeniero desde template
POST {{baseUrl}}/api/v1/software-engineers
Content-Type: {{contentType}}

{
  "name": "Test Ingeniero",
  "techStack": "Test Stack"
}
```

Si usas IntelliJ IDEA o VS Code (con REST Client), puedes hacer clic en "Send Request".

---

## 💾 Ejemplos con Base de Datos

### Ejemplo 1: Acceder a psql e inspeccionar

```bash
# Acceder al contenedor
docker exec -it postgres-spring-boot bash

# Conectar a psql
psql -U amigoscode

# Conectar a la BD amigos
\c amigos

# Ver tablas
\dt

# Ver estructura de software_engineer
\d software_engineer

# Ver todos los registros
SELECT * FROM software_engineer;

# Salir
\q
exit
```

### Ejemplo 2: Query directa sin entrar en psql

```bash
# Ver todos los ingenieros
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos -c "SELECT * FROM software_engineer;"

# Ver solo nombres
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos -c "SELECT name FROM software_engineer;"

# Contar registros
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos -c "SELECT COUNT(*) FROM software_engineer;"

# Ver con formato
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos -c "\x" -c "SELECT * FROM software_engineer;"
```

### Ejemplo 3: Script SQL

Crea un archivo `queries.sql`:

```sql
-- Consultas útiles para software_engineer

-- 1. Ver todos los ingenieros ordenados por nombre
SELECT id, name, tech_stack
FROM software_engineer
ORDER BY name;

-- 2. Buscar ingenieros que usan Java
SELECT name, tech_stack
FROM software_engineer
WHERE tech_stack ILIKE '%Java%';

-- 3. Contar ingenieros por longitud de tech_stack
SELECT length(tech_stack) as stack_length, COUNT(*) as quantity
FROM software_engineer
GROUP BY length(tech_stack)
ORDER BY stack_length;

-- 4. Ver último ID usado
SELECT MAX(id) FROM software_engineer;

-- 5. Listar solo tech_stacks únicos
SELECT DISTINCT tech_stack
FROM software_engineer
ORDER BY tech_stack;

-- 6. Ingenieros con más de 3 tecnologías
SELECT name,
       array_length(string_to_array(tech_stack, ','), 1) as tech_count,
       tech_stack
FROM software_engineer
WHERE array_length(string_to_array(tech_stack, ','), 1) > 3
ORDER BY tech_count DESC;

-- 7. Insertar datos de prueba
INSERT INTO software_engineer (name, tech_stack) VALUES
('Test 1', 'Java, Spring'),
('Test 2', 'Python, Django'),
('Test 3', 'Go, Rust');

-- 8. Actualizar múltiples registros
UPDATE software_engineer
SET tech_stack = tech_stack || ', Docker'
WHERE tech_stack NOT LIKE '%Docker%';

-- 9. Eliminar registros de prueba
DELETE FROM software_engineer
WHERE name LIKE 'Test%';

-- 10. Información de la tabla
\d+ software_engineer
```

Ejecuta el script:

```bash
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos -f /dev/stdin < queries.sql
```

---

## 🔄 Flujos de Trabajo Comunes

### Flujo 1: Crear y verificar datos

```bash
# Paso 1: Crear un ingeniero
curl -X POST http://localhost:8080/api/v1/software-engineers \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","techStack":"Java"}'

# Paso 2: Obtener todos (debería incluir el nuevo)
curl http://localhost:8080/api/v1/software-engineers | jq

# Paso 3: Obtener por ID
curl http://localhost:8080/api/v1/software-engineers/1 | jq

# Paso 4: Verificar en la BD
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos -c "SELECT * FROM software_engineer;"
```

### Flujo 2: Testing end-to-end

```bash
#!/bin/bash

echo "=== E2E Testing ==="

# Test 1: Endpoint raíz
echo "Test 1: GET /"
curl -s http://localhost:8080/ | head -c 50
echo -e "\n✓ Pass\n"

# Test 2: GET todos
echo "Test 2: GET /api/v1/software-engineers"
RESPONSE=$(curl -s http://localhost:8080/api/v1/software-engineers)
echo "$RESPONSE" | jq . > /dev/null && echo "✓ JSON válido" || echo "✗ JSON inválido"
echo ""

# Test 3: POST nuevo
echo "Test 3: POST /api/v1/software-engineers"
STATUS=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8080/api/v1/software-engineers \
  -H "Content-Type: application/json" \
  -d '{"name":"E2E Test","techStack":"Test"}')
[ "$STATUS" = "200" ] && echo "✓ Status 200" || echo "✗ Status $STATUS"
echo ""

# Test 4: GET por ID
echo "Test 4: GET /api/v1/software-engineers/1"
STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/api/v1/software-engineers/1)
[ "$STATUS" = "200" ] && echo "✓ Status 200" || echo "✗ Status $STATUS"
echo ""

# Test 5: DB connection
echo "Test 5: BD Connection"
docker exec postgres-spring-boot psql -U amigoscode -d amigos -c "SELECT COUNT(*) FROM software_engineer;" | tail -1 | grep -q '[0-9]'
[ $? -eq 0 ] && echo "✓ BD OK" || echo "✗ BD Error"

echo ""
echo "=== E2E Testing Completado ==="
```

### Flujo 3: Desarrollo local con recarga

**Terminal 1**: Inicia la app con Maven en modo watch

```bash
mvn spring-boot:run
```

**Terminal 2**: Haz cambios en el código

```bash
# Edita los archivos .java y guarda
# Maven detactará los cambios y recompilará
```

**Terminal 3**: Prueba con curl

```bash
# Después de cada cambio, prueba
curl http://localhost:8080/api/v1/software-engineers | jq
```

### Flujo 4: Debugging con logs

```bash
# Terminal 1: Mira los logs de BD
docker-compose logs -f db

# Terminal 2: Mira los logs de la app
mvn spring-boot:run 2>&1 | grep -E "(datasource|sql|Hibernate)"

# Terminal 3: Haz requests
curl http://localhost:8080/api/v1/software-engineers
```

---

## 📊 Monitoreo y Estadísticas

### Ver información de la conexión

```bash
# Conexiones activas a la BD
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos -c "SELECT pid, usename, state FROM pg_stat_activity;"

# Tamaño de la BD
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos -c "SELECT pg_size_pretty(pg_database_size('amigos'));"

# Número de conexiones
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos -c "SELECT COUNT(*) as connections FROM pg_stat_activity WHERE datname='amigos';"
```

### Performance de queries

```bash
# Habilitar EXPLAIN ANALYZE (dentro de psql)
docker exec -it postgres-spring-boot psql -U amigoscode -d amigos

-- Dentro de psql:
EXPLAIN ANALYZE SELECT * FROM software_engineer WHERE name LIKE '%Test%';
```

---

## 🐛 Debugging

### Ver logs de Spring Boot

```bash
# Busca líneas importantes
mvn spring-boot:run 2>&1 | grep -i "datasource\|hibernate\|error"
```

### Ver SQL ejecutado

Está habilitado en `application.properties`:

```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Verás algo como:

```sql
select seo1_0.id,seo1_0.name,seo1_0.tech_stack from software_engineer seo1_0
```

### Limpiar datos y empezar de nuevo

```bash
# Opción 1: Reinicia la app (borra BD por create-drop)
# En la terminal de Spring Boot: Ctrl+C
# Luego: mvn spring-boot:run

# Opción 2: Borra todo y reinicia Docker
docker-compose down
docker-compose up -d
```

---

## 📝 Checklist de verificación

Usa este checklist para verificar que todo funciona:

- [ ] Docker está instalado: `docker --version`
- [ ] Docker Compose está instalado: `docker-compose --version`
- [ ] PostgreSQL corre: `docker-compose ps`
- [ ] Conexión a BD: `docker exec -it postgres-spring-boot psql -U amigoscode -d amigos`
- [ ] Aplicación se inicia: `mvn spring-boot:run`
- [ ] Endpoint raíz responde: `curl http://localhost:8080/`
- [ ] API REST responde: `curl http://localhost:8080/api/v1/software-engineers`
- [ ] Puedo crear datos: `curl -X POST ...`
- [ ] Datos persisten en BD: `SELECT * FROM software_engineer;`

---

**Última actualización**: 30 de noviembre de 2025

**¿Problemas?** Consulta DATABASE_SETUP.md para troubleshooting detallado.

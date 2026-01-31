# Proyecto Integrador Final: API REST en Kotlin + Spring Boot + PostgreSQL (Docker)

## Objetivo
Backend completo en **Kotlin + Spring Boot** que implementa una API REST con 3 tablas relacionadas, siguiendo arquitectura **Controller → Service → Repository**, buenas prácticas de naming y con **100% de coverage en tests de la capa Service**.  
La solución se ejecuta localmente usando **Docker Compose** con una base de datos externa PostgreSQL y se documenta con una colección Postman.

---

## Tecnologías utilizadas
- Kotlin
- Spring Boot
- Spring Data JPA
- PostgreSQL (Docker)
- pgAdmin (gestión gráfica de la DB)
- JUnit 5 + Mockito
- JaCoCo (coverage report)
- Postman (colección de endpoints)

---

##  ¿Cómo levantar la DB con Docker Compose?

Para iniciar la base de datos PostgreSQL y la interfaz gráfica pgAdmin, debes estar en la carpeta raíz del proyecto (donde está el archivo docker-compose.yml) y ejecutar:

```bash
  docker compose up -d
```

## ¿Cómo ejecutar la app?

En la raíz del proyecto, ejecutar:

```bash
  ./gradlew bootRun
```

Esto iniciará la aplicación Spring Boot en el puerto 8080.
La API quedará disponible en http://localhost:8080.

## ¿Cómo correr tests?

Para ejecutar todos los tests de la capa Service (con JUnit 5 y Mockito):

```bash
  ./gradlew test
```

Esto validará casos exitosos, validaciones y errores definidos en los servicios.

## ¿Cómo ver coverage?

El proyecto está configurado con JaCoCo para generar reportes de cobertura.
Después de correr los tests, genera el reporte con:

```bash
  ./gradlew jacocoTestReport
```

El reporte HTML se almacena en la siguiente ruta:

```bash
  build/reports/jacoco/test/html/index.html
```

Abrir en el navegador para ver el detalle de cobertura por clase y método.

## Instrucciones de uso de la colección (Postman)

En el repositorio está el archivo postman_collection.json con todos los endpoints.

Para usarlo:

1. Abre Postman.

2. Ve a File → Import.

3. Selecciona el archivo postman_collection.json.

4. La colección aparecerá con todos los endpoints listos para probar.

IMPORTANTE: Antes de probar, asegúrate de que la aplicación esté corriendo (./gradlew bootRun) y la base de datos levantada (docker compose up -d).
Luego podrás ejecutar los requests (ejemplo: crear usuario, listar cursos, inscribir estudiantes) y ver las respuestas JSON directamente en Postman.
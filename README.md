# Pichincha challenge
Api rest desarrollado para solucionar el ejercicio de Bco Pichincha

**Para correr la api seguir los siguientes pasos:**

1. Clonar proyecto desde el repositorio.

2. Descargar las dependencias del build.gradle.

3. El proyecto fue desarrollado con flyway para la creacion de tablas y carga de datos iniciales, dentro del archivo: /resources/db/migration/V1.0__create_tables.sql se encuentran el script para creacion de tablas y carga de datos iniciales.

4. El proyecto se despliega en el puerto 8080 deacuerdo a la configuración en el archivo application.yml.

5. El contextPath de la app esta configurado con el valor /api.

**Para correr la APi dockerizada seguir los **

./gradlew clean build

docker build -t pichincha/transaction .

docker run -p 8080:8080 pichincha/transaction

Para ingresar a la consola de h2: http://localhost:8080/api/h2-console Datos para configurar conexión a la base de datos: Driver Class: org.h2.Driver JDBC URL: jdbc:h2:mem:nisum username: admin / password: system2023

El proyecto tiene la swagger para la documentacion y test de end points, para ingresar apuntar la siguiente ruta: http://localhost:8080/api/swagger-ui/index.html#/

Proyecto desarrollado por Diego Acosta ©

# Pichincha challenge
Api rest desarrollado para solucionar el ejercicio de Bco Pichincha

**Para correr la api seguir los siguientes pasos:**

1. Clonar proyecto desde el repositorio.

2. Descargar las dependencias del build.gradle.

3. El proyecto fue desarrollado con flyway para la creacion de tablas y carga de datos iniciales, dentro del archivo: /resources/db/migration/V1.0__create_tables.sql se encuentran el script para creacion de tablas y carga de datos iniciales.

4. El proyecto se despliega en el puerto 8080 deacuerdo a la configuración en el archivo application.yml.

5. El contextPath de la app esta configurado con el valor /api.

Para correr la APi dockerizada seguir los 

./mvnw install

docker build -t myorg/myapp .

docker run -p 8080:8080 myorg/myapp


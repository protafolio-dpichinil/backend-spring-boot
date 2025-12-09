# Backend Spring Boot

Este es un proyecto backend desarrollado con Spring Boot y Java 21, diseñado para demostrar mis conocimientos y experiencia en el desarrollo de aplicaciones Java modernas.

El objetivo principal es aplicar y exhibir buenas prácticas en la construcción de APIs RESTful, incluyendo:
*   **API REST:** Creación de endpoints para la gestión de recursos.
*   **Seguridad:** Implementación de autenticación y autorización utilizando Spring Security y JSON Web Tokens (JWT).
*   **Persistencia de Datos:** Uso de Spring Data JPA para la interacción con la base de datos.

## Tecnologías Utilizadas

A continuación se presenta una tabla con las principales tecnologías, frameworks y herramientas utilizadas en este proyecto.

| Categoría          | Tecnología / Herramienta | Versión    | Rol en el Proyecto|
|--------------------|--------------------------|------------|-------------------|
| Lenguaje           | Java                     | 21         | Uso de java 21 para el desarrolo del proyecto |
| Framework          | Spring Boot              | 3.2.5      | Configuración, autoconfiguración y ejecución rápida de la aplicación |
| Persistencia       | Spring Data JPA / H2     | -          | Gestion de capa de acceso a datos y mapeo de objetos relacionales |
| Seguridad          | Spring Security / JWT    | -          | Implementacion de seguridad de la api con JWT |
| Documentación API  | Springdoc OpenAPI        | 2.5.0      |         |
| Build              | Maven                    | -          | Gestion de dependencias del proyecto |
| Contenerización    | Docker (Dev Containers)  | -          | Conterizacion de aplicacion |
| Testing            | Junit 5 Jupiter          | -          | Pruebas unitarias y de integracion |
---


## Arquitectura de Tres Capas Implementada
El diseño del proyecto asegura una clara separación de responsabilidades, lo que facilita el mantenimiento, la escalabilidad y las pruebas:

### Capa de Presentación / Controlador (@RestController):

- Maneja las peticiones HTTP (GET, POST, PUT, DELETE).

- Convierte los datos JSON de entrada/salida a/desde objetos de la Capa de Servicio.

### Capa de Servicio (@Service):

- Contiene la lógica de negocio principal.

- Actúa como intermediario, orquestando las transacciones y la manipulación de datos.

### Capa de Datos / Repositorio (@Repository - JPA):

- Interactúa directamente con la base de datos (mediante Spring Data JPA).

- Define las operaciones CRUD y consultas personalizadas.

## Habilidades Demostradas
- Dominio en la configuración y uso de Spring Boot 3.

- Capacidad para desarrollar código limpio y moderno con Java 21.

- Aplicación práctica de la Arquitectura de Tres Capas para una alta mantenibilidad.

- Implementación de persistencia de datos eficiente con JPA/Hibernate.

- Compromiso con la calidad del código mediante pruebas unitarias y de integración usando JUnit 5.


# Cómo Empezar

Sigue estas instrucciones para levantar el entorno de desarrollo y ejecutar la aplicación.

### Requisitos Previos

*   [Visual Studio Code](https://code.visualstudio.com/)
*   [Docker Desktop](https://www.docker.com/products/docker-desktop/)
*   La extensión [Dev Containers](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers) para VS Code.

### Inicio Rápido con Dev Container

Este proyecto está configurado para usar Dev Containers, lo que simplifica la configuración del entorno de desarrollo.

1.  **Clona el repositorio** en tu máquina local.
2.  **Abre la carpeta del proyecto** con Visual Studio Code.
3.  VS Code detectará la configuración del Dev Container y te mostrará una notificación. Haz clic en **"Reopen in Container"**.
4.  Espera a que VS Code construya y configure el contenedor. El comando `postCreateCommand` en `devcontainer.json` se encargará de instalar las dependencias de Maven automáticamente.

### Ejecutar la Aplicación

Una vez que el contenedor esté en ejecución y las dependencias se hayan descargado, puedes arrancar la aplicación Spring Boot. Abre una nueva terminal dentro de VS Code (`Terminal > New Terminal`) y ejecuta:

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`.

### Visualización de Endpoints con OpenAPI (Swagger UI)

El proyecto incluye `springdoc-openapi` para generar documentación interactiva de la API. Una vez que la aplicación esté en marcha, puedes acceder a la interfaz de Swagger UI en tu navegador:

*   **URL de Swagger UI:** http://localhost:8080/swagger-ui/index.html

### Ejecutar Pruebas y Cobertura de Código

El proyecto está configurado con JaCoCo para medir la cobertura de los tests unitarios.

1.  Para ejecutar los tests y generar el informe de cobertura, utiliza el siguiente comando de Maven:
    ```bash
    ./mvnw clean verify
    ```
2.  Una vez finalizado, el informe de cobertura se encontrará en la siguiente ruta: `target/site/jacoco/index.html`. Puedes abrir este archivo en tu navegador para ver el detalle de la cobertura de código.

3.  **(Opcional) Visualizar el informe en un servidor local:** Si estás dentro del Dev Container, puede ser más cómodo levantar un servidor local para ver el informe. Ejecuta el siguiente comando:
    ```bash
    npx serve target/site/jacoco
    ```
    Esto iniciará un servidor web. Visual Studio Code detectará el puerto y te ofrecerá un botón para **"Abrir en el navegador"**. Por defecto, podrás acceder al informe en una URL como `http://localhost:3000`.

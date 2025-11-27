# backend-spring-boot

Este es un proyecto backend desarrollado con Spring Boot y Java 21, diseñado para demostrar mis conocimientos y experiencia en el desarrollo de aplicaciones Java modernas.

El objetivo principal es aplicar y exhibir buenas prácticas en la construcción de APIs RESTful, incluyendo:
*   **API REST:** Creación de endpoints para la gestión de recursos.
*   **Seguridad:** Implementación de autenticación y autorización utilizando Spring Security y JSON Web Tokens (JWT).
*   **Persistencia de Datos:** Uso de Spring Data JPA para la interacción con la base de datos.

---

## Cómo Empezar

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

*   **URL de Swagger UI:** http://localhost:8080/swagger-ui.html

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

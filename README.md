Notas App

▶️ Para ejecutar el proyecto simplemente corra el siguiente comando en la terminal del git bash estando en la carpeta raiz del proyecto : docker compose up --build

-------------------------------------------
Estado del Proyecto

✅ Fase 1 completada

-------------------------------------------
🏛️ Arquitectura

Este proyecto sigue una arquitectura basada en capas:

Frontend: SPA desarrollada en React con TypeScript.

Backend: API REST en Spring Boot siguiendo la separación de capas (Controller, Service, Repository, BO).

Base de Datos: MySQL 8 gestionada mediante Docker.

-------------------------------------------
🛠️ Tecnologías Utilizadas:

🔄Backend

Java (17) con Spring Boot (3.4.3)

JPA/Hibernate 

MySQL (8)  

Lombok 

Maven (3.9.6)


🎨Frontend

React.js con TypeScript

Material UI (Para la UI)

Axios (Para realizar peticiones al backend)

-------------------------------------------

🔧 Requisitos Previos:

Para ejecutar el proyecto, asegurese de tener instalado:

🐳 Docker & Docker Compose
 * Git
-------------------------------------------

▶️ Instalación y Ejecución:

▶️ Para ejecutar el proyecto simplemente corra el siguiente comando en la terminal del git bash estando en la carpeta raiz del proyecto : docker compose up --build

Este comando realizará:

📦 Construcción del backend y frontend.

🛢️ Creación del esquema de base de datos en MySQL.

🚀 Inicio de la aplicación con Docker.

--------------------------------------------

🏗️ Estructura de Contenedores

El docker-compose.yml define tres servicios:

frontend: Aplicación React corriendo en el puerto 5173.

backend: API REST en Spring Boot corriendo en el puerto 8080.

db: Base de datos MySQL en el puerto 3308 en la máquina local.

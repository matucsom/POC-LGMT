Notas App

▶️ Para ejecutar el proyecto simplemente corra el siguiente comando en la terminal del git bash estando en la carpeta raiz del proyecto : docker compose up --build

🌍 Ejecutado el comando dirijase a su : http://localhost:5173/

-------------------------------------------
Estado del Proyecto

✅ Fase 1 completada

📝 Crear, editar y eliminar notas.

📁 Archivar y desarchivar notas.

📋 Listar notas activas y archivadas.

-------------------------------------------
🏛️ Arquitectura

Este proyecto sigue una arquitectura basada en capas:

Frontend: SPA desarrollada en React con TypeScript.

Backend: API REST en Spring Boot siguiendo la separación de capas (Controller, Service, Repository, BO).

Base de Datos: MySQL gestionada mediante Docker.

-------------------------------------------
🛠️ Tecnologías Utilizadas:

🔄Backend: Java 17, Spring Boot 3.x, JPA, Hibernate, Lombok

🎨Frontend: React 18, TypeScript, Vite, Material UI

Base de Datos: MySQL 8

Otros: Docker, Docker Compose, Maven, Node.js 18

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

-------------------------------------------
🛢️ Configuración de la Base de Datos

La base de datos se configura automáticamente en el contenedor db. Si desea conectarse manualmente:

Host: localhost

Puerto: 3308

Usuario: root

Contraseña: root

Base de datos: note_app

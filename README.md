# ⚡ Casa de Electricidad



## 📝 Descripción
Casa de Electricidad es una aplicación web desarrollada con Spring Boot y Spring Security que implementa el patrón MVC en un proyecto Maven. Permite gestionar artículos y fábricas, así como administrar perfiles de usuario con roles de usuario y administrador.

## 🚀 Funcionalidades
- 📦 Registro de artículos con ID, número de artículo (usando AtomicInteger), nombre, descripción e imagen.
- 🏭 Gestión de fábricas con ID de fábrica y nombre.
- 🔐 Control de acceso mediante roles (user y admin).
- 🛠️ El administrador puede crear, modificar y eliminar artículos, fábricas y perfiles de usuario.
- 👥 El usuario puede listar artículos y fábricas.
- 🏡 Página de inicio con opciones de registro e inicio de sesión.
- ⚙️ Manejo de errores mediante controladores y mensajes personalizados.

## 🛠️ Tecnologías Utilizadas
- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Thymeleaf con Spring Security (Thymeleaf-Extras)
- Bootstrap
- MySQL
- Lombok
- AtomicInteger
- Jakarta Servlet API
- Maven

## 🟢 Instalación y Configuración
1. Clona el repositorio:
```bash
 git clone https://github.com/EstefanyRamirez08/Casa-de-Electricidad.git
```
2. Configura tu base de datos MySQL y ajusta `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/casa_de_electricidad
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
```
3. Ejecuta el proyecto:
```bash
mvn spring-boot:run
```
4. Accede a la aplicación en [http://localhost:8080](http://localhost:8080)

## 👥 Usuarios Predeterminados
- **Admin:** admin@admin.com / 123456
- **User:** prueba1@prueba.com / 123456

## 📜 Licencia
Proyecto con fines educativos. Derechos reservados.


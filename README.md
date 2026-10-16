# RecetaYa - API Gateway

API Gateway desarrollado con Java 17 y Spring Boot
para la Evaluacion Parcial 2 de RecetaYa.

## Funcionalidades

- Centralizar las peticiones a los microservicios.
- Enrutar solicitudes REST.
- Validar tokens JWT.
- Controlar el acceso segun roles.
- Proteger operaciones de usuarios, recetas,
  inventario y dispensaciones.

## Tecnologias

- Java 17.
- Spring Boot.
- Spring Security.
- JWT.
- Maven Wrapper.

## Requisitos

- JDK 17 o compatible.
- Git.
- Puerto 8080 disponible.
- Postman para realizar pruebas.

## Clonar el repositorio

git clone https://github.com/CatrinaMedina/recetayaapigateway.git

cd recetayaapigateway

## Compilar y generar el JAR

Desde Git Bash:

./mvnw clean package

Si la compilacion termina correctamente,
aparecera BUILD SUCCESS.

El archivo JAR se genera en target.

## Ejecutar localmente

./mvnw spring-boot:run

El Gateway funciona en:

http://localhost:8080

## Microservicios conectados

Los servicios de RecetaYa utilizan:

- Usuarios: puerto 8081.
- Recetas: puerto 8082.
- Inventario: puerto 8083.
- Dispensacion: puerto 8084.
- API Gateway: puerto 8080.

Los microservicios correspondientes deben
estar ejecutandose para procesar sus rutas.

## Arquitectura

- RecetayaapigatewayApplication:
  punto de inicio de la aplicacion.
- SecurityConfig:
  configuracion de seguridad y accesos.
- JwtAuthFilter:
  filtro de autenticacion JWT.
- JwtUtils:
  utilidades para validar tokens.
- application.properties:
  configuracion del Gateway y sus rutas.

## Rutas principales

- /usuarios
- /usuarios/login
- /roles
- /recetas
- /medicamentos
- /sucursales
- /inventario
- /dispensaciones

## Autenticacion JWT

El inicio de sesion se realiza mediante:

POST http://localhost:8080/usuarios/login

El token obtenido debe enviarse en
las solicitudes protegidas utilizando:

Authorization: Bearer TOKEN_JWT

El Gateway valida el token y los permisos
antes de permitir el acceso.

La propiedad jwt.secret debe ser compatible
con la configuracion del microservicio Usuarios.

## Control de acceso por roles

MEDICO:
- Consultar recetas.
- Crear, actualizar y eliminar recetas.
- Consultar medicamentos e inventario.

FARMACEUTICO:
- Consultar recetas.
- Consultar medicamentos e inventario.
- Gestionar medicamentos y sucursales.
- Realizar operaciones de stock.
- Gestionar dispensaciones.

Las rutas de usuarios y roles requieren
autenticacion segun las reglas configuradas.

## Pruebas con Postman

Se recomienda comprobar:

- Inicio de sesion y obtencion del JWT.
- Acceso con token valido.
- Rechazo de solicitudes sin autenticacion.
- Rechazo de tokens invalidos.
- Restricciones segun rol.
- Enrutamiento hacia los microservicios.
- Respuestas HTTP de exito y error.

## Persistencia

El API Gateway no administra una base
de datos propia.

La persistencia se encuentra en los
microservicios correspondientes,
que utilizan Spring Data JPA y H2.

## Proyecto

RecetaYa - Java: Diseno y Construccion
de Soluciones Nativas en Nube.

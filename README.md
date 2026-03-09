Challenge ForoHub 

Es un proyecto desarrollado en Java con spring boot, que permite la gestion de un foro academico.
Los etudiantes pueden autenticarse mediante JWT y realizar acciones sobre topicos como crear, consultar, actualizar y eliminar.

Se utilizo Java, Spring Boot 3, Spring security con JWT,
Spring Data JPA, Hibernate, MySQL, Maven, Postman.


Funciones:

1. Autentica con JWT
- Los usuarios se autentican usando login y clave
- Se genera un token que debe enviarse en el header para las soliciitudes protegidas
2. Gestiona topicos
- Crea topicos POST /topicos
- Lista los topicos GET /topicos
- lista con paginacion GET /topicos?page=0&size=10
- consulta un topico por ID GET /topicos/{id}
- Actualiza un topico PUT /topicos/{id}
- Elimina un topico DELETE /topicos/{id}

Para Usar:

1. Configura la baase de datos MySQL en application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/forohub
spring.datasource.username=root
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update

2. Ejecuta las migraciones de FlyWay para las tablas necesarias

3. Autentica:
- Endpoint: POST http://localhost:8080/login
- Body 
   {
   "login": "admin",
   "clave": "tu_clave"
   }
- Respuesta: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

4. Envia token protegidos, agregalos al header Authorization: Bearer <token> en Postman o en tu cliente HTTP.
5. Ejemplo:
   Endpoint: POST http://localhost:8080/topicos

Body:

{

"titulo": "Primer tema",

"mensaje": "Este es el mensaje del tema",

"autor": "Yessica",

"curso": "Spring Boot",

"estatus": "ABIERTO"

}

Header:

Authorization: Bearer <token_generado>



Realizado por Yessica Arias

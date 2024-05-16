# **SDA Catalogue Rest Api**
SDA Catalog API Built on Spring Boot. It use as backend service for SDA Catalog Environment.
SDA Catalog is application that built for mapping several applications that are running, being built, or being developed.
-------
### 1. Appliaction Specification
| Requirement        | Version     |
|--------------------|-------------|
| Spring Boot        | >= v3.2.0   |
| JDK                | >= v17.0.3  |
| PostgreSQL Driver  | >= v42.2.27 |
| Apache Maven       | >= v3.9.6   |

[*More information*](http://https://spring.io/quickstart "*More information*")
-------

### 2. Database
- PostgreSQL v14

[*More Information*](https://www.postgresql.org/ "*More Information*")

------
### 3. Installation
```bash
# Install dependency
$ mvn install

# run spring boot
$ mvn spring-boot:run

# run with seeder
$ mvn clean spring-boot:run -Dspring.profiles.active=local -Dspring-boot.run.arguments=--seeder=mapping,backend,frontend,picDev,sdaHosting,typeOfDb,webservice
```

------

### 4. Database Configuration
Go to into file application.yml and then :
- change url, port, and database name :
>spring.datasource.url=jdbc:postgresql://localhost:5432/db_sda_catalogue

- change username :
>spring.datasource.username=username

- change password :
>spring.datasource.password=password


-------

### 5. Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.0/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#web)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#using.devtools)
* [PostgreSQL documentation](https://www.postgresql.org/docs/14/index.html)


-------

### 6. Rest API Documentation
[**Click here**](ApiDoc.md "**Click here**")

-------

### 7. Contributors
- Galuh
- Rohmat TY
- Ricky


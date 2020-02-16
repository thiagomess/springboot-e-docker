![alt text](https://travis-ci.com/thiagomess/springboot-e-docker.svg?branch=master)
# REST API's RESTFul do 0 à Nuvem Com Spring Boot 2.x e Docker

Certificado:
https://www.udemy.com/certificate/UC-431c3054-63d7-4c07-ad42-3d922d0a51c9/


#####  Deploy

Para fazer o deploy local, temos da forma com Maven ou via Docker.

Na pasta raiz do projeto, executar os comandos abaixo, para baixar o fonte

```sh
git clone https://github.com/thiagomess/springboot-e-docker.git
```

Ir no caminho \springboot-e-docker\springboot-e-docker\src\main\resources\application.properties e alterar a chave para "test":
```sh
spring.profiles.active=test
 ```
Executar comandos Maven para empacotar e executar
```sh
cd ./springboot-e-docker/springboot-e-docker/
mvn clean package install -DskipTests
mvn spring-boot:run
```
E acessar a URL: http://localhost:8080/swagger-ui.html

#####  Via Docker

Esta disponibilizado uma imagem no docker Hub com o nome thiagomess/springboot-e-docker:1.0.0
```sh
docker pull thiagomess/springboot-e-docker:1.0.0
docker run --rm -p 8080:8080 thiagomess/springboot-e-docker:1.0.0
````

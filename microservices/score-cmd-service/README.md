# Score command microservice
The Score Command microservice provides REST and gRPC APIs for the Rock Paper Scissors game scores.
### Prerequisites
* Java 11 or higher
### Technology stack
* [OpenJDK 11](https://openjdk.java.net/projects/jdk/11)
* [Maven 3.6.0](https://maven.apache.org)
* [Spring Boot 2.6.1](https://spring.io/projects/spring-boot)
* [Lombok 1.18.20](https://projectlombok.org)
* [MapStruct](https://mapstruct.org)
* [Apache ZooKeeper 3.5.9](https://zookeeper.apache.org)
* [Apache Kafka 2.8.1](https://spring.io/projects/spring-kafka)
* [MongoDB 3.0](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html)
* [OpenAPI 3.0](https://springdoc.org)
* [gRPC framework 1.32.1](https://grpc.io/docs/languages/java/quickstart)
* [Micrometer 1.8.0](https://spring.io/blog/2018/03/16/micrometer-spring-boot-2-s-new-application-metrics-collector)
* [Spock 2.1](https://spockframework.org)
* [Apache Groovy 3.0.9](https://groovy-lang.org)
* [JaCoCo test coverage](https://www.jacoco.org/jacoco)
### Preconditions for running microservice
* Make sure you have the infrastructure (docker-compose-infrastructure.yml) is up and running before you run Score command microservice.
* If not navigate to the docker directory on your computer.
```
    > cd docker
```
* And run "docker-compose -f docker-compose-infrastructure.yml up --detach" command to deploy necessary infrastructure on docker containers in the background.
```
     > docker-compose -f docker-compose-infrastructure.yml up --detach
```
* If rps-grpc-lib is not already installed navigate to the common/rps-grpc-lib directory on your computer.
```
    > cd common/rps-grpc-lib
```
* And run "mvn clean install" in the root directory of the rps-grpc-lib project to generate Java model classes and service descriptions for microservices from proto3 models.
```
     > mvn clean install
```
* If cqrs-es-framework is not already installed navigate to the common/cqrs-es-framework directory on your computer.
```
    > cd common/cqrs-es-framework
```
* And run "mvn clean install" in the root directory of the cqrs-es-framework project to create jar file and install it to local .m2 repository.
```
     > mvn clean install
```
* If rps-common-lib is not already installed navigate to the common/rps-common-lib directory on your computer.
```
    > cd common/rps-common-lib
```
* And run "mvn clean install" in the root directory of the rps-common-lib project to create jar file and install it to local .m2 repository.
```
     > mvn clean install
```
### Running the Score command microservice from the command line
* Navigate to the root directory of the microservice on your computer.
```
    > cd microservices/score-cmd-service
```
* Run "mvn clean package -P<profile>" in the root directory to create the Score Command microservice app.
```
     > mvn clean package -Pdev
```
* Run microservice from the command line using spring boot maven plugin. Run "mvn spring-boot:run -Dspring.profiles.active=<profile>" in the root directory of the microservice to launch the Score Command microservice app.
```
     > mvn spring-boot:run -Dspring.profiles.active=dev
```
* Or run microservice from the command line. Run "java -jar target/score-cmd-service.jar" in the root directory of the microservice to launch the Score Command microservice app.
```
     > java -jar target/score-cmd-service.jar
```
* Open any browser and navigate to the microservice Open API 3.0 definition (REST API).
```
  http://localhost:8082/api/swagger-ui/index.html
```
### Useful links
For testing gRPC API (make sure that you are using correct grpc port for a profile), please consider the following options:
* [BloomRPC GUI client for gRPC](https://github.com/bloomrpc/bloomrpc)
* [gRPCurl command-line tool](https://github.com/fullstorydev/grpcurl)
* [gRPC UI command-line tool](https://github.com/fullstorydev/grpcui)

For testing REST API, you can also consider the following options:
* [Postman GUI client for REST](https://www.postman.com)

For testing MongoDB, you can also consider the following options:
* [Compass GUI for MongoDB](https://www.mongodb.com/products/compass)
* [Robo 3T for MongoDB](https://robomongo.org)

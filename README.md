# Human Resources Project

See the docs inside docs folder.

Run the docker container using
```
docker run --name human-resources -d -p 9090:9090 tavi903/human-resources:1.0.0
```


./mvnw org.hibernate.tool:hibernate-tools-maven:6.6.15.Final:hbm2java

Inside hibernate.properties
hibernate.connection.driver_class=org.h2.Driver
hibernate.connection.url=jdbc:h2:tcp://localhost:9090/mem:human-resources
hibernate.connection.username=sa
hibernate.default_schema=PUBLIC
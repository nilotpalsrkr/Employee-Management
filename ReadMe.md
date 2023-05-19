# Employee Management 

## Tech Stack
- Java 17
- Springboot 3.0
- JPA
- Postgres
- Swagger documentation
- maven

## Public link
github - https://github.com/nilotpalsrkr/Employee-Management/tree/master

docker -  https://hub.docker.com/repository/docker/nilotpals92/employee-ms/general

## Start the application:
### Method - 1:  Download code from github, build the project, start the project with single docker-compose
1. Git clone
   ```bash
   git clone git@github.com:nilotpalsrkr/Employee-Management.git
   ```
2. cd to the repository.
   ```bash
   cd employee-project
   ```
   
3. build the employee-management springboot application 
    ```bash
   mvn clean install 
   ```
   Or, use intellij's maven plugin to build the application.
4. Run the application using - Docker Compose
```bash
  docker-compose up -d
```
5. Test the application - 
** Postman **
   
Import the postman json, placed in the repository
    - docs/postman/Eke Assessment.postman_collection.json

** Swagger **

After the containers starts, access the below swagger url.
http://localhost:8080/swagger-ui/index.html#/



### Method - 2:  Run two docker containers - employee-ms and postgres.
1. Create a bridge network that 
   ```bash
   docker network create mynetwork
   ```
2. Pull and run postgres
```bash
docker run -d --network mynetwork --name postgres-db -e POSTGRES_PASSWORD=eke -e POSTGRES_USER=eke -e POSTGRES_DB=employee_mgmt -p 5432:5432 postgres:alpine

```
3. Run the image - nilotpals92/employee-ms
```bash
docker run -d --network mynetwork --name employee-ms -p 8080:8080 nilotpals92/employee-ms:1.0
```
4. Test the application -
   ** Postman **

Import the postman json, placed in the github repository
- https://github.com/nilotpalsrkr/Employee-Management/blob/master/docs/postman/Eke%20Assessment.postman_collection.json

** Swagger **

After the containers starts, access the below swagger url.
http://localhost:8080/swagger-ui/index.html#/

##Steps to create docker image:
```bash
 docker build -t nilotpals92/employee-ms:1.0 .
```
Steps to push the image to docker public repo -
```bash
 docker push nilotpals92/employee-ms:1.0
```

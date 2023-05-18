## Assuming the Employee management springboot microservice is hosted in localhost,


## Start the application:
### Method - 1: Download from github, build the project, start the project with docker-compose
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
4. Run the application using - Docker Compose
```bash
  docker-compose up -d
```
5. Test the application - 
**Postman **
Import the postman json, placed in the repository
    - docs/postman/Eke Assessment.postman_collection.json

**Swagger **
After the containers starts, access the below swagger url.
http://localhost:8080/swagger-ui/index.html#/

### Method - 2: Docker pull, start a container
1. Docker pull:
```bash
 docker pull nilotpals92/employee-ms:1.0
```
2. Run the image - nilotpals92/employee-ms
```bash
 docker run nilotpals92/employee-ms:1.0
```
===========================================================================================================
Steps to create docker image:
```bash
# docker build -t nilotpals92/employee-ms:1.0 .
```
Steps to push the image to docker public repo - https://hub.docker.com/repository/docker/nilotpals92/employee-ms/general
```bash
# docker push nilotpals92/employee-ms:1.0
```

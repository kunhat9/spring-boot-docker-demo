# spring-boot-docker-demo
# docker version 20.10.8 build 3967b7d
# run project with docker 
1. docker built -t spring-demo:1.0 .
2. docker compose up -d


# Fix Project
- Add Hibernate Properties for MySQL Version
- Modify Context Build Web Service in docker-compose file
- Application Properties server.port=8089 but Dockerfile expose 8080 ? => Fix server.port=8080


# Current State: Heavy Container, Slow Start Time. 
Drawback:
- Copy all source to container
- Run Project with Spring dev tool

# Enhance/Optimize
- Change Dockerfile: Copy only build folder to container, check: 
    
    https://huongdanjava.com/vi/deploy-ung-dung-spring-boot-su-dung-docker.html

    https://www.baeldung.com/dockerizing-spring-boot-application

- Or Using Maven Jib Plugin (an open-source Java tool maintained by Google for building Docker images of Java applications) instead of Dockerfile

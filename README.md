# Kaméleon apartmanház - Backend
Backend (API and database) for apartment booking website made with MariaDB and Spring Boot.

## Check out the live website
[kameleonbalaton.hu](https://www.kameleonbalaton.hu)

## Run application locally with docker 
The services can be run on the background with command:
```
docker-compose up -d
```
### Stop the application
Stopping all the running containers is also simple with a single command: 
```
docker-compose down
```
### Shut down and delete images and containers
If you need to stop and remove all containers, networks, and all images used by any service in docker-compose.yml file, use the command:
```
docker-compose down --rmi all
```

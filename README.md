# Kaméleon apartmanház

Spring Boot és Vue.js alkalmazás.

## Hivatalos weboldal
[kameleonbalaton.hu](https://www.kameleonbalaton.hu)


## Alkalmazás futtatása lokálisan

Az alkalmazás futtatásához külön kell futtatni a backend és a frontend alkalmazást.

### A backend futtatásának lépései:
1. Létre kell hozni egy MariaDB adatbázist *db_kameleon* néven és futtatni a 3360-as porton
2. A backendet a következő parancsokkal tudjuk lefordítani majd futtatni:
    - `mvn compile`
    - `mvn spring-boot:run `


3. Ezután pár POST hívást kell küldeni a backend-nek (Például Postman-nel), hogy inicializáljuk az adatbázist.
    - `POST http://localhost:8080/setup/roles`
    - `POST http://localhost:8080/setup/admin?password=testpassword`
    - `POST http://localhost:8080/setup/weekendhouse`
        - request body: weekendhouse.txt
    - `POST http://localhost:8080/setup/apartment`
        - request body: apartments.txt
 
### A frontend futtatásának lépései:
A frontend mappájában kell kiadni a következő két parancsot:
- `npm install`
- `npm run serve -- --port 2000`

### Ez után az alkalmazás elérhető a `http://localhost:2000/` címen. 
Az alkalmazást tesztelni új felhasználó regisztrálásával lehetséges, illetve admin felhasználóként az alábbi adatokkal lehet bejelentkezni:
- kameleonbalaton@gmail.com
- *testpassword*

Az email küldés funkciót (ami új felhasználó regisztrálásához szükséges) előfordulhat, hogy blokkolja a számítógép vírusirtója, így azt érdemes lehet kikapcsolni.

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



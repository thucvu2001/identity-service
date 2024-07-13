### create database container

```bash
docker run -d -p 3306:3306 --name mysql_container -e MYSQL_ROOT_PASSWORD=root mysql:latest
```

```bash
docker exec -it mysql_container mysql -u root -proot -e "CREATE DATABASE IF NOT EXISTS identity_service;"
```

```bash
docker exec -it mysql_container mysql -u root -p

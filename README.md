# xakaton-bimit-edge

## Подготовка проекта

Установить postgres
 - Завести пользователя: xakaton
 - Завести базу данных: xakaton
 
Установить брокер (например MQTT брокер Mosquitto)


## Запуск проекта из docker'а

Минимальный запуск
```
docker run -e BROKER=tcp://path_to_broker:1883  docker.pkg.github.com/maks-master/xakaton-bimit-world/docker-xakaton-world:work
```

Все параметры
```
docker run --name docker-xakaton-world -e BROKER=tcp://path_to_broker:1883 --rm -d -v ./logs:/usr/local/tomcat/logs  docker.pkg.github.com/maks-master/xakaton-bimit-world/docker-xakaton-world:work
```



## Сборка проекта

Клонируем
```
git clone https://github.com/maks-master/xakaton-bimit-world.git
```
```
cd ./xakaton-bimit-world
```
Сборка
```
mvn clean package -Pprod
```
Результат
```
./xakaton-bimit-world/target/workd.war
```

Используем сборку Dockerfile
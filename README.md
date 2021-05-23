# xakaton-bimit-edge

## Подготовка проекта

Установить postgres
 - Завести пользователя: xakaton
 - Завести базу данных: xakaton
 
Установить брокер (например MQTT брокер Mosquitto)


## Запуск проекта

Минимальный запуск
```
docker run -e BROKER=tcp://path_to_broker:1883  docker.pkg.github.com/maks-master/xakaton-bimit-world/docker-xakaton-world:work
```

Все параметры
```
docker run --name docker-xakaton-world -e BROKER=tcp://path_to_broker:1883 --rm -d -v ./logs:/usr/local/tomcat/logs  docker.pkg.github.com/maks-master/xakaton-bimit-world/docker-xakaton-world:work
```

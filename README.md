# Task manager
Task manager это это REST API по <a href="https://disk.yandex.ru/d/QGD1KWpb-_pWhg">тестовому заданию</a>

### Инструкция по локальному запуску
Для локального запуска приложения необходимы Java Development Kit и Maven

1) Создать .jar используя Maven Plugin: $ mvn package
2) Запустить приложение из консоли с указанием профиля local: $ java -jar target\task-manager-v.1.jar --spring.profiles.active=local

### Инструкция для Docker
Для локального запуска приложения необходимы Java Development Kit и Maven

1) Создать .jar используя Maven Plugin: $ mvn clean package
2) Выполнить из консоли $ docker-compose up

### Документация
После локального запуска приложения Swagger UI доступен по адресу http://localhost:8080/swagger-ui/


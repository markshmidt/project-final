# JiraRush Final Project

## Выполненные задания

### Подключение in-memory H2 для тестов
- Вынесены два DataSource-конфига: для PostgreSQL (`@Profile("prod")`) и H2 (`@Profile("test")`).
- Добавлен `application-test.yaml`.
- SQL-скрипт `data.sql` адаптирован для H2 (упрощена структура, удалены PostgreSQL-specific элементы).

### Покрытие тестами контроллера `ProfileRestController`
- Тестируются оба публичных метода: `GET /api/profile` и `PUT /api/profile`.
- Проверяются позитивные и негативные сценарии (успешный запрос, неавторизованный доступ, валидация данных).

### Dockerfile
- Добавлен `Dockerfile` в корень проекта для сборки Spring Boot приложения.

###  Docker Compose
- Создан `docker-compose.yml` для запуска:
  - PostgreSQL (с параметрами из `.env`)
  - Spring Boot сервера (`app`)
  - NGINX (`nginx`) с конфигурацией из `config/nginx.conf`.

## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование

- https://habr.com/ru/articles/259055/

Список выполненных задач:
...

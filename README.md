# Автоматизация тестирования приложения покупки туров - AQA SHOP
## Инструкция по установке, настройке ПО и запуску автотестов

### Для запуска автотестов необходимо установить:
  - Inteliji IDEA с плагинами lombok, allure
  - Java 11
  - Docker Desktop

### Зависимости:
В файле build.gradle должны быть прописаны зависимости для:
  - JUnit 5
  - Selenide
  - REST Assured
  - Allure
  - Lombok
  - MySQL
  - PostgreSQL
  - faker

### Запуск автотестов:

1. В первом терминале выполнить запуск контейнеров с базами данных MySQL и PostgreSQL, и эмулятором платежных сервисов командой:
   - docker compose up -d
   
2. Во втором терминале выполнить запуск приложения с флагом варианта подключения к целевой базе данных командами:
   - Для работы с MySQL: java -Dspring.datasource.url="jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
   - Для работы PostgreSQL: java -Dspring.datasource.url="jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

3. В третьем терминале запустить автотесты с заданием адреса базы данных:
   - Для работы с MySQL: ./gradlew clean test -Ddb.url="jdbc:mysql://localhost:3306/app"
   - Для работы с PostgreSQL: ./gradlew clean test  -Ddb.url="jdbc:postgresql://localhost:5432/app"

### Результат выполнения автотестов:
/build/report/index.html лучше открыть в браузере

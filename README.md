***Для запуска API тестов для работы с БД mySQL, необходимо выполнить следующие действия:***
1. В первом терминале запустить контейнеры в Docker с БД mySQL, Postgres и эмулятором банковских сервисов командой:
   
    docker compose up -d

3. Во втором терминале запустить jar-файл с приложением с параметрами БД mySQL:
   
     java -jar artifacts/aqa-shop.jar \ --spring.profiles.active=mysql \ --spring.credit-gate.url=http://your-credit-gate-url

4. В третьем терминале запустить выполнение тестов командой:
   
   ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app" "-Dspring.profiles.active=mysql"

5. Результат выполения тестов: /build/report/index.html лучше открыть в браузере

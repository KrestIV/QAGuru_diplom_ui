# <p style="text-align:center">Дипломный проект </p>
## <p style="text-align:center">UI-часть на примере сайта интернет магазина товаров для животных [Kakadu](http://kakadu39.ru/)</p>

<p style="text-align:center">
<img src="media/kakadu.png" height="201" width="157" style="background: #FFFFFF" alt="centicore_logo">
</p>

# Содержание
- [Стек технологий](#стек-технологий)
- [Список тестов](#список-тестов)
- [Jenkins](#jenkins)
- [Запуск автотестов](#запуск-автотестов)
- [Allure-отчет](#allure-отчеты)
- [Уведомления в телеграм](#уведомления-в-телеграм)
- [Пример запуска автотестов](#примеры-запуска-автотестов)


## Стек технологий
<p style="background:#ffffff;text-align:center">
<a href="https://www.java.com/ru/"><img width="8%" title="Java" src="media/java-original.svg" alt="java_logo"></a>
<a href="https://www.jetbrains.com/idea/"><img width="8%" title="IntelliJ IDEA" src="media/intellij-original.svg" alt="intelliJIDEA_logo"></a>
<a href="https://selenide.org/"><img width="8%" title="Selenide" src="media/Selenide.svg" alt="selenide_logo"></a>
<a href="https://aerokube.com/selenoid/"><img width="8%" title="Selenoid" src="media/Selenoid.svg" alt="selenoid_logo"></a>
<a href="https://allurereport.org/"><img width="8%" title="Allure Report" src="media/Allure_Report.svg" alt="allure_logo"></a>
<a href="https://gradle.org/"><img width="8%" title="Gradle" src="media/Gradle.svg" alt="gradle_logo"></a>
<a href="https://junit.org/junit5/"><img width="8%" title="JUnit5" src="media/junit-original-wordmark.svg" alt="jUnit5_logo"></a>
<a href="https://github.com/"><img width="8%" title="GitHub" src="media/GitHub.svg" alt="gitHub_logo"></a>
<a href="https://www.jenkins.io/"><img width="8%" title="Jenkins" src="media/Jenkins.svg" alt="jenkins_logo"></a>
<a href="https://telegram.org/"><img width="8%" title="Telegram" src="media/Telegram.svg" alt="telegram_logo"></a>
</p>  

Автотесты в проекте написаны на <code>Java</code> с использованием фреймворка [Selenide](https://selenide.org/),  
сборщик - <code>Gradle</code>,  
фреймворк модульного тестирования - <code>JUnit 5</code>,  
удаленный запуск браузера и прохождение сценариев - [Selenoid](https://aerokube.com/selenoid/),  
управление удаленным запуском, настройка параметров и формирование отчета - <code>Jenkins + Allure</code>.  
Реализована отправка результатов в <code>Telegram</code> при помощи бота.

### Список тестов
**fullFilledApplicationForPartnershipShouldDisplaySuccessMessageTest** - Тест отправки заявки для сотрудничества  
**partFilledApplicationForPartnershipShouldDisplayErrorMessageTest** - Тест ошибки при неполном заполнении формы  
**vacanciesListShouldContainRelevantItemsOrBeEmptyTest** - Тест фильтра вакансий по специальности  
**fullFilledRequestCallbackFormShouldDisplaySuccessMessageTest** - Тест формы заказа обратного звонка  
**contactsPageShouldContainAllLocationsTest** - Тест отображения локаций офисов

### Jenkins
Настройка, запуск и переход к результатам запуска автотестов осуществляется в [Jenkins](https://jenkins.autotests.cloud/job/006-ilya_krestsov_qa_guru_javaAQA_diplom/)

<p>
<img src="media/JenkinsJobMainScreen.png" style="background: #FFFFFF" alt="Jenkins_job">
</p>

### Запуск автотестов
Автотесты запускаются сборкой в <code>Jenkins</code> с заполнением параметров выполнения

<p>
<img src="media/jenkinsBuildOptions.png" height="331" width="610" style="background: #FFFFFF" alt="jenkins_build_parameters">
</p>  

- **EXEC_SERVER** - Адрес сервера выполнения тестов
- **CREDENTIALS_LOGIN** - Логин для доступа к серверу выполнения тестов
- **CREDENTIALS_PW** - пароль для доступа к серверу выполнения тестов
- **BROWSER, BROWSER_VERSION, BROWSER_SIZE** - Выбор браузера/версии/разрешения экрана для исполнения тестов
- **COMMENT** - Параметр, определяющий текст сообщения в боте уведомлений

### Allure-отчеты

Allure-отчет содержит в себе результаты выполнения всех тестов с дополнительной информацией:
- Скриншот финала теста
- код страницы
- журнал консоли браузера при выполнении теста
- [видео-запись выполнения теста](https://selenoid.autotests.cloud/video/f6f6c0e7f7576427a76354f107f99edd.mp4)

[Пример полного allure-отчета](https://jenkins.autotests.cloud/job/CenticoreJobApplication/allure/)

<p>
<img src="media/allureReportExample.png" height="296" width="884" style="background: #FFFFFF" alt="allure_report">
</p>

### Уведомления в телеграм

По завершении выполнения тестов отправляется краткий отчет в телеграм-бот со ссылкой на полный allure-отчет

<p>
<img src="media/telegramNotification.png" style="background: #FFFFFF" alt="telegram_notification">
</p> 

### Примеры запуска автотестов

<p style="text-align:center">
  <img title="Test execution example" src="media/TestExecutionExample_00.gif" alt="example №1">
</p>
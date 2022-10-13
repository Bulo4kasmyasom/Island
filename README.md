# Project "Island" for JavaRush module 2.

##### **Path to settings files:**

> - **src/main/java/com/javarush/island/sternard/settings/Settings.java**
>
> Settings that are read from JSON file in Settings.class. Contains such settings as
> - path to OrganismsProperty.json
> - path to log4j2.properties ( maybe not used )
> - show stacktrace when the exception worked or no ( "true" or "false" )
> - map settings
> - Multithreading time period
> - Statistics color text
> - etc...

----

> - **src/main/resources/sternard/GameSettings.json**
>
> Main json file with GameSettings

----

> - **src/main/resources/sternard/OrganismsProperty.json**
>
> json file with organism properties. Contains such settings as
> > name, icon, possible food, speed, weight, energy, etc...
>
> for all organisms

----

> - **src/main/resources/sternard/log4j2.properties**
>
> Settings for LOG4J. Path to this properties file located in Settings.java file at **Static block**
> > static { System.setProperty("log4j2.configurationFile" .... }

----

## Problems

1. Не ясно как правильно указывать classpath для использования log4j конфиг файла.
   Если файл положить в resources, то всё хорошо, но если будет во вложенных папках, то нужно как-то указать путь.
   Сейчас это сделано в Settings.class в статическом блоке:

> System.setProperty("log4j2.configurationFile", "src/main/resources/sternard/log4j2.properties");

2. В Settings в методе get() идёт проверка входных данных из JSON файла (GameSettings.json). Если программа запущена
   изначально с верными параметрами, то всё ок, но если при запущенной программе изменить конфиг файл и ввести
   специально неверные
   значения, то программу невозможно аварийно остановить при выбросе исключения
   в [exception/HandlerExceptions.java](src/main/java/com/javarush/island/sternard/exception/HandlerExceptions.java).
   Временно пришлось обращаться к
   executorService чтобы хоть как-то остановить потоки. synchronized в контракте метода не помогает.













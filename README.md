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
> -

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
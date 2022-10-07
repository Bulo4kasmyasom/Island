package ru.javarush.island.sternard.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameLogger {
    public static Logger getLog() {
        return LogManager.getLogger();
    }

}

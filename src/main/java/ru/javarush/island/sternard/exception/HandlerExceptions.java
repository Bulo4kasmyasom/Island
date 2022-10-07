package ru.javarush.island.sternard.exception;

import ru.javarush.island.sternard.settings.Settings;

public class HandlerExceptions extends RuntimeException {
    public HandlerExceptions(String message) {
        System.err.println(message);
    }

    public HandlerExceptions(String message, StackTraceElement[] stackTrace) {
        showStackTraceOrNo(message, stackTrace);
    }

    private void showStackTraceOrNo(String message, StackTraceElement[] stackTrace) {
        boolean exceptionShowStackTrace = Settings.get().isExceptionShowStackTrace();
        System.err.println(message);
        if (!exceptionShowStackTrace) {
            System.exit(0);
        } else {
            for (StackTraceElement stackTraceElement : stackTrace) {
                System.out.println(stackTraceElement);
            }
        }
    }

}
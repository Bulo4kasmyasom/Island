package ru.javarush.island.sternard.utils;

import java.io.File;


public class PathFinder {

    public static String convertPathForAllOS(String path) {
        char s = File.separator.charAt(0);
        return System.getProperty("user.dir") + File.separator + path.replace('/', s);
    }
}
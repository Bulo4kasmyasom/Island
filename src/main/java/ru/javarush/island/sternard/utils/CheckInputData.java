package ru.javarush.island.sternard.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.javarush.island.sternard.annotation.Check;
import ru.javarush.island.sternard.exception.HandlerExceptions;

import java.lang.reflect.Field;
import java.util.Map;

import static ru.javarush.island.sternard.constant.lang.English.ACCESS_FIELD_ERROR;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckInputData {
    public static String check(Object object) {
        Class<?> objectClass = object.getClass();
        for (Field declaredField : objectClass.getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(Check.class)) {
                declaredField.setAccessible(true);
                Object fieldValue;
                try {
                    fieldValue = declaredField.get(object);
                } catch (IllegalAccessException e) {
                    throw new HandlerExceptions(ACCESS_FIELD_ERROR, e.getStackTrace());
                }
                Check annotationValue = declaredField.getAnnotation(Check.class);

                if ((checkStrings(declaredField, fieldValue, annotationValue)) ||
                        (checkDigits(fieldValue, annotationValue)) ||
                        (checkMaps(fieldValue)) ||
                        (checkStringArrays(fieldValue)))
                    return declaredField.getAnnotation(Check.class).message() + " Field: " + declaredField.getName();
            }
        }
        return "";
    }

    private static boolean checkMaps(Object fieldValue) {
        if (fieldValue instanceof Map<?, ?> map)
            return map.size() == 0;
        return false;
    }

    private static boolean checkStringArrays(Object fieldValue) {
        if (fieldValue instanceof String[] array)
            return array.length == 0;
        return false;
    }

    private static boolean checkDigits(Object fieldValue, Check annotationValue) {
        if (fieldValue instanceof Number number)
            return number.intValue() < annotationValue.minValue() || number.intValue() > annotationValue.maxValue();
        return false;
    }

    private static boolean checkStrings(Field declaredField, Object fieldValue, Check annotationValue) {
        if (!annotationValue.isNull() && fieldValue == null)
            return false;

        if (fieldValue instanceof String val) {
            if (!annotationValue.isEmpty() && val.isEmpty())
                return true;

            // valid:
            // path/to/file.json
            // file.json
            return (declaredField.getName().equals("pathToOrganismsProperty")) &&
                    (!val.matches("^(\\w+\\/)*(\\w+\\.json)$"));
        }
        return false;
    }

}

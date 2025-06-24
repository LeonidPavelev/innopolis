package ru.innopolis.utils;

/**
 * Утилитарный класс для работы с исключениями
 */
public class ExceptionUtils {
    /**
     * Приватный конструктор для запрета создания экземпляров утилитного класса
     */
    private ExceptionUtils() {
    }

    /**
     * @param throwable исключение
     * @return строка со стеком вызовов
     */
    public static String getStackTrace(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append(element).append("\n");
        }
        return sb.toString();
    }
}

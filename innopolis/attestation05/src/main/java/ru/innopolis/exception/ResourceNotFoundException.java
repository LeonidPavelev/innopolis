package ru.innopolis.exception;


/**
 * Исключение, выбрасываемое когда запрашиваемый ресурс не найден.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Создает новое исключение с указанным сообщением.
     *
     * @param message сообщение об ошибке
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

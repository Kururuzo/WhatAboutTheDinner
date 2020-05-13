package ru.restaurant.util.exception;

public class DateTimeExpiredException extends RuntimeException{
    public DateTimeExpiredException(String message) {
        super(message);
    }
}

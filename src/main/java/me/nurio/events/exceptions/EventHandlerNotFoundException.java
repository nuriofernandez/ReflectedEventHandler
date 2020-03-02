package me.nurio.events.exceptions;

public class EventHandlerNotFoundException extends RuntimeException {

    private static final String PREFIX = "Unable to find Event > ";

    public EventHandlerNotFoundException(String message) {
        super(PREFIX + message);
    }

    public EventHandlerNotFoundException(String message, Throwable throwable) {
        super(PREFIX + message, throwable);
    }

}
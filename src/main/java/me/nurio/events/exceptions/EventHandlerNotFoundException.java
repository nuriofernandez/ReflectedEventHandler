package me.nurio.events.exceptions;

import me.nurio.events.internal.EventReflectionUtils;

/**
 * This exception will be throw when something went wrong with an event handler.
 *
 * @see EventReflectionUtils#getEventFromMethod
 */
public class EventHandlerNotFoundException extends RuntimeException {

    private static final String PREFIX = "Unable to find Event > ";

    public EventHandlerNotFoundException(String message) {
        super(PREFIX + message);
    }

    public EventHandlerNotFoundException(String message, Throwable throwable) {
        super(PREFIX + message, throwable);
    }

}
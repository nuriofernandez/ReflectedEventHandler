package me.nurio.events.internal;

import me.nurio.events.exceptions.EventHandlerNotFoundException;
import me.nurio.events.handler.Event;

import java.lang.reflect.Method;

/**
 * This class manages internally event's reflection operations.
 */
public class EventReflectionUtils {

    /**
     * Obtain event class from reflected method.
     *
     * @param method Reflected method to find event class.
     * @return reflected event class from specified method.
     */
    public static Class<? extends Event> getEventFromMethod(Method method) {
        if (method.getParameterCount() != 1) {
            throw new EventHandlerNotFoundException("Handled method doesn't have a event parameter or have more than one.");
        }

        Class<?> parameter = method.getParameterTypes()[0];
        if (!Event.class.isAssignableFrom(parameter)) {
            throw new EventHandlerNotFoundException("Handled method event parameter aren't a valid event.");
        }

        return (Class<? extends Event>) parameter;
    }

}
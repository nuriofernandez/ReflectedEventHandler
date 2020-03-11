package me.nurio.events;

import me.nurio.events.exceptions.EventHandlerNotFoundException;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventPriority;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EventReflection {

    /**
     * Check if specified method its an Event handler.
     *
     * @param method Specified method to check.
     * @return true if its a event handler method.
     */
    public static boolean isHandledMethod(Method method) {
        return method.getAnnotation(EventHandler.class) != null;
    }

    /**
     * This method will obtain all methods with the EventHandler annotation.
     *
     * @param classListener
     * @return
     */
    public static List<Method> getHandledMethodsFrom(Class<?> classListener) {
        return Arrays.stream(classListener.getMethods())
                .filter(EventReflection::isHandledMethod)
                .collect(Collectors.toList());
    }

    /**
     * Obtain event class from reflected method.
     *
     * @param method Reflected method to find event class.
     * @return reflected event class from specified method.
     */
    public static Class<?> getEventFromMethod(Method method) {
        if (method.getParameterCount() != 1) {
            throw new EventHandlerNotFoundException("Handled method doesn't have a event parameter or have more than one.");
        }

        Class<?> parameter = method.getParameterTypes()[0];
        Class<?> superClass = parameter.getSuperclass();
        if (!superClass.equals(Event.class)) {
            throw new EventHandlerNotFoundException("Handled method event parameter aren't a valid event.");
        }

        try {
            return parameter;
        } catch (Exception er) {
            throw new EventHandlerNotFoundException("Unexpected error", er);
        }
    }

    /**
     * Obtain {@link EventPriority} from reflection method.
     *
     * @param method Reflected method to find the EventPriority.
     * @return EventPriority of the event method.
     */
    public static EventPriority getEventPriorityFromMethod(Method method) {
        EventHandler handler = method.getAnnotation(EventHandler.class);
        return handler.priority();
    }

}
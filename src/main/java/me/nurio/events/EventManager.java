package me.nurio.events;

import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventListener;

import java.lang.reflect.Method;
import java.util.*;

public class EventManager {

    public static <L extends EventListener> void registerEvents(L listener) {
        List<Method> eventListeners = EventReflection.getHandledMethodsFrom(listener.getClass());
        eventListeners.forEach(method -> EventManagement.registerEvent(new RegisteredEventListener(listener, method)));
    }

    public static <E extends Event> void callEvent(E event) {
        EventManagement.getEventListenersOrderedByPriorityFor(event).forEach(listener -> listener.invoke(event));
    }

}
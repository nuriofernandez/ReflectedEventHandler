package me.nurio.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    public static <L extends Listener> void registerEvents(L listener) {
        List<Method> eventListeners = EventReflection.getHandledMethodsFrom(listener.getClass());
        eventListeners.forEach(method -> EventManagement.registerEvent(new RegisteredEventListener(listener, method)));
    }

    public static <E extends Event> void callEvent(E event) {
        EventManagement.getEventListenersFor(event).forEach(listener -> listener.invoke(event));
    }

}

class EventManagement {

    private static Map<Class<?>, List<RegisteredEventListener>> eventMap = new HashMap<>();

    static List<RegisteredEventListener> getEventListenersFor(Event event) {
        return getEventListenersFor(event.getClass());
    }

    static <L extends Listener> void registerEvent(RegisteredEventListener registeredEvent) {
        eventMap.putIfAbsent(registeredEvent.getEvent(), new ArrayList<>());
        getEventListenersFor(registeredEvent.getEvent()).add(registeredEvent);
        System.out.println("Registered event => " + registeredEvent.getName());
    }

    private static List<RegisteredEventListener> getEventListenersFor(Class<?> event) {
        return eventMap.getOrDefault(event, new ArrayList<>());
    }

}

package me.nurio.events;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class EventManager {

    public static <L extends Listener> void registerEvents(L listener) {
        List<Method> eventListeners = EventReflection.getHandledMethodsFrom(listener.getClass());
        eventListeners.forEach(method -> EventManagement.registerEvent(new RegisteredEventListener(listener, method)));
    }

    public static <E extends Event> void callEvent(E event) {
        EventManagement.getEventListenersOrderedByPriorityFor(event).forEach(listener -> listener.invoke(event));
    }

}

class EventManagement {

    private static Map<Class<?>, List<RegisteredEventListener>> eventMap = new HashMap<>();

    static List<RegisteredEventListener> getEventListenersFor(Event event) {
        return getEventListenersFor(event.getClass());
    }

    static List<RegisteredEventListener> getEventListenersOrderedByPriorityFor(Class<?> event){
        return getEventListenersFor(event);
    }

    static List<RegisteredEventListener> getEventListenersOrderedByPriorityFor(Event event){
        return getEventListenersFor(event).stream().sorted(Comparator.comparing(RegisteredEventListener::getPriority)).collect(Collectors.toList());
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

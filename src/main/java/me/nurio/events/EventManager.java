package me.nurio.events;

import java.lang.reflect.Method;
import java.util.*;

public class EventManager {

    // Event list

    private static Map<Class<?>, List<RegisteredEventListener>> eventMap = new HashMap<>();

    protected static List<RegisteredEventListener> getEventListenersFor(Event event) {
        return getEventListenersFor(event.getClass());
    }

    protected static List<RegisteredEventListener> getEventListenersFor(Class<?> event) {
        return eventMap.getOrDefault(event, new ArrayList<>());
    }

    protected static void addEventListerTo(Event event, RegisteredEventListener eventListener) {
        addEventListenerTo(event.getClass(), eventListener);
    }

    protected static void addEventListenerTo(Class<?> event, RegisteredEventListener eventListener) {
        eventMap.putIfAbsent(event, new ArrayList<>());
        getEventListenersFor(event).add(eventListener);
    }

    // Event registration

    public static <L extends Listener> void registerEvents(L listener) {
        List<Method> eventListeners = EventReflection.getHandledMethodsFrom(listener.getClass());
        eventListeners.forEach(method -> registerEvent(new RegisteredEventListener(listener, method)));
    }

    private static <L extends Listener> void registerEvent(RegisteredEventListener eventMethod) {
        addEventListenerTo(eventMethod.getEvent(), eventMethod);
        System.out.println("Registered event => " + eventMethod.getName());
    }

    public static <E extends Event> void callEvent(E event) {
        getEventListenersFor(event).forEach(listener -> listener.invoke(event));
    }

}

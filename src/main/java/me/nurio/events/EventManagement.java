package me.nurio.events;

import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventListener;

import java.util.*;
import java.util.stream.Collectors;

public class EventManagement {

    private static Map<Class<?>, List<RegisteredEventListener>> eventMap = new HashMap<>();

    protected static <L extends EventListener> void registerEvent(RegisteredEventListener registeredEvent) {
        eventMap.putIfAbsent(registeredEvent.getEvent(), new ArrayList<>());
        getEventListenersFor(registeredEvent.getEvent()).add(registeredEvent);
        System.out.println("Registered event => " + registeredEvent.getName());
    }

    protected static List<RegisteredEventListener> getEventListenersOrderedByPriorityFor(Event event) {
        return getEventListenersOrderedByPriorityFor(event.getClass());
    }

    private static List<RegisteredEventListener> getEventListenersOrderedByPriorityFor(Class<?> event) {
        return getEventListenersFor(event).stream().sorted(Comparator.comparing(RegisteredEventListener::getPriority)).collect(Collectors.toList());
    }

    private static List<RegisteredEventListener> getEventListenersFor(Event event) {
        return getEventListenersFor(event.getClass());
    }

    private static List<RegisteredEventListener> getEventListenersFor(Class<?> event) {
        return eventMap.getOrDefault(event, new ArrayList<>());
    }

}

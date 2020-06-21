package me.nurio.events;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.nurio.events.handler.Event;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class manages internally event registry.
 */
@RequiredArgsConstructor
class EventManagement {

    @NonNull private final EventManager eventManager;

    /**
     * Registered events mapped as 'EventClass' as key and list of 'RegisteredEventHandlers' for that event.
     */
    private Map<Class<?>, List<RegisteredEventListener>> eventMap = new HashMap<>();

    /**
     * Register event handler to the event manager.
     *
     * @param registeredEvent RegisteredEvent instance of the event to register.
     */
    protected void registerEvent(RegisteredEventListener registeredEvent) {
        eventMap.putIfAbsent(registeredEvent.getEvent(), new ArrayList<>());
        getRegisteredEventListenersFor(registeredEvent.getEvent()).add(registeredEvent);
        if (eventManager.isDebugLoggingEnabled()) System.out.println("[EventManager] The event handler '" + registeredEvent.getName() + "' was successful registered.");
    }

    /**
     * Obtain a list of registered event handlers listening to provided event.
     *
     * @param event Handled event instance.
     * @return List of Registered events.
     */
    protected List<RegisteredEventListener> getEventListenersFor(Event event) {
        return getEventListenersFor(event.getClass());
    }

    /**
     * Obtain a list of registered event handlers listening to provided event.
     *
     * @param event Handled event class type.
     * @return List of Registered events.
     */
    protected List<RegisteredEventListener> getEventListenersFor(Class<?> event) {
        return getEventListenersOrderedByPriorityFor(event);
    }

    /**
     * Obtain a ordered list by EventPriority of registered event handlers listening to provided event.
     * The order will come from MONITOR(First) to LOWEST(last).
     *
     * @param event Handled event instance.
     * @return List of Registered events ordered by his EventPriority.
     */
    protected List<RegisteredEventListener> getEventListenersOrderedByPriorityFor(Event event) {
        return getEventListenersOrderedByPriorityFor(event.getClass());
    }

    /**
     * Obtain a ordered list by EventPriority of registered event handlers listening to provided event.
     * The order will come from MONITOR(First) to LOWEST(last).
     *
     * @param event Handled event class type.
     * @return List of Registered events ordered by his EventPriority.
     */
    protected List<RegisteredEventListener> getEventListenersOrderedByPriorityFor(Class<?> event) {
        return getRegisteredEventListenersFor(event).stream()
            .sorted(Comparator.comparing(RegisteredEventListener::getPriority))
            .collect(Collectors.toList());
    }

    /**
     * Obtain all registered event listening for provided event.
     *
     * @param event Handled event instance.
     * @return List of Registered events.
     */
    protected List<RegisteredEventListener> getRegisteredEventListenersFor(Event event) {
        return getRegisteredEventListenersFor(event.getClass());
    }

    /**
     * Obtain all registered event listening for provided event.
     *
     * @param event Handled event class type.
     * @return List of Registered events.
     */
    protected List<RegisteredEventListener> getRegisteredEventListenersFor(Class<?> event) {
        return eventMap.getOrDefault(event, new ArrayList<>());
    }

}
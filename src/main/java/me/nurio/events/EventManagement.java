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
        // Prevent null pointers.
        eventMap.putIfAbsent(registeredEvent.getEvent(), new ArrayList<>());

        // Obtain already registered listeners and add the new one.
        List<RegisteredEventListener> registeredEventListeners = getEventListenersFor(registeredEvent.getEvent());
        registeredEventListeners.add(registeredEvent);

        // Sort event' registered listeners by their assigned priority.
        registeredEventListeners.sort(Comparator.comparing(RegisteredEventListener::getPriority));

        // Debug event listener registration.
        if (eventManager.isDebugLoggingEnabled()) System.out.println("[EventManager] The event handler '" + registeredEvent.getName() + "' was successful registered.");
    }

    /**
     * Obtain all registered event handlers for provided event,
     * they will be sorted by EventPriority of registered event handlers listening to provided event.
     * The order will come from MONITOR(First) to LOWEST(last).
     *
     * @param event Handled event class type.
     * @return List of Registered events sorted by his EventPriority.
     */
    protected List<RegisteredEventListener> getEventListenersFor(Event event) {
        return getEventListenersFor(event.getClass());
    }

    /**
     * Obtain all registered event handlers for provided event,
     * they will be sorted by EventPriority of registered event handlers listening to provided event.
     * The order will come from MONITOR(First) to LOWEST(last).
     *
     * @param event Handled event class type.
     * @return List of Registered events sorted by his EventPriority.
     */
    protected List<RegisteredEventListener> getEventListenersFor(Class<?> event) {
        return eventMap.getOrDefault(event, new ArrayList<>());
    }

}
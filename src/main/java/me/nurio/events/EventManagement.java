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
    private Map<Class<?>, List<RegisteredEventHandler>> eventMap = new HashMap<>();

    /**
     * Lists all registered event handlers.
     *
     * @return List of registered event handlers.
     */
    protected List<RegisteredEventHandler> getRegisteredEvents() {
        return eventMap.values().stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    /**
     * Register event handler to the event manager.
     *
     * @param eventHandler RegisteredEventHandler instance of the event to register.
     */
    protected void registerEvent(RegisteredEventHandler eventHandler) {
        // Prevent null pointers.
        eventMap.putIfAbsent(eventHandler.getEvent(), new ArrayList<>());

        // Obtain already registered listeners and add the new one.
        List<RegisteredEventHandler> registeredEventHandlers = getEventHandlerFor(eventHandler.getEvent());
        registeredEventHandlers.add(eventHandler);

        // Sort event' registered listeners by their assigned priority.
        registeredEventHandlers.sort(Comparator.comparing(RegisteredEventHandler::getPriority));

        // Debug event listener registration.
        if (eventManager.isDebugLoggingEnabled()) System.out.println("[EventManager] The event handler '" + eventHandler.getName() + "' was successful registered.");
    }

    /**
     * Obtain all registered event handlers for provided event,
     * they will be sorted by EventPriority of registered event handlers listening to provided event.
     * The order will come from MONITOR(First) to LOWEST(last).
     *
     * @param event Handled event class type.
     * @return List of Registered events sorted by his EventPriority.
     */
    protected List<RegisteredEventHandler> getEventHandlerFor(Event event) {
        return getEventHandlerFor(event.getClass());
    }

    /**
     * Obtain all registered event handlers for provided event,
     * they will be sorted by EventPriority of registered event handlers listening to provided event.
     * The order will come from MONITOR(First) to LOWEST(last).
     *
     * @param event Handled event class type.
     * @return List of Registered events sorted by his EventPriority.
     */
    protected List<RegisteredEventHandler> getEventHandlerFor(Class<?> event) {
        return eventMap.getOrDefault(event, new ArrayList<>());
    }

}
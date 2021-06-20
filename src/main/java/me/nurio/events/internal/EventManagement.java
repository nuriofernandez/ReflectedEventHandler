package me.nurio.events.internal;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.nurio.events.exceptions.EventHandlerNotFoundException;
import me.nurio.events.handler.Event;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class manages internally event registry.
 */
@RequiredArgsConstructor
class EventManagement {

    @NonNull private final ReflectedEventManager eventManager;

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
     * Un-register event handler from the event manager.
     *
     * @param registeredEvent RegisteredEvent instance of the event to un-register.
     */
    protected void unregisterEvent(RegisteredEventHandler registeredEvent) {
        // Obtain already registered listeners and verify if the provided event is registered.
        List<RegisteredEventHandler> registeredEventListeners = getEventHandlerFor(registeredEvent.getEvent());
        if (!registeredEventListeners.contains(registeredEvent)) {
            throw new EventHandlerNotFoundException(
                String.format("Can not unregister '%s' event cause it's not registered yet.", registeredEvent.getName())
            );
        }

        // Remove provided event
        registeredEventListeners.remove(registeredEvent);

        // Sort event' registered listeners by their assigned priority.
        registeredEventListeners.sort(Comparator.comparing(RegisteredEventHandler::getPriority));

        // Debug event listener registration.
        if (eventManager.isDebugLoggingEnabled()) System.out.println("[EventManager] The event handler '" + registeredEvent.getName() + "' was successful un-registered.");
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
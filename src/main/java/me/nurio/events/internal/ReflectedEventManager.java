package me.nurio.events.internal;

import lombok.Getter;
import lombok.Setter;
import me.nurio.events.EventManager;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventDispatchEvent;
import me.nurio.events.handler.EventListener;
import me.nurio.events.internal.annotations.EventAnnotationHandlersManager;
import me.nurio.events.internal.annotations.EventHandlerAnnotationEventHandler;
import me.nurio.events.internal.annotations.HandledMethod;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class manages the final developer event control.
 */
public class ReflectedEventManager implements EventManager {

    /**
     * Internal management of events registration, un-registration, etc.
     */
    private final EventManagement eventManagement = new EventManagement(this);

    /**
     * Internal management of event handling annotations.
     * To register annotations it requires an event annotation handler.
     */
    private final EventAnnotationHandlersManager handlersManager = new EventAnnotationHandlersManager() {{
        addAnnotationHandler(new EventHandlerAnnotationEventHandler());
    }};

    /**
     * Disables or enables output debug messages from the event system.
     */
    @Setter @Getter private boolean debugLoggingEnabled;

    /**
     * Lists registered event listeners.
     *
     * @return List of registered event listeners instances.
     */
    public List<EventListener> getRegisteredListeners() {
        return eventManagement.getRegisteredEvents().stream()
            .map(RegisteredEventHandler::getListener)
            .distinct()
            .collect(Collectors.toList());
    }

    /**
     * Register event listener at the EventManager to fire its event when some handled event went called.
     *
     * @param listener EventListener event class instance.
     * @param <L>      Event listener class type to register.
     */
    public <L extends EventListener> void registerEvents(L listener) {
        // Obtain event handler methods of the provided EventListener.
        List<HandledMethod> eventHandlerMethods = handlersManager.getHandledMethodsFrom(listener.getClass());

        // Map event handler methods to a RegisterEventHandler instance.
        List<RegisteredEventHandler> eventHandlers = eventHandlerMethods.stream()
            .map(method -> new RegisteredEventHandler(listener, method))
            .collect(Collectors.toList());

        // Register each event handler of the provided EventListener.
        eventHandlers.forEach(eventManagement::registerEvent);
    }

    /**
     * Unregister event listener at the EventManager.
     *
     * @param listener EventListener event class instance.
     * @param <L>      Event listener class type to unregister.
     */
    public <L extends EventListener> void unregisterEvents(L listener) {
        // Obtain all registered event handlers from the EventManagement instance.
        List<RegisteredEventHandler> eventHandlers = eventManagement.getRegisteredEvents().stream()
            .filter(registeredListener -> registeredListener.getListener().equals(listener))
            .collect(Collectors.toList());

        // Unregister each event handler of the provided EventHandler.
        eventHandlers.forEach(eventManagement::unregisterEvent);
    }

    /**
     * Call all event handlers listening for the provided event.
     *
     * @param event Event instance to call.
     * @param <E>   Event class type to call.
     */
    public <E extends Event> void callEvent(E event) {
        // Calling the EventDispatchEvent to allow a generic event handling.
        // This event could cause a slight loss of performance with large scale systems.
        EventDispatchEvent dispatchEvent = new EventDispatchEvent(event);
        for (RegisteredEventHandler eventHandler : eventManagement.getEventHandlerFor(EventDispatchEvent.class)) {
            eventHandler.invoke(dispatchEvent);
        }

        if (dispatchEvent.isCancelled()) {
            // This will cancel the event dispatch. Meaning the event
            // cancellation system will be ignored cause the event has never launched.
            return;
        }

        for (RegisteredEventHandler eventHandler : eventManagement.getEventHandlerFor(event)) {
            // Skip event handling execution for these events that were canceled and are not flagged to 'ignore event cancellation'.
            if (event.isCancelled() && !eventHandler.isIgnoreCancelled()) continue;

            // Invoke the event handler method.
            eventHandler.invoke(event);
        }
    }

}
package me.nurio.events;

import lombok.Getter;
import lombok.Setter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventListener;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class manages the final developer event control.
 */
public class EventManager {

    private final EventManagement eventManagement = new EventManagement(this);

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
        List<Method> eventHandlerMethods = EventReflection.getHandledMethodsFrom(listener.getClass());

        // Map event handler methods to a RegisterEventHandler instance.
        List<RegisteredEventHandler> eventHandlers = eventHandlerMethods.stream()
            .map(method -> new RegisteredEventHandler(this, listener, method))
            .collect(Collectors.toList());

        // Register each event handler of the provided EventListener.
        eventHandlers.forEach(eventManagement::registerEvent);
    }

    /**
     * Call all event handlers listening for the provided event.
     *
     * @param event Event instance to call.
     * @param <E>   Event class type to call.
     */
    public <E extends Event> void callEvent(E event) {
        for (RegisteredEventHandler eventHandler : eventManagement.getEventHandlerFor(event)) {
            // Skip event handling execution for these events that were canceled and are not flagged to 'ignore event cancellation'.
            if (event.isCancelled() && !eventHandler.isIgnoreCancelled()) continue;

            // Invoke the event handler method.
            eventHandler.invoke(event);
        }
    }

}
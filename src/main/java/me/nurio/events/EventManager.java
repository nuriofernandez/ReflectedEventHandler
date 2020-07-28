package me.nurio.events;

import lombok.Getter;
import lombok.Setter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventListener;

import java.lang.reflect.Method;
import java.util.List;

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
     * Register event listener at the EventManager to fire its event when some handled event went called.
     *
     * @param listener EventListener event class instance.
     * @param <L>      Event listener class type to register.
     */
    public <L extends EventListener> void registerEvents(L listener) {
        List<Method> eventListeners = EventReflection.getHandledMethodsFrom(listener.getClass());
        eventListeners.forEach(method -> eventManagement.registerEvent(new RegisteredEventListener(this, listener, method)));
    }

    /**
     * Call all event handlers listening for the provided event.
     *
     * @param event Event instance to call.
     * @param <E>   Event class type to call.
     */
    public <E extends Event> void callEvent(E event) {
        eventManagement.getEventListenersFor(event).stream()
            .filter(listener -> !event.isCancelled() || event.isCancelled() && listener.isIgnoreCancelled())
            .forEach(listener -> listener.invoke(event));
    }

}
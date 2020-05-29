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

    /**
     * Disables or enables output debug messages from the event system.
     */
    @Setter @Getter private static boolean debugLoggingEnabled;

    /**
     * Register event listener at the EventManager to fire its event when some handled event went called.
     *
     * @param listener EventListener event class instance.
     */
    public static <L extends EventListener> void registerEvents(L listener) {
        List<Method> eventListeners = EventReflection.getHandledMethodsFrom(listener.getClass());
        eventListeners.forEach(method -> EventManagement.registerEvent(new RegisteredEventListener(listener, method)));
    }

    /**
     * Call all event handlers listening for the provided event.
     *
     * @param event Event instance to call.
     */
    public static <E extends Event> void callEvent(E event) {
        EventManagement.getEventListenersFor(event).stream()
            .filter(listener -> !event.isCancelled() || event.isCancelled() && listener.isIgnoreCancelled())
            .forEach(listener -> listener.invoke(event));
    }

}
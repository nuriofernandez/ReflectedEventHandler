package me.nurio.events;

import lombok.AccessLevel;
import lombok.Getter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventListener;
import me.nurio.events.handler.EventPriority;

import java.lang.reflect.Method;

/**
 * This class maps an @EventHandler method registered in an EventManager.
 */
public class RegisteredEventHandler {

    private final EventManager eventManager;
    private Method method;

    @Getter(AccessLevel.PACKAGE)
    private Class<?> event;

    @Getter private EventListener listener;

    @Getter private EventPriority priority;
    @Getter private boolean ignoreCancelled;
    @Getter private String name;

    public RegisteredEventHandler(EventManager eventManager, EventListener listener, Method method) {
        this.eventManager = eventManager;
        this.listener = listener;
        this.method = method;

        name = method.getDeclaringClass().getCanonicalName() + "#" + method.getName();
        event = EventReflection.getEventFromMethod(method);
        priority = EventReflection.getEventPriorityFromMethod(method);
        ignoreCancelled = EventReflection.getIgnoreCancelledFromMethod(method);
    }

    /**
     * Execute the register event.
     *
     * @param event Called event instance.
     */
    public void invoke(Event event) {
        try {
            method.invoke(listener, event);
            if (eventManager.isDebugLoggingEnabled()) System.out.println("[EventManager] Launching '" + name + "' event handler.");
        } catch (Exception e) {
            System.err.println("[EventManager] Error launching '" + name + "' event handler.");
            e.printStackTrace();
        }
    }

}
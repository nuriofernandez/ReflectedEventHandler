package me.nurio.events;

import lombok.AccessLevel;
import lombok.Getter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventPriority;
import me.nurio.events.handler.EventListener;

import java.lang.reflect.Method;

/**
 * This class will map Registered Event Listener for using it as an object.
 */
public class RegisteredEventListener {

    private EventListener listener;
    private Method method;

    @Getter(AccessLevel.PACKAGE)
    private Class<?> event;

    @Getter private EventPriority priority;
    @Getter private String name;

    public RegisteredEventListener(EventListener listener, Method method) {
        this.listener = listener;
        this.method = method;

        name = method.getName();
        event = EventReflection.getEventFromMethod(method);
        priority = EventReflection.getEventPriorityFromMethod(method);
    }

    /**
     * Execute the register event.
     *
     * @param event Called event instance.
     */
    public void invoke(Event event) {
        try {
            method.invoke(listener, event);
            System.out.println("[EventManager] Launching '" + name + "' event.");
        } catch (Exception e) {
            throw new RuntimeException("[EventManager] Error launching '" + name + "' event.", e);
        }
    }

}
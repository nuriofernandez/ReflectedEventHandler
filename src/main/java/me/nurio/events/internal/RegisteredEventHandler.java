package me.nurio.events.internal;

import lombok.AccessLevel;
import lombok.Getter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventListener;
import me.nurio.events.handler.EventPriority;
import me.nurio.events.internal.annotations.AnnotationEventHandler;
import me.nurio.events.internal.annotations.HandledMethod;

import java.lang.reflect.Method;

/**
 * This class maps an @EventHandler method registered in an EventManager.
 */
public class RegisteredEventHandler {

    private Method method;
    private AnnotationEventHandler handler;

    @Getter(AccessLevel.PACKAGE)
    private Class<? extends Event> event;

    @Getter private EventListener listener;

    @Getter private EventPriority priority;
    @Getter private boolean ignoreCancelled;
    @Getter private String name;

    public RegisteredEventHandler(EventListener listener, HandledMethod handledMethod) {
        this.listener = listener;

        method = handledMethod.getMethod();
        handler = handledMethod.getHandler();

        name = method.getDeclaringClass().getCanonicalName() + "#" + method.getName();
        event = EventReflectionUtils.getEventFromMethod(method);
        priority = handler.getEventPriority(method);
        ignoreCancelled = handler.shouldIgnoreCancellable(method);
    }

    /**
     * Execute the register event.
     *
     * @param event Called event instance.
     */
    public void invoke(Event event) {
        try {
            method.invoke(listener, event);
        } catch (Exception e) {
            System.err.println("[EventManager] Error launching '" + name + "' event handler.");
            e.printStackTrace();
        }
    }

}
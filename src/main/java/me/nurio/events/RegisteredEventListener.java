package me.nurio.events;

import lombok.AccessLevel;
import lombok.Getter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventPriority;
import me.nurio.events.handler.EventListener;

import java.lang.reflect.Method;

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

    public void invoke(Event event) {
        try {
            method.invoke(listener, event);
            System.out.println("Launched event '" + name + "'");
        } catch (Exception e) {
            throw new RuntimeException("Error dispatching event '" + name + "'", e);
        }
    }

}
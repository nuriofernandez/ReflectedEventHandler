package me.nurio.events;

import lombok.Getter;

import java.lang.reflect.Method;

public class RegisteredEventListener {

    private Listener listener;
    private Method method;

    @Getter private Class<?> event;

    public RegisteredEventListener(Listener listener, Method method) {
        this.listener = listener;
        this.method = method;

        event = EventReflection.getEventFromMethod(method);
    }

    public String getName() {
        return method.getName();
    }

    public void invoke(Event event) {
        try {
            method.invoke(listener, event);
            System.out.println("Launched event '" + method.getName() + "'");
        } catch (Exception e) {
            throw new RuntimeException("Error dispatching event '" + method.getName() + "'", e);
        }
    }

}
package me.nurio.events;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class EventManager {

    // Event list

    private static Map<Class<?>, List<RegisteredEventListener>> eventMap = new HashMap<>();

    protected static List<RegisteredEventListener> getEventListenersFor(Class<?> event) {
        return eventMap.getOrDefault(event, new ArrayList<>());
    }

    protected static void addEventListenerTo(Class<?> event, RegisteredEventListener eventListener) {
        eventMap.putIfAbsent(event, new ArrayList<>());
        getEventListenersFor(event).add(eventListener);
    }

    // Event registration

    public static <L extends Listener> void registerEvents(L listener) {
        List<Method> eventListeners = getMethodsWithEventHandlerFrom(listener.getClass());
        eventListeners.forEach(method -> registerEvent(new RegisteredEventListener(listener, method)));
    }

    private static <L extends Listener> void registerEvent(RegisteredEventListener eventMethod) {
        Class<?> event = getEventFromMethod(eventMethod.getMethod());
        addEventListenerTo(event, eventMethod);

        System.out.println("Registered event => " + eventMethod.getMethod().getName());
    }

    public static Class<?> getEventFromMethod(Method method) {
        if (method.getParameterCount() != 1) {
            throw new RuntimeException("Trying to register invalid event handler.");
        }

        Class<?> parameter = method.getParameterTypes()[0];
        Class<?> superClass = parameter.getSuperclass();
        if (!superClass.equals(Event.class)) {
            throw new RuntimeException("Trying to register invalid event type.");
        }

        try {
            return parameter;
        } catch (Exception er) {
            throw new RuntimeException("Error at the recognition of the event type");
        }
    }

    /**
     * This method will obtain all methods with the EventHandler annotation.
     *
     * @param classListener
     * @return
     */
    public static List<Method> getMethodsWithEventHandlerFrom(Class<?> classListener) {
        return Arrays.stream(classListener.getMethods())
                .filter(method -> Objects.nonNull(method.getAnnotation(EventHandler.class)))
                .collect(Collectors.toList());
    }


    public static <E extends Event> void callEvent(E event) {
        for (RegisteredEventListener registeredEvent : getEventListenersFor(event.getClass())) {
            Method method = registeredEvent.getMethod();
            try {
                method.invoke(registeredEvent.getListener(), event);
                System.out.println("Launched event '" + method.getName() + "'");
            } catch (Exception er) {
                throw new RuntimeException("Error dispatching event '" + method.getName() + "'", er);
            }
        }
    }

}

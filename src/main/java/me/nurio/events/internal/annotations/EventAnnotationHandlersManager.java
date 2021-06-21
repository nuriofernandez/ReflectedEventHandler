package me.nurio.events.internal.annotations;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class will handle with event handler's annotation related checks.
 */
public class EventAnnotationHandlersManager {

    /**
     * List of annotation event handler for this manager instance.
     */
    private List<AnnotationEventHandler> annotationHandlers = new ArrayList<>();

    /**
     * Register a annotation event handler to this manager.
     *
     * @param annotationHandler Annotation event handler to register to this manager.
     */
    public void addAnnotationHandler(AnnotationEventHandler annotationHandler) {
        annotationHandlers.add(annotationHandler);
    }

    /**
     * Check if specified method its an Event handler.
     *
     * @param method Specified method to check.
     * @return true if its a event handler method.
     */
    public List<AnnotationEventHandler> getHandlers(Method method) {
        if (method.getAnnotations().length <= 0) return new ArrayList<>();

        return annotationHandlers.stream()
            .filter(handler -> handler.shouldHandle(method))
            .collect(Collectors.toList());
    }

    /**
     * Check if specified method its an Event handler.
     *
     * @param method Specified method to check.
     * @return true if its an event handler method.
     */
    public boolean hasHandlers(Method method) {
        return !getHandlers(method).isEmpty();
    }

    /**
     * This method will obtain all handled methods with at least one event handling annotation.
     * In case some method has more than one handling annotation it will be obtained twice, one for each handler.
     *
     * @param classListener Event listener class to scan.
     * @return List of handled methods matching criteria.
     */
    public List<HandledMethod> getHandledMethodsFrom(Class<?> classListener) {
        return handledMethodsFrom(classListener)
            .stream()
            .flatMap(method -> getHandlers(method).stream()
                .map(handler -> new HandledMethod(method, handler))
            )
            .collect(Collectors.toList());
    }

    /**
     * Obtains all methods with at least one event handler annotation.
     *
     * @param classListener Event listener class to scan.
     * @return List of reflection methods matching criteria.
     */
    protected List<Method> handledMethodsFrom(Class<?> classListener) {
        return Arrays.stream(classListener.getMethods())
            .filter(this::hasHandlers)
            .collect(Collectors.toList());
    }

}
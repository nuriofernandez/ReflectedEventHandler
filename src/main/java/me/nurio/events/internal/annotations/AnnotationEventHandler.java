package me.nurio.events.internal.annotations;

import me.nurio.events.handler.EventPriority;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface AnnotationEventHandler {

    Class<? extends Annotation> getAnnotation();

    default boolean shouldHandle(Method method) {
        // Default implementation of the handler is to match the annotation.
        return method.getAnnotation(getAnnotation()) != null;
    }

    default EventPriority getEventPriority(Method method) {
        // Default implementation in case there is no support for event priorities.
        return EventPriority.NORMAL;
    }

    default boolean shouldIgnoreCancellable(Method method) {
        // Default implementation in case there is no support for event cancellation ignorance.
        return false;
    }

}
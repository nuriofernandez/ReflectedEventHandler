package me.nurio.events.internal.annotations;

import me.nurio.events.handler.EventPriority;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Represents an annotation event handler.
 * To create a custom annotation it is required to create a AnnotationEventHandler implementation too.
 */
public interface AnnotationEventHandler {

    /**
     * The annotation class that will be used to handle events with this handler.
     */
    Class<? extends Annotation> getAnnotation();

    /**
     * This method should return true in case the requested method matches the
     * annotation handler criteria. It could be the event type, the annotations
     * of the method, or any other property.
     * <p>
     * In case the method doesn't match the handler criteria, return false to make
     * the annotation handler manager ignore that method from this handler.
     *
     * @param method Method to be handled.
     * @return 'true' in case this annotation handler matches the method properties.
     */
    default boolean shouldHandle(Method method) {
        // Default implementation of the handler is to match the annotation.
        return method.getAnnotation(getAnnotation()) != null;
    }

    /**
     * Should obtain the {@link EventPriority} from reflection method.
     *
     * @param method Reflected method to find the EventPriority.
     * @return EventPriority of the event method.
     */
    default EventPriority getEventPriority(Method method) {
        // Default implementation in case there is no support for event priorities.
        return EventPriority.NORMAL;
    }

    /**
     * Should obtain the expected event cancellation behavior from reflection method.
     *
     * @param method Reflected method to obtain the expected cancellation behavior.
     * @return 'true' when it should ignore event cancellations.
     */
    default boolean shouldIgnoreCancellable(Method method) {
        // Default implementation in case there is no support for event cancellation ignorance.
        return false;
    }

}
package me.nurio.events.internal.annotations;

import lombok.Getter;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventPriority;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Default @EventHandler annotation handler.
 */
public class EventHandlerAnnotationEventHandler implements AnnotationEventHandler {

    /**
     * The annotation class that will be used to handle events with this handler.
     */
    @Getter private final Class<? extends Annotation> annotation = EventHandler.class;

    /**
     * Obtain {@link EventPriority} from reflection method.
     *
     * @param method Reflected method to find the EventPriority.
     * @return EventPriority of the event method.
     */
    @Override
    public EventPriority getEventPriority(Method method) {
        EventHandler handler = method.getAnnotation(EventHandler.class);
        return handler.priority();
    }

    /**
     * Obtain 'ignoreCancelled' value from reflection method.
     *
     * @param method Reflected method to get the 'ignoreCancelled' value.
     * @return 'true' when ignoreCancelled was enabled.
     */
    @Override
    public boolean shouldIgnoreCancellable(Method method) {
        EventHandler handler = method.getAnnotation(EventHandler.class);
        return handler.ignoreCancelled();
    }

}
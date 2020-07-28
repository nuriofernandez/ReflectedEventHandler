package me.nurio.events.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation will mark methods as Event Handler Listeners.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

    /**
     * Mark the event run priority ordered from LOWEST(last) to MONITOR(first).
     *
     * @return Priority level of target event.
     */
    EventPriority priority() default EventPriority.NORMAL;

    /**
     * Ignore the cancellation of the event an run them inclusive when
     * it has been cancelled previously by another event handler.
     *
     * @return true when event should be executed inclusive if cancelled.
     */
    boolean ignoreCancelled() default false;

}
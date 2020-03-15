package me.nurio.events.handler;

import me.nurio.events.EventManager;

/**
 * Represents an event.
 *
 * @see EventManager
 * @see EventCancellable
 */
public abstract class Event {

    /**
     * Assert if the event can be cancelled.
     *
     * @return true if cancellable
     */
    public boolean isCancellable() {
        return (this instanceof EventCancellable);
    }

    /**
     * Check if the event is cancellable and it was cancelled.
     *
     * @return 'true' when was cancelled.
     */
    public boolean isCancelled() {
        return isCancellable() && ((EventCancellable) this).isCancelled();
    }

}
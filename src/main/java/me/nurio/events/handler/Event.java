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

}
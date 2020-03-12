package me.nurio.events.handler;

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
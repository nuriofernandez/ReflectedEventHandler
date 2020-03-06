package me.nurio.events;

public abstract class Event {

    /**
     * Assert if the event can be cancelled.
     *
     * @return true if cancellable
     */
    public boolean isCancellable() {
        return (this instanceof Cancellable);
    }

}
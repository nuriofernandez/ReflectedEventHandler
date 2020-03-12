package me.nurio.events.handler;

/**
 * This interface will generify the way to cancel events adding the isCancelled() and setCancelled() methods.
 */
public interface EventCancellable {

    /**
     * Validate if the cancellable event was cancelled.
     *
     * @return true if cancelled.
     */
    boolean isCancelled();

    /**
     * Set the cancellable event as cancelled or not.
     *
     * @param cancelled true to set cancelled, false to set uncancelled.
     */
    void setCancelled(boolean cancelled);

}
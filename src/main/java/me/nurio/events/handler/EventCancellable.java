package me.nurio.events.handler;

public interface EventCancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);

}
package me.nurio.events.handler;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);

}
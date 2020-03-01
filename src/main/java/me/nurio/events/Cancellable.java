package me.nurio.events;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);

}
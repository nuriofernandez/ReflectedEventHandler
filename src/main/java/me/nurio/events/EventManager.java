package me.nurio.events;

import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventListener;

import java.util.List;

/**
 * Handles the registration and unregistration of events listeners, event listeners listing and event call broadcasting.
 */
public interface EventManager {

    /**
     * Obtains a list of registered event listeners.
     *
     * @return List of registered event listeners.
     */
    List<EventListener> getRegisteredListeners();

    /**
     * Registers a new event listener class to the event manager.
     *
     * @param listener Event listener to register.
     * @param <L>      Allows user's custom event listeners to work with the event manager.
     */
    <L extends EventListener> void registerEvents(L listener);

    /**
     * Unregisters an already registered event listener class from the event manager.
     *
     * @param listener Event listener to unregister.
     * @param <L>      Allows user's custom event listeners to work with the event manager.
     */
    <L extends EventListener> void unregisterEvents(L listener);

    /**
     * Calls the provided events throw all the registered event managers.
     *
     * @param event Event to broadcast throw the registered listeners.
     * @param <E>   Allow user's custom event types to work with the event manager.
     */
    <E extends Event> void callEvent(E event);

}
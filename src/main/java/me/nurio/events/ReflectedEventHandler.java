package me.nurio.events;

import me.nurio.events.internal.ReflectedEventManager;

/**
 * Main entry point of the ReflectedEventHandler library.
 */
public class ReflectedEventHandler {

    /**
     * Creates a new instance of EventManager to use it around applications.
     *
     * @return EventManager instance ready to be used.
     */
    public static ReflectedEventManager createEventManager() {
        return new ReflectedEventManager();
    }

}
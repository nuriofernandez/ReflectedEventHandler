package me.nurio.events.internal.eventmanager;

import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventListener;

import java.util.List;

public interface StandardEventManager {

    List<EventListener> getRegisteredListeners();

    <L extends EventListener> void registerEvents(L listener);

    <L extends EventListener> void unregisterEvents(L listener);

    <E extends Event> void callEvent(E event);

}
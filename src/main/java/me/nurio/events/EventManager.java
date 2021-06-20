package me.nurio.events;

import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventListener;

import java.util.List;

public interface EventManager {

    List<EventListener> getRegisteredListeners();

    <L extends EventListener> void registerEvents(L listener);

    <L extends EventListener> void unregisterEvents(L listener);

    <E extends Event> void callEvent(E event);

}
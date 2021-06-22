package me.nurio.events.internal.eventmanager;

import me.nurio.events.handler.ElasticEvent;
import me.nurio.events.handler.Event;

public interface ElasticEventManager extends StandardEventManager {

    default <R, E extends ElasticEvent<R>> R callEvent(E event) {
        callEvent((Event) event);
        return null;
    }

}
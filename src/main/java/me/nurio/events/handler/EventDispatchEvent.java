package me.nurio.events.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * This event represents the dispatching of an event. It is going to be called for all the events.
 * The cancellation of this event will prevent the launch of the dispatching event, cause the event
 * is not going to be launched, the event cancellation system is not going to work with it.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class EventDispatchEvent extends Event implements EventCancellable {

    /**
     * Cancelling this event will prevent the dispatching of the called event.
     * Cause the event is not going to be launched, the event cancellation system is not going to work with it.
     */
    private boolean cancelled;

    /**
     * The event what is going to be dispatched.
     */
    private final Event event;

}
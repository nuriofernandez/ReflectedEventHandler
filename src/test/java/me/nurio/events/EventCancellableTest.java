package me.nurio.events;

import me.nurio.events.testclasses.CancellableEvent;
import me.nurio.events.testclasses.IgnoredCancelledEventTestListener;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventCancellableTest {

    private EventManager eventManager;

    @Before
    public void registerEvent() {
        eventManager = new EventManager();
        eventManager.registerEvents(new IgnoredCancelledEventTestListener());
    }

    @Test
    public void callEvent_shouldIgnoreEventsAfterCancelled() {
        // Create CancellableEvent and call it.
        CancellableEvent event = new CancellableEvent("This should be changed.");
        eventManager.callEvent(event);

        // Assert results.
        assertTrue(event.isCancelled());
        assertEquals("CancelledIgnoreCancelled", event.getName());
    }

}
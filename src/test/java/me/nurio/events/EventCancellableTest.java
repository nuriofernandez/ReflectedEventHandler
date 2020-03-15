package me.nurio.events;

import me.nurio.events.testclasses.CancellableEvent;
import me.nurio.events.testclasses.IgnoredCancelledEventTestListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventCancellableTest {

    @Before
    public void registerEvent() {
        EventManager.registerEvents(new IgnoredCancelledEventTestListener());
    }

    @Test
    public void callEvent_shouldIgnoreEventsAfterCancelled() {
        // Create CancellableEvent and call it.
        CancellableEvent event = new CancellableEvent("This should be changed.");
        EventManager.callEvent(event);

        // Assert results.
        assertTrue(event.isCancelled());
        assertEquals("CancelledIgnoreCancelled", event.getName());
    }

}
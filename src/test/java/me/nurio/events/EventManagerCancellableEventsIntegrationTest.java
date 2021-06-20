package me.nurio.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventCancellable;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import me.nurio.events.internal.ReflectedEventManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests that event cancellation works as expected.
 */
public class EventManagerCancellableEventsIntegrationTest {

    private EventManager eventManager;

    @Before
    public void registerEventManager() {
        eventManager = new ReflectedEventManager();
        eventManager.registerEvents(new EventCancellationListener());
    }

    @Test
    public void canceledEvent_shouldBeCancelled_whenEventListenerCancelsIt() {
        CancellableEvent event = new CancellableEvent(true);
        eventManager.callEvent(event);
        assertTrue(event.isCancelled());
    }

    @Test
    public void nonCanceledEvent_shouldBeNotCancelled_whenEventListenerDoesNotCancelsIt() {
        CancellableEvent event = new CancellableEvent(false);
        eventManager.callEvent(event);
        assertFalse(event.isCancelled());
    }

    /**
     * This class will cancel events flagged to be canceled.
     */
    public static class EventCancellationListener implements EventListener {
        @EventHandler
        public void cancellableEvent(CancellableEvent event) {
            event.setCancelled(event.isToBeCancelled());
        }
    }

    /**
     * This event will be used to test event cancellation.
     */
    @RequiredArgsConstructor
    public static class CancellableEvent extends Event implements EventCancellable {
        @Getter @Setter private final boolean toBeCancelled;
        @Getter @Setter private boolean cancelled;
    }

}
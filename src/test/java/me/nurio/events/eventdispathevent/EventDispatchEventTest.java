package me.nurio.events.eventdispathevent;

import me.nurio.events.EventManager;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventDispatchEvent;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import me.nurio.events.internal.ReflectedEventManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test will ensure that the EventDispatchEvent is being fired for all the event calls.
 */
class EventDispatchEventTest {

    private EventManager eventManager;
    private static boolean hasBeenCalled;

    @BeforeEach
    void registerEventManager() {
        eventManager = new ReflectedEventManager();
        eventManager.registerEvents(new TestListener());
        hasBeenCalled = false;
    }

    @Test
    void eventDispatchEventIsBeingCalled() {
        assertFalse(hasBeenCalled);
        eventManager.callEvent(new TestEvent());
        assertTrue(hasBeenCalled);
    }

    /**
     * This event listener proves that event handled methods will be called.
     */
    public static class TestListener implements EventListener {
        @EventHandler
        public void updateFieldName(EventDispatchEvent event) {
            hasBeenCalled = true;
        }
    }

    /**
     * This event is used to prove that event handler methods are called as expected.
     */
    public static class TestEvent extends Event {}

}
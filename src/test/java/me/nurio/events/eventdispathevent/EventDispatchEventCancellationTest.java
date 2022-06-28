package me.nurio.events.eventdispathevent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
 * This test will ensure that the EventDispatchEvent cancellation prevents the event from being dispatched.
 */
class EventDispatchEventCancellationTest {

    private EventManager eventManager;

    @BeforeEach
    void registerEventManager() {
        eventManager = new ReflectedEventManager();
        eventManager.registerEvents(new TestListener());
    }

    @Test
    void eventDispatchEventCancellationIsPreventingEventCall() {
        TestEvent testEvent = new TestEvent("cancel-me");
        eventManager.callEvent(testEvent);
        assertFalse(testEvent.isCalled());
    }

    @Test
    void eventDispatchEventListeningIsNotPreventingEventCall() {
        TestEvent testEvent = new TestEvent("random-name-not-cancelling");
        eventManager.callEvent(testEvent);
        assertTrue(testEvent.isCalled());
    }

    /**
     * This event listener proves that event handled methods will be called.
     */
    public static class TestListener implements EventListener {
        @EventHandler
        public void dispatchEvent(EventDispatchEvent event) {
            TestEvent testEvent = (TestEvent) event.getEvent();
            if (testEvent.getName().equalsIgnoreCase("cancel-me")) {
                event.setCancelled(true);
            }
        }

        @EventHandler
        public void updateFieldName(TestEvent event) {
            event.setCalled(true);
        }
    }

    /**
     * This event is used to prove that event handler methods are called as expected.
     */
    @RequiredArgsConstructor
    public static class TestEvent extends Event {
        @Getter private final String name;
        @Getter @Setter private boolean called;
    }

}
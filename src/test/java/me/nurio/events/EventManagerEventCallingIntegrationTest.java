package me.nurio.events;

import lombok.Getter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import me.nurio.events.internal.ReflectedEventManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class will prove that EventManager#callEvent calls all matching event handlers.
 */
class EventManagerEventCallingIntegrationTest {

    private EventManager eventManager;

    @BeforeEach
    void registerEventManager() {
        eventManager = new ReflectedEventManager();
        eventManager.registerEvents(new EventModificationCounterListener());
        eventManager.registerEvents(new EventModificationCounterTwoListener());
    }

    @Test
    void everyMatchingEventHandler_shouldBeExecuted_whenCallingMatchingEvent() {
        ModificationCounterEvent event = new ModificationCounterEvent();
        eventManager.callEvent(event);
        assertEquals(3, event.getTimes());
    }

    /**
     * This class will cancel events flagged to be canceled.
     */
    public static class EventModificationCounterListener implements EventListener {
        @EventHandler
        public void one(ModificationCounterEvent event) {
            event.increment();
        }

        @EventHandler
        public void two(ModificationCounterEvent event) {
            event.increment();
        }
    }

    /**
     * This class will cancel events flagged to be canceled.
     */
    public static class EventModificationCounterTwoListener implements EventListener {
        @EventHandler
        public void three(ModificationCounterEvent event) {
            event.increment();
        }
    }

    /**
     * This event will be used to test event cancellation.
     */
    public static class ModificationCounterEvent extends Event {
        @Getter private int times;

        public void increment() {
            times++;
        }
    }

}

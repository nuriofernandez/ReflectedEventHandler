package me.nurio.events;

import lombok.Getter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class will prove that EventManager#callEvent calls all matching event handlers.
 */
public class EventManagerEventCallingIntegrationTest {

    private EventManager eventManager;

    @Before
    public void registerEventManager() {
        eventManager = new EventManager();
        eventManager.registerEvents(new EventModificationCounterListener());
        eventManager.registerEvents(new EventModificationCounterTwoListener());
    }

    @Test
    public void everyMatchingEventHandler_shouldBeExecuted_whenCallingMatchingEvent() {
        ModificationCounterEvent event = new ModificationCounterEvent();
        eventManager.callEvent(event);
        assertEquals(3, event.getTimes());
    }

    /**
     * This class will cancel events flagged to be canceled.
     */
    private static class EventModificationCounterListener implements EventListener {
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
    private static class EventModificationCounterTwoListener implements EventListener {
        @EventHandler
        public void three(ModificationCounterEvent event) {
            event.increment();
        }
    }

    /**
     * This event will be used to test event cancellation.
     */
    private static class ModificationCounterEvent extends Event {
        @Getter private int times;

        public void increment() {
            times++;
        }
    }

}

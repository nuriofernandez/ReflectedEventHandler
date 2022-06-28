package me.nurio.events;

import lombok.Getter;
import lombok.Setter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import me.nurio.events.internal.ReflectedEventManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class proves the behavior of the EventManager event registering and event calling.
 */
class EventManagerEventCallingTest {

    private EventManager eventManager;

    @BeforeEach
    void registerEventManager() throws NoSuchFieldException, IllegalAccessException {
        eventManager = new ReflectedEventManager();
    }

    @Test
    void callEvent_shouldUpdateTestNameField_whenTestEventUpdateFieldNameUpdatesTheField() {
        // Register events
        eventManager.registerEvents(new TestListener());

        // Perform event call
        TestEvent testEvent = new TestEvent();
        testEvent.setTestName("Random");
        eventManager.callEvent(testEvent);

        // Assert changes
        assertEquals("Changed", testEvent.getTestName());
    }

    /**
     * This event listener proves that event handled methods will be called.
     */
    public static class TestListener implements EventListener {
        @EventHandler
        public void updateFieldName(TestEvent event) {
            event.setTestName("Changed");
            // This should be included in the event method list.
        }
    }

    /**
     * This event is used to prove that event handler methods are called as expected.
     */
    public static class TestEvent extends Event {
        @Getter @Setter private String testName;
    }

}
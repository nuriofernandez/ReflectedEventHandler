package me.nurio.events;

import lombok.Getter;
import lombok.Setter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * This class proves the behavior of the EventManager event registering and event calling.
 */
public class EventManagerEventCallingTest {

    private EventManager eventManager;
    private EventManagement eventManagement;

    @Before
    public void registerEventManager() throws NoSuchFieldException, IllegalAccessException {
        eventManager = new EventManager();
        eventManagement = getEventManagement();
    }

    @Test
    public void callEvent_shouldUpdateTestNameField_whenTestEventUpdateFieldNameUpdatesTheField() {
        // Register events
        eventManager.registerEvents(new TestListener());

        // Perform event call
        TestEvent testEvent = new TestEvent();
        testEvent.setTestName("Random");
        eventManager.callEvent(testEvent);

        // Assert changes
        assertEquals("Changed", testEvent.getTestName());
    }

    private EventManagement getEventManagement() throws NoSuchFieldException, IllegalAccessException {
        Field field = eventManager.getClass().getDeclaredField("eventManagement");
        field.setAccessible(true);
        return (EventManagement) field.get(eventManager);
    }

    /**
     * This event listener proves that event handled methods will be called.
     */
    private static class TestListener implements EventListener {
        @EventHandler
        public void updateFieldName(TestEvent event) {
            event.setTestName("Changed");
            // This should be included in the event method list.
        }
    }

    /**
     * This event is used to prove that event handler methods are called as expected.
     */
    private static class TestEvent extends Event {
        @Getter @Setter private String testName;
    }

}
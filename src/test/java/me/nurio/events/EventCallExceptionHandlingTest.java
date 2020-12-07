package me.nurio.events;

import lombok.Getter;
import lombok.Setter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class will test the behavior of an event handler that throws an exception.
 */
public class EventCallExceptionHandlingTest {

    private EventManager eventManager;

    @Before
    public void registerEventManager() {
        eventManager = new EventManager();
    }

    @Test
    public void callingEventHandlerThatThrowExceptions_shouldNotStopTheEventExecutionFlow() {
        // Register event listeners
        eventManager.registerEvents(new TestListener());
        eventManager.registerEvents(new TestThatThrowsExceptionsListener());

        // Create an event and call it
        TestEvent event = new TestEvent();
        eventManager.callEvent(event);

        // Assert expected behavior
        assertEquals("Changed", event.getTestName());
    }

    /**
     * This event listener will be used to prove that in case any other event handler throws an exception this will be executed anyway.
     */
    private static class TestListener implements EventListener {
        @EventHandler
        public void updateFieldName(TestEvent event) {
            event.setTestName("Changed");
        }
    }

    /**
     * This event listener will be used to prove the behavior or an event handler that throws an exception.
     */
    private static class TestThatThrowsExceptionsListener implements EventListener {
        @EventHandler
        public void exceptionThrower(TestEvent event) {
            throw new RuntimeException("Testing what happens when something goes wrong!");
        }
    }

    /**
     * This event will be used to test the exception throwing behavior.
     */
    private static class TestEvent extends Event {
        @Getter @Setter private String testName;
    }

}
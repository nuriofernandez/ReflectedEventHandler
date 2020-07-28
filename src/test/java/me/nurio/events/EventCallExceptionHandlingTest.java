package me.nurio.events;

import me.nurio.events.testclasses.TestEvent;
import me.nurio.events.testclasses.TestExceptionListener;
import me.nurio.events.testclasses.TestListener;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

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
        eventManager.registerEvents(new TestExceptionListener());

        // Create an event and call it
        TestEvent event = new TestEvent();
        eventManager.callEvent(event);

        // Assert expected behavior
        assertEquals("Changed", event.getTestName());
    }

}
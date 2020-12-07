package me.nurio.events;

import lombok.Getter;
import lombok.Setter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This class proves the behavior of the EventManager event registering and event calling.
 */
public class EventManagerRegistrationManagementTest {

    private EventManager eventManager;
    private EventManagement eventManagement;

    @Before
    public void registerEventManager() throws NoSuchFieldException, IllegalAccessException {
        eventManager = new EventManager();
        eventManagement = getEventManagement();
    }

    @Test
    public void registerEvents_shouldRegisterMethodsWithEventHandler_whenTheyAreCorrectlyWritten() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        // Register events
        eventManager.registerEvents(new TestListener());

        // Obtain method listeners
        Method method = eventManagement.getClass().getDeclaredMethod("getEventHandlerFor", Class.class);
        method.setAccessible(true);
        List<RegisteredEventHandler> registeredListeners = (List<RegisteredEventHandler>) method.invoke(eventManagement, TestEvent.class);
        RegisteredEventHandler eventListener = registeredListeners.get(0);

        // Assert data
        assertEquals(1, registeredListeners.size());
        assertEquals("me.nurio.events.EventManagerRegistrationManagementTest.TestListener#updateFieldName", eventListener.getName());
    }

    @Test
    public void unregisterEvents_shouldUnregisterRegisteredEventHandlerMethods_whenProvidedListenerAreAlreadyRegistered() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        // Event to register & unregister
        TestListener testListener = new TestListener();

        // Register events
        eventManager.registerEvents(testListener);

        // Obtain method listeners
        Method method = eventManagement.getClass().getDeclaredMethod("getEventHandlerFor", Class.class);
        method.setAccessible(true);
        List<RegisteredEventHandler> registeredListeners = (List<RegisteredEventHandler>) method.invoke(eventManagement, TestEvent.class);
        RegisteredEventHandler eventListener = registeredListeners.get(0);

        // Assert registered data
        assertEquals(1, registeredListeners.size());
        assertEquals("me.nurio.events.EventManagerRegistrationManagementTest.TestListener#updateFieldName", eventListener.getName());

        // Unregister and assert unregistered data
        eventManager.unregisterEvents(testListener);
        assertEquals(0, registeredListeners.size());
    }

    private EventManagement getEventManagement() throws NoSuchFieldException, IllegalAccessException {
        Field field = eventManager.getClass().getDeclaredField("eventManagement");
        field.setAccessible(true);
        return (EventManagement) field.get(eventManager);
    }

    /**
     * This event listener proves that invalid event handlers will be ignore and valid ones will be call.
     */
    private static class TestListener implements EventListener {
        @EventHandler
        public void updateFieldName(TestEvent event) {
            event.setTestName("Changed");
            // This should be included in the event method list.
        }

        public void nonEventMethod() {
            // This should be excluded from the event method list because they don't have the @EventHandler annotation.
        }
    }

    /**
     * This event is used to prove that valid event handlers are called as expected.
     */
    private static class TestEvent extends Event {
        @Getter @Setter private String testName;
    }

}
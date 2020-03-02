package me.nurio.events;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class EventManagerTest {

    @Test
    public void getMethodsWithEventHandlerFrom_shouldReturnAllMethodWithTheEventHandlerAnnotation() {
        // Obtain method names that have @EventHandler annotation
        List<Method> eventHandledMethods = EventReflection.getHandledMethodsFrom(WrongTestListener.class);
        List<String> methodNames = eventHandledMethods.stream().map(Method::getName).collect(Collectors.toList());

        // Assert data
        assertTrue(methodNames.contains("wrongEventMethod"));
        assertTrue(methodNames.contains("fineEventMethod"));
        assertFalse(methodNames.contains("nonEventMethod"));
    }

    @Test
    public void getEventFromMethod_shouldReturnAllMethodWithTheEventHandlerAnnotation() throws Exception {
        // Obtain event type from method.
        Method method = TestListener.class.getDeclaredMethod("updateFieldName", TestEvent.class);
        Class<?> event = EventReflection.getEventFromMethod(method);

        // Assert data
        assertEquals("TestEvent", event.getSimpleName());
    }

    @Test
    public void registerEvents_shouldRegisterMethodsWithEventHandler_whenTheyAreCorrectlyWritten() {
        // Register events
        EventManager.registerEvents(new TestListener());

        // Obtain method listeners
        List<RegisteredEventListener> registeredListeners = EventManager.getEventListenersFor(TestEvent.class);
        RegisteredEventListener eventListener = registeredListeners.get(0);

        // Assert data
        assertEquals(1, registeredListeners.size());
        assertEquals("updateFieldName", eventListener.getName());
    }

    @Test
    public void callEvent_shouldUpdateTestNameField_whenTestEventUpdateFieldNameUpdatesTheField() {
        // Register events
        EventManager.registerEvents(new TestListener());

        // Assert event call
        TestEvent testEvent = new TestEvent();
        testEvent.setTestName("Random");
        EventManager.callEvent(testEvent);
        assertEquals("Changed", testEvent.getTestName());
    }

}

class TestListener implements Listener {

    @EventHandler
    public void updateFieldName(TestEvent event) {
        event.setTestName("Changed");
        // This should be included in the event method list.
    }

    public void nonEventMethod() {
        // This should be excluded from the event method list because they don't have @EventHandler annotation.
    }

}

class WrongTestListener implements Listener {

    @EventHandler
    public void wrongEventMethod() {
        // This should be excluded from the event method list because they don't have a event type.
    }

    @EventHandler
    public void fineEventMethod(TestEvent event) {
        // This should be included in the event method list.
    }

    public void nonEventMethod() {
        // This should be excluded from the event method list because they don't have @EventHandler annotation.
    }

}

class TestEvent extends Event {

    @Getter @Setter private String testName;

}
package me.nurio.events;

import me.nurio.events.testclasses.PriorityTestListener;
import me.nurio.events.testclasses.TestEvent;
import me.nurio.events.testclasses.TestListener;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EventManagerTest {

    @Test
    public void registerEvents_shouldRegisterMethodsWithEventHandler_whenTheyAreCorrectlyWritten() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Register events
        EventManager.registerEvents(new TestListener());

        // Obtain method listeners
        Method method = EventManagement.class.getDeclaredMethod("getRegisteredEventListenersFor", Class.class);
        method.setAccessible(true);
        List<RegisteredEventListener> registeredListeners = (List<RegisteredEventListener>) method.invoke(EventManager.class, TestEvent.class);
        RegisteredEventListener eventListener = registeredListeners.get(0);

        // Assert data
        assertEquals(1, registeredListeners.size());
        assertEquals("updateFieldName", eventListener.getName());
    }

    @Test
    public void callEvent_shouldUpdateTestNameField_whenTestEventUpdateFieldNameUpdatesTheField() {
        // Register events
        EventManager.registerEvents(new TestListener());

        // Perform event call
        TestEvent testEvent = new TestEvent();
        testEvent.setTestName("Random");
        EventManager.callEvent(testEvent);

        // Assert changes
        assertEquals("Changed", testEvent.getTestName());
    }

    @Test
    public void getEventListenersOrderedByPriorityFor_shouldReturnEventsSortedByPriority_whenProvidedListenerHasDifferentPrioritizedEvents(){
        // Register listeners with prioritized events
        EventManager.registerEvents(new PriorityTestListener());

        // Get events from Listeners
        List<RegisteredEventListener> list = EventManagement.getEventListenersOrderedByPriorityFor(TestEvent.class);

        // Assert order
        assertEquals("monitorEvent", list.get(0).getName());
        assertEquals("nonTagEvent", list.get(1).getName().substring(0,11));
        assertEquals("nonTagEvent", list.get(2).getName().substring(0,11));
        assertEquals("nonTagEvent", list.get(3).getName().substring(0,11));
        assertEquals("lowEvent", list.get(4).getName());
        assertEquals("lowestEvent", list.get(5).getName());
    }

}
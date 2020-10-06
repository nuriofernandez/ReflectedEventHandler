package me.nurio.events;

import me.nurio.events.testclasses.PriorityTestListener;
import me.nurio.events.testclasses.TestEvent;
import me.nurio.events.testclasses.TestListener;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EventManagerTest {

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
        Method method = eventManagement.getClass().getDeclaredMethod("getRegisteredEventListenersFor", Class.class);
        method.setAccessible(true);
        List<RegisteredEventListener> registeredListeners = (List<RegisteredEventListener>) method.invoke(eventManagement, TestEvent.class);
        RegisteredEventListener eventListener = registeredListeners.get(0);

        // Assert data
        assertEquals(1, registeredListeners.size());
        assertEquals("me.nurio.events.testclasses.TestListener#updateFieldName", eventListener.getName());
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

    @Test
    public void getEventListenersOrderedByPriorityFor_shouldReturnEventsSortedByPriority_whenProvidedListenerHasDifferentPrioritizedEvents() throws NoSuchFieldException, IllegalAccessException {
        // Register listeners with prioritized events
        eventManager.registerEvents(new PriorityTestListener());

        // Get events from Listeners
        List<RegisteredEventListener> list = eventManagement.getEventListenersOrderedByPriorityFor(TestEvent.class);

        // Assert order
        assertEquals("me.nurio.events.testclasses.PriorityTestListener#monitorEvent", list.get(0).getName());

        // Substring name cause doesn't matters if #nonTagEvent, #nonTagEventTwo or #nonTagEventTree are fired in different order.
        assertEquals("me.nurio.events.testclasses.PriorityTestListener#nonTagEvent", list.get(1).getName().substring(0, 60));
        assertEquals("me.nurio.events.testclasses.PriorityTestListener#nonTagEvent", list.get(2).getName().substring(0, 60));
        assertEquals("me.nurio.events.testclasses.PriorityTestListener#nonTagEvent", list.get(3).getName().substring(0, 60));

        assertEquals("me.nurio.events.testclasses.PriorityTestListener#lowEvent", list.get(4).getName());
        assertEquals("me.nurio.events.testclasses.PriorityTestListener#lowestEvent", list.get(5).getName());
    }

    private EventManagement getEventManagement() throws NoSuchFieldException, IllegalAccessException {
        Field field = eventManager.getClass().getDeclaredField("eventManagement");
        field.setAccessible(true);
        return (EventManagement) field.get(eventManager);
    }

}
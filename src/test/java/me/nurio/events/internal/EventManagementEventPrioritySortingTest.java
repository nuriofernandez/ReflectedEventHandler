package me.nurio.events.internal;

import me.nurio.events.EventManager;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import me.nurio.events.handler.EventPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test will prove that EventManagement are sorting event handlers by his priority as excepted.
 */
class EventManagementEventPrioritySortingTest {

    private EventManager eventManager;
    private EventManagement eventManagement;

    @BeforeEach
    void registerEventManager() throws NoSuchFieldException, IllegalAccessException {
        eventManager = new ReflectedEventManager();
        eventManagement = getEventManagement();
    }

    @Test
    void getEventHandlersFor_shouldReturnEventsSortedByPriority_whenProvidedListenerHasDifferentPrioritizedEvents() throws NoSuchFieldException, IllegalAccessException {
        // Register listeners with prioritized events
        eventManager.registerEvents(new PriorityTestListener());

        // Get events from Listeners
        List<RegisteredEventHandler> list = eventManagement.getEventHandlerFor(TestEvent.class);

        // Assert order
        assertEquals("me.nurio.events.internal.EventManagementEventPrioritySortingTest.PriorityTestListener#monitorEvent", list.get(0).getName());

        // Substring name cause doesn't matters if #nonTagEvent, #nonTagEventTwo or #nonTagEventTree are fired in different order.
        String expectedCanonicalName = "me.nurio.events.internal.EventManagementEventPrioritySortingTest.PriorityTestListener#nonTagEvent";

        assertEquals(expectedCanonicalName, list.get(1).getName().substring(0, 97));
        assertEquals(expectedCanonicalName, list.get(2).getName().substring(0, 97));
        assertEquals(expectedCanonicalName, list.get(3).getName().substring(0, 97));

        assertEquals("me.nurio.events.internal.EventManagementEventPrioritySortingTest.PriorityTestListener#lowEvent", list.get(4).getName());
        assertEquals("me.nurio.events.internal.EventManagementEventPrioritySortingTest.PriorityTestListener#lowestEvent", list.get(5).getName());
    }

    private EventManagement getEventManagement() throws NoSuchFieldException, IllegalAccessException {
        Field field = eventManager.getClass().getDeclaredField("eventManagement");
        field.setAccessible(true);
        return (EventManagement) field.get(eventManager);
    }

    /**
     * This event listener will be used to test the sorting of different events by his priority.
     */
    public static class PriorityTestListener implements EventListener {
        @EventHandler
        public void nonTagEvent(TestEvent eve) {}

        @EventHandler(priority = EventPriority.MONITOR)
        public void monitorEvent(TestEvent eve) {}

        @EventHandler
        public void nonTagEventTwo(TestEvent eve) {}

        @EventHandler(priority = EventPriority.LOW)
        public void lowEvent(TestEvent eve) {}

        @EventHandler(priority = EventPriority.LOWEST)
        public void lowestEvent(TestEvent eve) {}

        @EventHandler
        public void nonTagEventTree(TestEvent eve) {}
    }

    /**
     * This testing event is used to create different prioritized event handlers.
     */
    public static class TestEvent extends Event {}

}
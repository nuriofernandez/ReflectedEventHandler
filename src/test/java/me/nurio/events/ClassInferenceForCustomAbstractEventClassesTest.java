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
 * Test if <b>Reflected Event Handler</b> allows class inference for custom abstract event classes
 *
 * @link https://github.com/xXNurioXx/ReflectedEventHandler/issues/22
 */
public class ClassInferenceForCustomAbstractEventClassesTest {

    private EventManager eventManager;

    @Before
    public void registerEventManager() {
        eventManager = new EventManager();
    }

    @Test
    public void test() {
        // Register event listeners
        eventManager.registerEvents(new TestListener());

        // Create an event and call it
        ChildEvent event = new ChildEvent();
        eventManager.callEvent(event);

        // Assert expected behavior
        assertEquals("Changed", event.getMessage());
    }

    /**
     * Test listener class
     */
    public static class TestListener implements EventListener {
        @EventHandler
        public void updateFieldName(ChildEvent event) {
            event.setMessage("Changed");
        }
    }

    /* Testing case dependencies has parent event class */

    private static class ParentEvent extends Event {}

    private static class ChildEvent extends ParentEvent {
        @Getter
        @Setter
        private String message;
    }

}
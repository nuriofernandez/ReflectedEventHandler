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
 * Test if <b>Reflected Event Handler</b> allows class inference for custom abstract event classes
 *
 * @link https://github.com/xXNurioXx/ReflectedEventHandler/issues/22
 */
class ClassInferenceForCustomAbstractEventClassesTest {

    private EventManager eventManager;

    @BeforeEach
    void registerEventManager() {
        eventManager = new ReflectedEventManager();
    }

    @Test
    void test() {
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

    public static class ParentEvent extends Event {}

    public static class ChildEvent extends ParentEvent {
        @Getter
        @Setter
        private String message;
    }

}
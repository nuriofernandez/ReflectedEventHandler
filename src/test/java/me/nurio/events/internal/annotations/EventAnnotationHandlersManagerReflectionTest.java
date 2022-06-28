package me.nurio.events.internal.annotations;

import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class will test the EventRefection behavior with valid and invalid event handlers.
 */
class EventAnnotationHandlersManagerReflectionTest {

    private EventAnnotationHandlersManager handlersManager;

    @BeforeEach
    void setupHandlersManager() {
        handlersManager = new EventAnnotationHandlersManager() {{
            addAnnotationHandler(new EventHandlerAnnotationEventHandler());
        }};
    }

    @Test
    void hasHandlers_shouldReturnFalse_whenProvidedMethodWasNotHandled() throws NoSuchMethodException {
        // Obtain isHandled response from nonHandledMethod
        Method nonHandledMethod = WrongTestListener.class.getMethod("nonEventMethod");
        boolean isHandled = handlersManager.hasHandlers(nonHandledMethod);

        // Assert data
        assertFalse(isHandled);
    }

    @Test
    void hasHandlers_shouldReturnTrue_whenProvidedMethodWasHandled() throws NoSuchMethodException {
        // Obtain isHandled response from handledMethod
        Method handledMethod = WrongTestListener.class.getMethod("fineEventMethod", TestEvent.class);
        boolean isHandled = handlersManager.hasHandlers(handledMethod);

        // Assert data
        assertTrue(isHandled);
    }

    @Test
    void handledMethodsFrom_shouldReturnAllMethodWithEventHandlerAnnotation() {
        // Obtain method names that have @EventHandler annotation
        List<Method> eventHandledMethods = handlersManager.handledMethodsFrom(WrongTestListener.class);
        List<String> methodNames = eventHandledMethods.stream().map(Method::getName).collect(Collectors.toList());

        // Assert data
        assertTrue(methodNames.contains("wrongEventMethod"));
        assertTrue(methodNames.contains("fineEventMethod"));
        assertFalse(methodNames.contains("nonEventMethod"));
    }

    /**
     * This event listener will be used to prove the EventReflection behavior with invalid event handlers.
     */
    public static class WrongTestListener implements EventListener {
        @EventHandler
        public void wrongEventMethod() {
            // This should be excluded from the event method list because they don't have a event type.
        }

        @EventHandler
        public void wrongEventMethodType(String string) {
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

    /**
     * This testing event is used to create a valid event handler.
     */
    public static class TestEvent extends Event {}

}
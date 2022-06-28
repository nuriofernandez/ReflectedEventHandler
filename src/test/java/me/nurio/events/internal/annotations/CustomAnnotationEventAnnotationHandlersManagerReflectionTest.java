package me.nurio.events.internal.annotations;

import lombok.Getter;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class will test the EventRefection behavior with valid and invalid event handlers.
 */
class CustomAnnotationEventAnnotationHandlersManagerReflectionTest {

    private EventAnnotationHandlersManager handlersManager;

    @BeforeEach
    void setupHandlersManager() {
        handlersManager = new EventAnnotationHandlersManager() {{
            addAnnotationHandler(new TestEventHandlerAnnotationHandler());
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
        @TestEventHandler
        public void wrongEventMethod() {
            // This should be excluded from the event method list because they don't have a event type.
        }

        @TestEventHandler
        public void wrongEventMethodType(String string) {
            // This should be excluded from the event method list because they don't have a event type.
        }

        @TestEventHandler
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

    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestEventHandler {}

    public static class TestEventHandlerAnnotationHandler implements AnnotationEventHandler {
        @Getter private final Class<? extends Annotation> annotation = TestEventHandler.class;
    }

}
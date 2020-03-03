package me.nurio.events;

import me.nurio.events.exceptions.EventHandlerNotFoundException;
import me.nurio.events.testclasses.TestEvent;
import me.nurio.events.testclasses.WrongTestListener;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class EventReflectionTest {

    @Test
    public void isHandledMethod_shouldReturnFalse_whenProvidedMethodWasNotHandled() throws NoSuchMethodException {
        // Obtain isHandled response from nonHandledMethod
        Method nonHandledMethod = WrongTestListener.class.getMethod("nonEventMethod");
        boolean isHandled = EventReflection.isHandledMethod(nonHandledMethod);

        // Assert data
        assertFalse(isHandled);
    }

    @Test
    public void isHandledMethod_shouldReturnTrue_whenProvidedMethodWasHandled() throws NoSuchMethodException {
        // Obtain isHandled response from handledMethod
        Method handledMethod = WrongTestListener.class.getMethod("fineEventMethod", TestEvent.class);
        boolean isHandled = EventReflection.isHandledMethod(handledMethod);

        // Assert data
        assertTrue(isHandled);
    }

    @Test
    public void getHandledMethodsFrom_shouldReturnAllMethodWithEventHandlerAnnotation() {
        // Obtain method names that have @EventHandler annotation
        List<Method> eventHandledMethods = EventReflection.getHandledMethodsFrom(WrongTestListener.class);
        List<String> methodNames = eventHandledMethods.stream().map(Method::getName).collect(Collectors.toList());

        // Assert data
        assertTrue(methodNames.contains("wrongEventMethod"));
        assertTrue(methodNames.contains("fineEventMethod"));
        assertFalse(methodNames.contains("nonEventMethod"));
    }

    @Test
    public void getEventFromMethod_shouldReturnEventClassFromMethod_whenProvidedMethodWasWellFormed() throws NoSuchMethodException {
        Method handledFineMethod = WrongTestListener.class.getMethod("fineEventMethod", TestEvent.class);
        Class<?> eventClass = EventReflection.getEventFromMethod(handledFineMethod);

        // Assert equals
        assertEquals(TestEvent.class, eventClass);
    }

    @Test(expected = EventHandlerNotFoundException.class)
    public void getEventFromMethod_shouldThrowEventHandlerNotFoundException_whenProvidedMethodHasNoEventParam() throws NoSuchMethodException {
        Method handledWrongMethod = WrongTestListener.class.getMethod("wrongEventMethod");

        // This call should throw an EventHandlerNotFoundException
        EventReflection.getEventFromMethod(handledWrongMethod);
    }

    @Test(expected = EventHandlerNotFoundException.class)
    public void getEventFromMethod_shouldThrowEventHandlerNotFoundException_whenProvidedMethodHasParamButWasNotEvent() throws NoSuchMethodException {
        Method handledWrongMethod = WrongTestListener.class.getMethod("wrongEventMethodType", String.class);

        // This call should throw an EventHandlerNotFoundException
        EventReflection.getEventFromMethod(handledWrongMethod);
    }

}
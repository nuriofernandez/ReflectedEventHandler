package me.nurio.events.internal.annotations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

/**
 * This class represents a event handler method.
 * In case some method got more than one handling annotation it will have one HandledMethod for each handler.
 */
@Getter
@RequiredArgsConstructor
public class HandledMethod {
    private final Method method;
    private final AnnotationEventHandler handler;
}
package me.nurio.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

@Data
@AllArgsConstructor
public class RegisteredEventListener {

    private Listener listener;
    private Method method;

}
package me.nurio.events.testclasses;

import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;

public class TestExceptionListener implements EventListener {

    @EventHandler
    public void exceptionThrower(TestEvent event) {
        throw new RuntimeException("Testing what happens when something goes wrong!");
    }

}
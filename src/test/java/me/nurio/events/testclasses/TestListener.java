package me.nurio.events.testclasses;

import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.Listener;

public class TestListener implements Listener {

    @EventHandler
    public void updateFieldName(TestEvent event) {
        event.setTestName("Changed");
        // This should be included in the event method list.
    }

    public void nonEventMethod() {
        // This should be excluded from the event method list because they don't have @EventHandler annotation.
    }

}
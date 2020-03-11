package me.nurio.events.testclasses;

import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;

public class WrongTestListener implements EventListener {

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
package me.nurio.events.testclasses;

import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventPriority;
import me.nurio.events.handler.EventListener;

public class PriorityTestListener implements EventListener {

    @EventHandler
    public void nonTagEvent(TestEvent eve) {
        // Non-tag event
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void monitorEvent(TestEvent eve) {
        // Monitor event
    }

    @EventHandler
    public void nonTagEventTwo(TestEvent eve) {
        // Non-tag event
    }

    @EventHandler(priority = EventPriority.LOW)
    public void lowEvent(TestEvent eve) {
        // Lowest event
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void lowestEvent(TestEvent eve) {
        // Lowest event
    }

    @EventHandler
    public void nonTagEventTree(TestEvent eve) {
        // Non-tag event
    }

}
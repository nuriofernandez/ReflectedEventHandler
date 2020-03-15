package me.nurio.events.testclasses;

import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import me.nurio.events.handler.EventPriority;

public class IgnoredCancelledEventTestListener implements EventListener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void eventCanceller(CancellableEvent eve) {
        eve.setName("Cancelled");
        eve.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void noIgnoredEvent(CancellableEvent eve) {
        eve.setName(eve.getName() + "ThisShouldBeSkipped");
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void ignoredEvent(CancellableEvent eve) {
        eve.setName(eve.getName() + "IgnoreCancelled");
    }

}
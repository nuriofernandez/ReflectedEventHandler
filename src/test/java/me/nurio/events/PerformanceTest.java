package me.nurio.events;

import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import me.nurio.events.testclasses.TestEvent;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class PerformanceTest implements EventListener {

    private EventManager eventManager = new EventManager();
    private static final int LOOPS = 100_000_000;

    @Test
    public void test() {
        eventManager.registerEvents(this);
        long start = System.currentTimeMillis();
        for (int i = 0; i < LOOPS; i++) {
            TestEvent testEvent = new TestEvent();
            eventManager.callEvent(testEvent);
        }
        long end = System.currentTimeMillis();

        long diff = end - start;
        System.out.printf("Total execution time: %dms%n", diff);
        System.out.printf("Per second times: %d%n", LOOPS / (diff / 1000));
    }

    @EventHandler
    public void doSomething(TestEvent event) {
        // Nothing at all
    }

}
package me.nurio.events;

import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This class is used to manually perform fast benchmarking of the call event process.
 */
@Ignore
public class PerformanceTest {

    private EventManager eventManager = new EventManager();
    private static final int LOOPS = 100_000_000;

    @Test
    public void test() {
        eventManager.registerEvents(new TestListener());
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

    /**
     * This event listener will be used to benchmark the event calling process.
     */
    private static class TestListener implements EventListener {
        @EventHandler
        public void doSomething(TestEvent event) {
            // Nothing at all
        }
    }

    /**
     * This event will be used to call the testing event handler.
     */
    private static class TestEvent extends Event {}

}
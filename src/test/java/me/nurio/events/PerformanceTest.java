package me.nurio.events;

import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import me.nurio.events.internal.ReflectedEventManager;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This class is used to manually perform fast benchmarking of the call event process.
 */
@Ignore
public class PerformanceTest {

    private EventManager eventManager = new ReflectedEventManager();
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
        long eventsPerSecond = (long) (LOOPS / (diff / 1000D));
        System.out.printf("Total execution time: %dms%n", diff);
        System.out.printf("Events per second: %d%n", eventsPerSecond);
    }

    /**
     * This event listener will be used to benchmark the event calling process.
     */
    public static class TestListener implements EventListener {
        @EventHandler
        public void doSomething(TestEvent event) {
            // Nothing at all
        }
    }

    /**
     * This event will be used to call the testing event handler.
     */
    public static class TestEvent extends Event {}

}
package me.nurio.events;

import me.nurio.events.testclasses.CancellableEvent;
import me.nurio.events.testclasses.ConnectTestEvent;
import me.nurio.events.testclasses.ConnectionTestListener;
import me.nurio.events.testclasses.DisconnectTestEvent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventManagerIntegrationTest {

    private EventManager eventManager;

    @Before
    public void registerEventManager() {
        eventManager = new EventManager();
    }

    @Test
    public void callEvent_shouldCallAllEventHandlersFromAllRegisteredListeners() {
        eventManager.registerEvents(new ConnectionTestListener("Pedro"));

        // Testing ConnectionTestListener's randomConnectEventListenerMethodName
        {
            ConnectTestEvent connectEvent = new ConnectTestEvent("Carlos");
            eventManager.callEvent(connectEvent);
            assertEquals("Lagarto", connectEvent.getWhoIsConnecting());
        }

        // Testing ConnectionTestListener's randomConnectEventListenerMethodName
        {
            ConnectTestEvent connectEvent = new ConnectTestEvent("Pedro");
            eventManager.callEvent(connectEvent);
            assertEquals("Pedro", connectEvent.getWhoIsConnecting());
        }

        // Testing ConnectionTestListener's randomDisconnectEventListenerMethodName
        {
            DisconnectTestEvent disconnectEvent = new DisconnectTestEvent("Carlitos");
            eventManager.callEvent(disconnectEvent);
            assertEquals("CarlitosChanged", disconnectEvent.getWhoIsDisconnecting());
        }
    }

    @Test
    public void callEvent_shouldRecognizeCancelledEvent_whenCalledEventWasCancelled() {
        eventManager.registerEvents(new ConnectionTestListener("Pedro"));

        CancellableEvent event = new CancellableEvent("Jolin");
        eventManager.callEvent(event);
        assertTrue(event.isCancelled());
    }

    @Test
    public void callEvent_shouldRecognizeNotCancelledEvent_whenCalledEventWasNotCancelled() {
        eventManager.registerEvents(new ConnectionTestListener("Pedro"));

        CancellableEvent event = new CancellableEvent("Nooog");
        eventManager.callEvent(event);
        assertFalse(event.isCancelled());
    }

}




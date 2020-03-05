package me.nurio.events;

import me.nurio.events.testclasses.CancellableEvent;
import me.nurio.events.testclasses.ConnectTestEvent;
import me.nurio.events.testclasses.ConnectionTestListener;
import me.nurio.events.testclasses.DisconnectTestEvent;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventManagerIntegrationTest {

    @Test
    public void callEvent_shouldCallAllEventHandlersFromAllRegisteredListeners() {
        EventManager.registerEvents(new ConnectionTestListener("Pedro"));

        // Testing ConnectionTestListener's randomConnectEventListenerMethodName
        {
            ConnectTestEvent connectEvent = new ConnectTestEvent("Carlos");
            EventManager.callEvent(connectEvent);
            assertEquals("Lagarto", connectEvent.getWhoIsConnecting());
        }

        // Testing ConnectionTestListener's randomConnectEventListenerMethodName
        {
            ConnectTestEvent connectEvent = new ConnectTestEvent("Pedro");
            EventManager.callEvent(connectEvent);
            assertEquals("Pedro", connectEvent.getWhoIsConnecting());
        }

        // Testing ConnectionTestListener's randomDisconnectEventListenerMethodName
        {
            DisconnectTestEvent disconnectEvent = new DisconnectTestEvent("Carlitos");
            EventManager.callEvent(disconnectEvent);
            assertEquals("CarlitosChanged", disconnectEvent.getWhoIsDisconnecting());
        }
    }

    @Test
    public void callEvent_shouldRecognizeCancelledEvent_whenCalledEventWasCancelled(){
        EventManager.registerEvents(new ConnectionTestListener("Pedro"));

        CancellableEvent event = new CancellableEvent("Jolin");
        EventManager.callEvent(event);
        assertTrue(event.isCancelled());
    }

    @Test
    public void callEvent_shouldRecognizeNotCancelledEvent_whenCalledEventWasNotCancelled(){
        EventManager.registerEvents(new ConnectionTestListener("Pedro"));

        CancellableEvent event = new CancellableEvent("Nooog");
        EventManager.callEvent(event);
        assertFalse(event.isCancelled());
    }

}




package me.nurio.events;

import me.nurio.events.testclasses.ConnectTestEvent;
import me.nurio.events.testclasses.ConnectionTestListener;
import me.nurio.events.testclasses.DisconnectTestEvent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

}




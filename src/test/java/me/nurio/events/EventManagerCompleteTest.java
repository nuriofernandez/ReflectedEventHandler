package me.nurio.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.nurio.events.Event;
import me.nurio.events.EventHandler;
import me.nurio.events.EventManager;
import me.nurio.events.Listener;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventManagerCompleteTest {

    @Test
    public void eventTest() {
        EventManager.registerEvents(new ConnectionTestListener("Pedro"));
        {
            ConnectEvent connectEvent = new ConnectEvent("Carlos");
            EventManager.callEvent(connectEvent);
            assertEquals("Lagarto", connectEvent.getWhoIsConnecting());
        }
        {
            ConnectEvent connectEvent = new ConnectEvent("Pedro");
            EventManager.callEvent(connectEvent);
            assertEquals("Pedro", connectEvent.getWhoIsConnecting());
        }
        {
            DisconnectEvent disconnectEvent = new DisconnectEvent("Carlitos");
            EventManager.callEvent(disconnectEvent);
            assertEquals("CarlitosChanged", disconnectEvent.getWhoIsDisconnecting());
        }
    }

}

class ConnectionTestListener implements Listener {

    private String correctName;

    public ConnectionTestListener(String correctName) {
        this.correctName = correctName;
    }

    @EventHandler
    public void randomConnectEventListenerMethodName(ConnectEvent eve) {
        if (eve.getWhoIsConnecting().equalsIgnoreCase(correctName)) return;
        eve.setWhoIsConnecting("Lagarto");
    }

    @EventHandler
    public void randomDisconnectEventListenerMethodName(DisconnectEvent eve) {
        if (eve.getWhoIsDisconnecting().equalsIgnoreCase(correctName)) return;
        eve.setWhoIsDisconnecting(eve.getWhoIsDisconnecting()+"Changed");
    }

}

@Data
@AllArgsConstructor
class ConnectEvent extends Event {
    private String whoIsConnecting;
}

@Data
@AllArgsConstructor
class DisconnectEvent extends Event {
    private String whoIsDisconnecting;
}
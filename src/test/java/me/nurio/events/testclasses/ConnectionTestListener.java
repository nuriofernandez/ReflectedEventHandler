package me.nurio.events.testclasses;

import me.nurio.events.EventHandler;
import me.nurio.events.Listener;

public class ConnectionTestListener implements Listener {

    private String correctName;

    public ConnectionTestListener(String correctName) {
        this.correctName = correctName;
    }

    @EventHandler
    public void randomConnectEventListenerMethodName(ConnectTestEvent eve) {
        if (eve.getWhoIsConnecting().equalsIgnoreCase(correctName)) return;
        eve.setWhoIsConnecting("Lagarto");
    }

    @EventHandler
    public void randomDisconnectEventListenerMethodName(DisconnectTestEvent eve) {
        if (eve.getWhoIsDisconnecting().equalsIgnoreCase(correctName)) return;
        eve.setWhoIsDisconnecting(eve.getWhoIsDisconnecting()+"Changed");
    }

    @EventHandler
    public void cancellableEvent(CancellableEvent event){
        if(event.getName().equals("Jolin")) {
            event.setCancelled(true);
        }
    }

}
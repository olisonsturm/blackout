package me.olisonsturm.blackout.view.Infos;

public class ConnectionInfo {
    private static boolean isConnected;

    public ConnectionInfo(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public static boolean isConnected() {
        return isConnected;
    }

    public static void setConnected(boolean connected) {
        isConnected = connected;
    }
}

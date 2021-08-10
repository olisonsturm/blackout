package me.olisonsturm.blackout.view.activitys;

public class DeviceInfo {
    String name;
    String address;

    public DeviceInfo(String name, String macAddress) {
        this.name = name;
        this.address = macAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String macAdress) {
        this.address = macAdress;
    }
}

package me.olisonsturm.blackout.util;

import android.app.Application;

import java.util.List;

import me.olisonsturm.blackout.model.Player;

public class GlobalData extends Application {

    private List<Player> globalPlayerArrayList;

    public List<Player> getGlobalPlayerArrayList() {
        return globalPlayerArrayList;
    }

    public void setGlobalPlayerArrayList(List<Player> playerArrayList) {
        this.globalPlayerArrayList = playerArrayList;
    }

}

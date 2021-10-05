package me.olisonsturm.blackout.util;

import android.app.Application;

import java.util.List;

import me.olisonsturm.blackout.model.playerexampler;

public class GlobalData extends Application {

    private List<playerexampler> globalPlayerArrayList;

    public List<playerexampler> getGlobalPlayerArrayList() {
        return globalPlayerArrayList;
    }

    public void setGlobalPlayerArrayList(List<playerexampler> playerArrayList) {
        this.globalPlayerArrayList = playerArrayList;
    }

}

package me.olisonsturm.blackout.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class PlayerViewModel extends AndroidViewModel {
    private PlayerRepository repository;
    private LiveData<List<Player>> allPlayers;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        repository = new PlayerRepository(application);
        allPlayers = repository.getAllPlayer();
    }

    public void insert(Player player){
        repository.insert(player);
    }

    public void update(Player player){
        repository.update(player);
    }

    public void delete(Player player){
        repository.delete(player);
    }

    public void deleteAllPlayers(){
        repository.deleteAllPlayers();
    }

    public LiveData<List<Player>> getAllPlayers(){
        return allPlayers;
    }

}

package me.olisonsturm.blackout.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PlayerRepository {
    private PlayerDao playerDao;
    private LiveData<List<Player>> allPlayer;

    public PlayerRepository(Application application){
        PlayerDatabase database = PlayerDatabase.getInstance(application);
        playerDao = database.playerDao();
        allPlayer = playerDao.getAllPlayers();
    }
    public void insert(Player player){
        new InsertPlayerAsyncTask(playerDao).execute(player);
    }

    public void update(Player player){
        new UpdatePlayerAsyncTask(playerDao).execute(player);
    }
    public void delete(Player player){
        new DeletePlayerAsyncTask(playerDao).execute(player);
    }
    public void deleteAllPlayers(){
        new DeleteAllPlayersAsyncTask(playerDao).execute();
    }
    public LiveData<List<Player>> getAllPlayer(){
        return allPlayer;
    }


    private static class InsertPlayerAsyncTask extends AsyncTask<Player, Void, Void> {
        private PlayerDao playerDao;

        private InsertPlayerAsyncTask(PlayerDao playerDao){
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(Player... players){
            playerDao.insert(players[0]);
            return null;
        }
    }

    private static class UpdatePlayerAsyncTask extends AsyncTask<Player, Void, Void> {
        private PlayerDao playerDao;

        private UpdatePlayerAsyncTask(PlayerDao playerDao){
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(Player... players){
            playerDao.update(players[0]);
            return null;
        }
    }

    private static class DeletePlayerAsyncTask extends AsyncTask<Player, Void, Void> {
        private PlayerDao playerDao;

        private DeletePlayerAsyncTask(PlayerDao playerDao){
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(Player... players){
            playerDao.delete(players[0]);
            return null;
        }
    }

    private static class DeleteAllPlayersAsyncTask extends AsyncTask<Void, Void, Void> {
        private PlayerDao playerDao;

        private DeleteAllPlayersAsyncTask(PlayerDao playerDao){
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            playerDao.deleteAllPlayers();
            return null;
        }
    }






}

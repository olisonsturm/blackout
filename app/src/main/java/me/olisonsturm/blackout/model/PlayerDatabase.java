package me.olisonsturm.blackout.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

@Database(entities = {Player.class}, version = 1)
public abstract class PlayerDatabase extends RoomDatabase {

    private static PlayerDatabase instance;

    public abstract PlayerDao playerDao();

    public static synchronized PlayerDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PlayerDatabase.class, "player_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private PlayerDao playerDao;
        private PopulateDbAsyncTask(PlayerDatabase db){
            playerDao = db.playerDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            playerDao.insert(new Player("Niki", "Niklas Deckert", 0,1));
            playerDao.insert(new Player("Oli", "Olison Sturm", 0,2));
            playerDao.insert(new Player("Meggi", "Magdalena Müller", 1,3));
            return null;
        }
    }

}

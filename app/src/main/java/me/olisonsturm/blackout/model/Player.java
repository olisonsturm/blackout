package me.olisonsturm.blackout.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "player_table")
public class Player {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int priority;

    private String nickName;
    private String realName;
    private String firstLetter;
    private int gender;

    private int drinkAmount;
    private int steps;

    private int drinkAmountTotal;
    private int gameCount;
    private int score;


    public Player(String nickName, String realName, int gender, int priority) {
        this.nickName = nickName;
        this.realName = realName;
        this.firstLetter = String.valueOf(nickName.charAt(0) + nickName.charAt(1));
        this.gender = gender;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDrinkAmount(int drinkAmount) {
        this.drinkAmount = drinkAmount;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setDrinkAmountTotal(int drinkAmountTotal) {
        this.drinkAmountTotal = drinkAmountTotal;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public String getNickName() {
        return nickName;
    }

    public String getRealName() {
        return realName;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public int getGender() {
        return gender;
    }

    public int getDrinkAmount() {
        return drinkAmount;
    }

    public int getSteps() {
        return steps;
    }

    public int getDrinkAmountTotal() {
        return drinkAmountTotal;
    }

    public int getGameCount() {
        return gameCount;
    }

    public int getScore() {
        return score;
    }
}

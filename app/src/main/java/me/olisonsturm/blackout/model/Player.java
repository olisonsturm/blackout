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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getDrinkAmount() {
        return drinkAmount;
    }

    public void setDrinkAmount(int drinkAmount) {
        this.drinkAmount = drinkAmount;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getDrinkAmountTotal() {
        return drinkAmountTotal;
    }

    public void setDrinkAmountTotal(int drinkAmountTotal) {
        this.drinkAmountTotal = drinkAmountTotal;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

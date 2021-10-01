package me.olisonsturm.blackout.model;

public class Player {
    int id;

    String nickName;
    String realName;
    char firstLetter;
    int gender;

    int drinkAmount;
    int steps;

    int drinkAmountTotal;
    int gameCount;
    int score;

    public Player(String nickName, String realName, int gender) {
        this.nickName = nickName;
        this.realName = realName;
        firstLetter = nickName.charAt(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        firstLetter = nickName.charAt(0);
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public char getFirstLetter() {
        return firstLetter;
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

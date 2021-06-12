package model;

import model.prizes.Prize;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private int score;
    private int lives;
    List<Prize> prizes;

    public Player(String name) {
        this.name = name;
        this.lives = 3;
        prizes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public List<Prize> getPrizes() {
        return prizes;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void applyScore(int score){
        this.score += score;
    }

    public void loseLife(){
        this.lives -= 1;
    }

    /// name, lives, score
    public String json(){
        return name + " " + lives + " " + score;
    }
}

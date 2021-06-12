package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ball extends Rectangle implements Cloneable {
    private static final List<Ball> balls = new ArrayList<>();

    private double xVelocity;
    private int yVelocity;
//    private int speed;
    private Color color;
    private boolean fireMode;
    private boolean fastMode;
    private boolean slowMode;

    /// -1 : left, 1 : right
    public Ball(int posX, int posY, int ballWidth, int ballHeight, double xVelocity, int yVelocity){
        super(posX, posY, ballWidth, ballHeight);
        color = Color.YELLOW;
//        speed = speed;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        balls.add(this);
    }

    public static List<Ball> getBalls() {
        return balls;
    }

    public boolean isFireMode() {
        return fireMode;
    }

    public boolean isFastMode() {
        return fastMode;
    }

    public boolean isSlowMode() {
        return slowMode;
    }

//    public void setSpeed(int speed) {
//        this.speed = speed;
//    }

    public void setFireMode(boolean fireMode) {
        this.fireMode = fireMode;
        if (fireMode){
            this.color = Color.RED;
        } else {
            this.color = Color.YELLOW;
        }
    }

    public void setFastMode(boolean fastMode) {
        if (fastMode && !this.fastMode){
            xVelocity *= 1.5;
            yVelocity *= 1.5;
            this.fastMode = true;
        } else if (!fastMode){
            xVelocity /= 1.5;
            yVelocity /= 1.5;
            this.fastMode = false;
        }
    }

    public void setSlowMode(boolean slowMode) {
        if (slowMode && !this.slowMode){
            xVelocity /= 2;
            yVelocity /= 2;
            this.slowMode = true;
        } else if (!slowMode){
            xVelocity *= 2;
            yVelocity *= 2;
            this.slowMode = false;
        }
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public int getYVelocity() {
        return yVelocity;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setXDirection(double xDirection){
        xVelocity = xDirection;
    }

    public void setYDirection(int yDirection){
        yVelocity = yDirection;
    }

    public void move(){
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }

    /// x, y, xVelocity, yVelocity, fireMode, fastMode, slowMode (true = 1, false = 0)
    public String json(){
        String data = this.x + " " + this.y + " " + this.xVelocity + " " + this.yVelocity + " ";
        if (fireMode) data += 1 + " ";
        else data += 0 + " ";
        if (fastMode) data += 1 + " ";
        else data += 0 + " ";
        if (slowMode) data += 1;
        else data += 0;
        return data;
    }


}

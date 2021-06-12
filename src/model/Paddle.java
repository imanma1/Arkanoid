package model;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle {
    private int xVelocity;
    private int speed;
    private boolean dizzyMode;
    private boolean bigSizeMode;
    private boolean smallSizeMode;

    public Paddle(int posX, int posY, int paddleLength, int paddleHeight) {
        super(posX, posY, paddleLength, paddleHeight);
        speed = 5;
    }

    public boolean isDizzyMode() {
        return dizzyMode;
    }

    public boolean isBigSizeMode() {
        return bigSizeMode;
    }

    public boolean isSmallSizeMode() {
        return smallSizeMode;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDizzyMode(boolean dizzyMode) {
        this.dizzyMode = dizzyMode;
    }

    public void setBigSizeMode(boolean bigSizeMode) {
        this.bigSizeMode = bigSizeMode;
        if (bigSizeMode){
            this.width = 120;
        } else {
            this.width = 80;
        }
    }

    public void setSmallSizeMode(boolean smallSizeMode) {
        this.smallSizeMode = smallSizeMode;
        if (smallSizeMode){
            this.width = 50;
        } else {
            this.width = 80;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if (!dizzyMode){
                setXDirection(-speed);
            } else {
                setXDirection(speed);
            }
            move();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (!dizzyMode){
                setXDirection(speed);
            } else {
                setXDirection(-speed);
            }
            move();
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            setXDirection(0);
            move();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            setXDirection(0);
            move();
        }
    }

    public void setXDirection(int xDirection){
        xVelocity = xDirection;
    }

    public void move(){
        x += xVelocity;
    }

    public void draw(Graphics g){
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width, height);
    }

    /// x, y, dizzyMode, bigMode, smallMode (true = 1, false = 0)
    public String json(){
        String data = this.x + " " + this.y + " ";
        if (dizzyMode) data += 1 + " ";
        else data += 0 + " ";
        if (bigSizeMode) data += 1 + " ";
        else data += 0 + " ";
        if (smallSizeMode) data += 1;
        else data += 0;
        return data;
    }
}

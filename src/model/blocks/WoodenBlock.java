package model.blocks;

import model.Ball;

import java.awt.*;
import java.util.List;

public class WoodenBlock extends Block{
    private int lives;

    public WoodenBlock(int posX, int posY, int blockLength, int blockHeight) {
        super(posX, posY, blockLength, blockHeight);
        this.lives = 2;
    }

    public void loseLife(){
        this.lives -= 1;
    }

    @Override
    public void collision(List<Block> blocks, Ball ball) {
        if (!ball.isFireMode()){
            if (!ball.isFireMode()){
                if (Math.abs(ball.y - this.y) == 19){
                    ball.setYDirection(-ball.getYVelocity());
                } else {
                    ball.setXDirection(-ball.getXVelocity());
                }
            }
        }
        loseLife();
        if (ball.isFireMode()) loseLife();
        if (this.lives <= 0){
            blocks.remove(this);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect(x, y, width, height);
    }

    @Override
    public String json() {
        String data = super.json();
        data += "4";
        return data;
    }
}

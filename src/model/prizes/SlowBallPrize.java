package model.prizes;

import model.Ball;
import model.blocks.Block;
import panel.GamePanel;

import java.awt.*;

public class SlowBallPrize extends Prize{
    public SlowBallPrize(int posX, int posY, int prizeWidth, int prizeHeight) {
        super(posX, posY, prizeWidth, prizeHeight);
        this.color = Color.CYAN;
    }

    @Override
    public void usePrize(GamePanel gamePanel) {
        for (Ball ball : gamePanel.getBalls()) {
            ball.setSlowMode(true);
        }
    }

    @Override
    public void draw(Graphics g) {
        if (this.isDropped()){
            g.setColor(color);
            g.fillOval(x, y, width, height);
        }
    }
}

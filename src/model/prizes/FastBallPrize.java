package model.prizes;

import model.Ball;
import model.blocks.Block;
import panel.GamePanel;

import java.awt.*;

public class FastBallPrize extends Prize {
    public FastBallPrize(int posX, int posY, int prizeWidth, int prizeHeight) {
        super(posX, posY, prizeWidth, prizeHeight);
        this.color = Color.ORANGE;
    }

    @Override
    public void usePrize(GamePanel gamePanel) {
        for (Ball ball : gamePanel.getBalls()) {
            ball.setFastMode(true);
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

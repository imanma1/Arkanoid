package model.prizes;

import model.Ball;
import model.blocks.Block;
import panel.GamePanel;

import java.awt.*;

public class FireBallPrize extends Prize {

    public FireBallPrize(int posX, int posY, int prizeWidth, int prizeHeight) {
        super(posX, posY, prizeWidth, prizeHeight);
        this.color = Color.RED;
    }

    @Override
    public void usePrize(GamePanel gamePanel) {
        for (Ball ball : gamePanel.getBalls()) {
            ball.setFireMode(true);
        }
    }

    @Override
    public void draw(Graphics g) {
        if (this.isDropped()){
            g.setColor(Color.RED);
            g.fillOval(x, y, width, height);
        }
    }
}

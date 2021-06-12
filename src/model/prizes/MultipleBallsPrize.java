package model.prizes;

import model.Ball;
import model.blocks.Block;
import panel.GamePanel;

import java.awt.*;

public class MultipleBallsPrize extends Prize{
    public MultipleBallsPrize(int posX, int posY, int prizeWidth, int prizeHeight) {
        super(posX, posY, prizeWidth, prizeHeight);
        this.color = Color.GREEN;
    }

    @Override
    public void usePrize(GamePanel gamePanel) {
        gamePanel.getBalls().add(new Ball(293, 575, 15, 15, -4, -4));
        gamePanel.getBalls().add(new Ball(293, 575, 15, 15, 4, -4));
    }

    @Override
    public void draw(Graphics g) {
        if (this.isDropped()){
            g.setColor(color);
            g.fillOval(x, y, width, height);
        }
    }
}

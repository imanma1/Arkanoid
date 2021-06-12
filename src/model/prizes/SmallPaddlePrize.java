package model.prizes;

import panel.GamePanel;

import java.awt.*;

public class SmallPaddlePrize extends Prize{
    public SmallPaddlePrize(int posX, int posY, int prizeWidth, int prizeHeight) {
        super(posX, posY, prizeWidth, prizeHeight);
        this.color = Color.BLUE;
    }

    @Override
    public void usePrize(GamePanel gamePanel) {
        gamePanel.getPaddle().setSmallSizeMode(true);
    }

    @Override
    public void draw(Graphics g) {
        if (this.isDropped()){
            g.setColor(color);
            g.fillOval(x, y, width, height);
        }
    }
}

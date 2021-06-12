package model.prizes;

import model.blocks.Block;
import panel.GamePanel;

import java.awt.*;

public class BigPaddlePrize extends Prize {
    public BigPaddlePrize(int posX, int posY, int prizeWidth, int prizeHeight) {
        super(posX, posY, prizeWidth, prizeHeight);
        this.color = Color.MAGENTA;
    }

    @Override
    public void usePrize(GamePanel gamePanel) {
        gamePanel.getPaddle().setBigSizeMode(true);
    }

    @Override
    public void draw(Graphics g) {
        if (this.isDropped()){
            g.setColor(color);
            g.fillOval(x, y, width, height);
        }
    }
}

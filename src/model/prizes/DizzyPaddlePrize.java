package model.prizes;

import model.blocks.Block;
import panel.GamePanel;

import java.awt.*;

public class DizzyPaddlePrize extends Prize {
    public DizzyPaddlePrize(int posX, int posY, int prizeWidth, int prizeHeight) {
        super(posX, posY, prizeWidth, prizeHeight);
        this.color = Color.GRAY;
    }

    @Override
    public void usePrize(GamePanel gamePanel) {
        gamePanel.getPaddle().setDizzyMode(true);
    }

    @Override
    public void draw(Graphics g) {
        if (this.isDropped()){
            g.setColor(color);
            g.fillOval(x, y, width, height);
        }
    }
}

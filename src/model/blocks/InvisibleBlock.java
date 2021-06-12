package model.blocks;

import java.awt.*;

public class InvisibleBlock extends Block{

    public InvisibleBlock(int posX, int posY, int blockLength, int blockHeight) {
        super(posX, posY, blockLength, blockHeight);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
    }

    @Override
    public String json() {
        String data = super.json();
        data += "1";
        return data;
    }
}

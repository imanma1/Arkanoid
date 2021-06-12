package model.blocks;

import java.awt.*;

public class GlassBlock extends Block {

    public GlassBlock(int posX, int posY, int blockLength, int blockHeight) {
        super(posX, posY, blockLength, blockHeight);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    @Override
    public String json() {
        String data = super.json();
        data += "0";
        return data;
    }
}

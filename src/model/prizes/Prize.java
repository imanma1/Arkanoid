package model.prizes;

import model.blocks.Block;
import panel.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Prize extends Rectangle {
    private static final List<Prize> prizes = new ArrayList<>();

    private final int yVelocity;
    private boolean dropped;
    protected Color color;

    public Prize(int posX, int posY, int prizeWidth, int prizeHeight){
        super(posX, posY, prizeWidth, prizeHeight);
        yVelocity = 2;
        prizes.add(this);
    }

    public static List<Prize> getPrizes() {
        return prizes;
    }

    public boolean isDropped() {
        return dropped;
    }

    public void setDropped(boolean dropped) {
        this.dropped = dropped;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void move(){
        y += yVelocity;
    }

    public void usePrize(GamePanel gamePanel){ }

    public void draw(Graphics g){ }
}

package model.blocks;

import model.Ball;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WinkerBlock extends Block{
    private boolean off;
    static List<WinkerBlock> offWinkerBlocks = new ArrayList<>();
    static List<WinkerBlock> onWinkerBlocks = new ArrayList<>();

    public WinkerBlock(int posX, int posY, int blockLength, int blockHeight) {
        super(posX, posY, blockLength, blockHeight);
        onWinkerBlocks.add(this);
    }

    public boolean isOff() {
        return off;
    }

    public void setOff(boolean off) {
        this.off = off;
    }

    public static void offWinkerBlock(){
        for (int i = 0; i < 4; i++) {
            if (onWinkerBlocks.size()>0){
                int rand = new Random().nextInt(onWinkerBlocks.size());
                onWinkerBlocks.get(rand).setOff(true);
                offWinkerBlocks.add(onWinkerBlocks.get(rand));
                onWinkerBlocks.remove(rand);
            }
        }
    }

    public static void onWinkerBlock(){
        for (int i = 0; i < 4; i++) {
            if (offWinkerBlocks.size()>0){
                int rand = new Random().nextInt(offWinkerBlocks.size());
                offWinkerBlocks.get(rand).setOff(false);
                onWinkerBlocks.add(offWinkerBlocks.get(rand));
                offWinkerBlocks.remove(rand);
            }
        }
    }

    @Override
    public void collision(List<Block> blocks, Ball ball) {
        if (!isOff()){
            if (!ball.isFireMode()){
                if (Math.abs(ball.y - this.y) == 19){
                    ball.setYDirection(-ball.getYVelocity());
                } else {
                    ball.setXDirection(-ball.getXVelocity());
                }
            }
            blocks.remove(this);
        }
    }

    @Override
    public void draw(Graphics g) {
        if (this.off){
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect(x, y, width, height);
    }

    @Override
    public String json() {
        String data = super.json();
        data += "3";
        return data;
    }
}

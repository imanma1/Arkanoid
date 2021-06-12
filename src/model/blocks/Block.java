package model.blocks;

import model.Ball;
import model.prizes.Prize;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Block extends Rectangle{

    public Block(int posX, int posY, int blockLength, int blockHeight){
        super(posX, posY, blockLength, blockHeight);
    }

    public void collision(List<Block> blocks, Ball ball){
        blocks.remove(this);
        if (!ball.isFireMode()){
            if (Math.abs(ball.y - this.y) == 19){
                ball.setYDirection(-ball.getYVelocity());
            } else {
                ball.setXDirection(-ball.getXVelocity());
            }
        }
    }

    public static void addNewLine(List<List<Block>> blocks){
        shiftAllLines(blocks);
        blocks.add(0, new ArrayList<>());
        int x = -25;
        int y = 20;
        for (int i = 0; i < 9; i++) {
            x += 60;
            int rand = new Random().nextInt(5);
            Block block = null;
            if (rand == 0){
                block = new GlassBlock(x, y, 50, 20);
            }
            if (rand == 1){
                block = new InvisibleBlock(x, y, 50, 20);
            }
            if (rand == 2){
                block = new WoodenBlock(x, y, 50, 20);
            }
            if (rand == 3){
                block = new WinkerBlock(x, y, 50, 20);
            }
            if (rand == 4){
                block = new PrizeBlock(x, y, 50, 20);
            }
            blocks.get(0).add(block);
        }
    }

    public static void shiftAllLines(List<List<Block>> blocks){
        for (List<Block> blocks1 : blocks) {
            for (Block block : blocks1) {
                block.y += 40;
                if (block instanceof PrizeBlock){
                    ((PrizeBlock) block).getPrize().y += 40;
                }
            }
        }
    }

    public void draw(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    public static void drawAll(List<List<Block>> blocks, Graphics g){
        for (List<Block> blocks1 : blocks) {
            for (Block block : blocks1) {
                block.draw(g);
            }
        }
    }

    /// x, y, type
    public String json(){
        String data = "";
        data += this.x + " " + this.y + " ";
        return data;
    }

}

package model.blocks;

import model.Ball;
import model.prizes.*;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class PrizeBlock extends Block{
    private Prize prize;

    public PrizeBlock(int posX, int posY, int blockLength, int blockHeight) {
        super(posX, posY, blockLength, blockHeight);
        int rand = new Random().nextInt(8);
        if (rand == 0) prize = new BigPaddlePrize(posX, posY, 8, 8);
        if (rand == 1) prize = new DizzyPaddlePrize(posX, posY, 8, 8);
        if (rand == 2) prize = new FastBallPrize(posX, posY, 8, 8);
        if (rand == 3) prize = new FireBallPrize(posX, posY, 8, 8);
        if (rand == 4) prize = new SlowBallPrize(posX, posY, 8, 8);
        if (rand == 5) prize = new SmallPaddlePrize(posX, posY, 8, 8);
        if (rand == 6) prize = new MultipleBallsPrize(posX, posY, 8, 8);
        if (rand == 7) prize = randomPrize(posX, posY);
    }

    public Prize getPrize() {
        return prize;
    }

    private Prize randomPrize(int posX, int posY){
        int rand = new Random().nextInt(7);
        Prize prize1 = null;
        if (rand == 0) prize1 = new BigPaddlePrize(posX, posY, 8, 8);
        if (rand == 1) prize1 = new DizzyPaddlePrize(posX, posY, 8, 8);
        if (rand == 2) prize1 = new FastBallPrize(posX, posY, 8, 8);
        if (rand == 3) prize1 = new FireBallPrize(posX, posY, 8, 8);
        if (rand == 4) prize1 = new SlowBallPrize(posX, posY, 8, 8);
        if (rand == 5) prize1 = new SmallPaddlePrize(posX, posY, 8, 8);
        if (rand == 6) prize1 = new MultipleBallsPrize(posX, posY, 8, 8);
        prize1.setColor(Color.WHITE);
        return prize1;
    }

    @Override
    public void collision(List<Block> blocks, Ball ball) {
        super.collision(blocks, ball);
        prize.setDropped(true);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    }

    @Override
    public String json() {
        String data = super.json();
        data += "2";
        return data;
    }
}

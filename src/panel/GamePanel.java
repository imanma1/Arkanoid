package panel;

import logic.ModelSaver;
import model.Ball;
import model.Paddle;
import model.Player;
import model.blocks.Block;
import model.blocks.WinkerBlock;
import model.prizes.Prize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable{

    static final int GAME_WIDTH = 600;
    static final int GAME_HEIGHT = 600;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 15;
    static final int PADDLE_HEIGHT = 10;
    static final int PADDLE_WIDTH = 80;
    static final int BLOCK_HEIGHT = 20;
    static final int BLOCK_WIDTH = 50;
    private Thread gameThread;
    private Image image;
    private Graphics graphics;
    private Player player;
    private Paddle paddle;
//    private Ball ball;
    private ScorePanel scorePanel;
    private List<Ball> balls = new ArrayList<>();
    private List<List<Block>> blocks;
    private boolean isStarted;

    public GamePanel(String name){
        player = new Player(name);
        newBall();
        newPaddle();
        newBlocks();
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        scorePanel = new ScorePanel(player);
        gameThread = new Thread(this);
        gameThread.start();
    }



    public List<List<Block>> getBlocks() {
        return blocks;
    }

    public Player getPlayer() {
        return player;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public GamePanel getGamePanel(){
        return this;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public void newBall(){
        balls.add(new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2) , GAME_HEIGHT-BALL_DIAMETER-PADDLE_HEIGHT, BALL_DIAMETER, BALL_DIAMETER, -4, 4));
    }

    public void newPaddle(){
        paddle = new Paddle((GAME_WIDTH/2)-(PADDLE_WIDTH/2) , GAME_HEIGHT-PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    public void newBlocks(){
        blocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Block.addNewLine(blocks);
        }
    }

    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g){
        paddle.draw(g);
        Block.drawAll(blocks, g);
        for (Ball ball : balls) {
            ball.draw(g);
        }
        for (Prize prize : Prize.getPrizes()) {
            if (prize.isDropped()) prize.draw(g);
        }
        scorePanel.draw(g);
    }

    public void move(){
        paddle.move();
        for (Ball ball : balls) {
            ball.move();
        }
        for (Prize prize : Prize.getPrizes()) {
            if (prize.isDropped()) prize.move();
        }
    }

    public void checkCollision(){
        /// stops paddle at edges
        paddleHitsTheEdges();

        /// stops the ball at edges
        ballHitsTheEdges();

        /// ball hits the paddle
        ballHitsThePaddle();

        /// ball hits the block
        ballHitsABlock();

        /// lose a life and creates new ball and paddle
        ballHitsTheFloor();

        ///use prize when paddle hits the prize
        paddleHitsThePrize();

        /// check if any block hits the floor and restart the game
        blockHitsTheFloor();
    }

    public void paddleHitsTheEdges(){
        if (paddle.x <= 0)
            paddle.x = 0;
        if (paddle.x >= GAME_WIDTH-paddle.width)
            paddle.x = GAME_WIDTH-paddle.width;
    }

    public void ballHitsTheEdges(){
        for (Ball ball : balls) {
            if (ball.x <= 0 || ball.x >= GAME_WIDTH - BALL_DIAMETER){
                ball.setXDirection(-ball.getXVelocity());
            }
            if (ball.y <= 0){
                ball.setYDirection(-ball.getYVelocity());
            }
        }
    }

    public void ballHitsThePaddle(){
        for (Ball ball : balls) {
            if (ball.intersects(paddle)){
                ball.setYDirection(-ball.getYVelocity());

                if(ball.getCenterX() > paddle.getCenterX()) {
                    if (ball.getXVelocity() > 0)
                        ball.setXDirection((0.005 * Math.abs(ball.getCenterX() - ball.getX()) + ball.getXVelocity()));
                    else
                        ball.setXDirection(-(-0.005 * Math.abs(ball.getCenterX() - ball.getX()) + ball.getXVelocity()));
                }
                else {
                    if (ball.getXVelocity() > 0)
                        ball.setXDirection(-(0.005 * Math.abs(ball.getCenterX() - ball.getX()) + ball.getXVelocity()));
                    else
                        ball.setXDirection((-0.005 * Math.abs(ball.getCenterX() - ball.getX()) + ball.getXVelocity()));
                }

                player.applyScore(1);
            }
        }
    }

    public void ballHitsABlock(){
        for (List<Block> blocks1 : blocks) {
            for (Ball ball : balls){
                for (int i = 0; i < blocks1.size(); i++) {
                    if (ball.intersects(blocks1.get(i))){
                        player.applyScore(3);
                        int tmp = blocks1.size();
                        blocks1.get(i).collision(blocks1, ball);
                        if (blocks1.size() == tmp-1) i--;
                    }
                }
            }
        }
    }

    public void ballHitsTheFloor(){
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            if (ball.y >= GAME_HEIGHT - BALL_DIAMETER){
                balls.remove(i);
                if (balls.size() == 0){
                    player.loseLife();
                    if (player.getLives() <= -1) loseTheGame();
                    isStarted = false;
                    newPaddle();
                    newBall();
                }
                i--;
            }
        }
    }

    public void paddleHitsThePrize(){
        for (int i = 0; i < Prize.getPrizes().size(); i++) {
            if (paddle.intersects(Prize.getPrizes().get(i))){
                Prize.getPrizes().get(i).usePrize(this);
                Prize.getPrizes().remove(i);
                i--;
            }
        }
    }


    public void blockHitsTheFloor(){
        loop: for (List<Block> blockList : blocks) {
            for (Block block : blockList) {
                if (block.y >= GAME_HEIGHT - BLOCK_HEIGHT){
                    loseTheGame();
                    break loop;
                }
            }
        }
    }

    public int addNewLineOfBLocks(int timer){
        timer--;
        if (timer == 0){
            Block.addNewLine(blocks);
            timer = 750;
        }
        return timer;
    }

    public int onWinkerBlock(int timer){
        timer--;
        if (timer == 0){
            WinkerBlock.onWinkerBlock();
            timer = new Random().nextInt(100) + 50;
        }
        return timer;
    }

    public int offWinkerBlock(int timer){
        timer--;
        if (timer == 0){
            WinkerBlock.offWinkerBlock();
            timer = new Random().nextInt(100) + 50;
        }
        return timer;
    }

    public int checkDizzyModeTimer(int timer){
        if (paddle.isDizzyMode()){
            timer--;
            if (timer == 0){
                paddle.setDizzyMode(false);
                timer = 750;
            }
        }
        return timer;
    }

    public int checkDBigPaddleModeTimer(int timer){
        if (paddle.isBigSizeMode()){
            timer--;
            if (timer == 0){
                paddle.setBigSizeMode(false);
                timer = 750;
            }
        }
        return timer;
    }

    public int checkSmallPaddleModeTimer(int timer){
        if (paddle.isSmallSizeMode()){
            timer--;
            if (timer == 0){
                paddle.setSmallSizeMode(false);
                timer = 750;
            }
        }
        return timer;
    }

    public int checkFireBallModeTimer(int timer){
        for (Ball ball : balls) {
            if (ball.isFireMode()){
                timer--;
                if (timer == 0){
                    ball.setFireMode(false);
                    timer = 750;
                }
            }
        }
        return timer;
    }

    public int checkFastBallModeTimer(int timer){
        for (Ball ball : balls) {
            if (ball.isFastMode()){
                timer--;
                if (timer == 0){
                    ball.setFastMode(false);
                    timer = 750;
                }
            }
        }
        return timer;
    }

    public int checkSlowBallModeTimer(int timer){
        for (Ball ball : balls) {
            if (ball.isSlowMode()){
                timer--;
                if (timer == 0){
                    ball.setSlowMode(false);
                    timer = 750;
                }
            }
        }
        return timer;
    }

    public void loseTheGame(){
        /// save score
        JOptionPane.showMessageDialog(null, "You lost!", "OOPS!", JOptionPane.INFORMATION_MESSAGE);
        restart();
    }

    public void restart(){
        player.setLives(3);
        player.setScore(0);
        isStarted = false;
        balls.clear();
        newBall();
        newPaddle();
        Prize.getPrizes().clear();
        newBlocks();
    }

    @Override
    public void run() {
        ///game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int addNewBlockLineTimer = 750;
        int dizzyPaddleTimer = 750;
        int bigPaddleTimer = 750;
        int smallPaddleTimer = 750;
        int fireBallTimer = 750;
        int fastBallTimer = 750;
        int slowBallTimer = 750;
        int offWinkerBlockTimer = new Random().nextInt(150)+50;
        int onWinkerBlockTimer = new Random().nextInt(150)+50;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                if (isStarted){
                    move();
                    checkCollision();

                    try {
                        ModelSaver.saveScore(this);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    addNewBlockLineTimer = addNewLineOfBLocks(addNewBlockLineTimer);

                    offWinkerBlockTimer = offWinkerBlock(offWinkerBlockTimer);
                    onWinkerBlockTimer = onWinkerBlock(onWinkerBlockTimer);

                    dizzyPaddleTimer = checkDizzyModeTimer(dizzyPaddleTimer);
                    bigPaddleTimer = checkDBigPaddleModeTimer(bigPaddleTimer);
                    smallPaddleTimer = checkSmallPaddleModeTimer(smallPaddleTimer);
                    fireBallTimer = checkFireBallModeTimer(fireBallTimer);
                    fastBallTimer = checkFastBallModeTimer(fastBallTimer);
                    slowBallTimer = checkSlowBallModeTimer(slowBallTimer);
                }
                repaint();
                delta--;
            }
        }
    }

    public class AL extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE){
                isStarted = !isStarted;
            }
            if (e.getKeyCode() == KeyEvent.VK_R){
                restart();
            }
            if (e.getKeyCode() == KeyEvent.VK_S){
                try {
                    ModelSaver modelSaver = new ModelSaver(getGamePanel());
                    modelSaver.save();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            if (isStarted){
                paddle.keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);
        }
    }
}

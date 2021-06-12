package logic;

import model.Ball;
import model.Paddle;
import model.Player;
import model.blocks.*;
import panel.GamePanel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ModelLoader {
    private GamePanel gamePanel;
    private File blocksFile;
    private File playerFile;
    private File ballsFile;
    private File paddleFile;

    public ModelLoader(GamePanel gamePanel, String name){
        this.gamePanel = gamePanel;

        this.blocksFile = new File("resources\\" + name + "\\blocks");
        this.ballsFile = new File("resources\\" + name + "\\balls");
        this.playerFile = new File("resources\\" + name + "\\player.txt");
        this.paddleFile = new File("resources\\" + name + "\\paddle.txt");
    }

    public static void rename(String name, String newName){
       new File("resources\\" + name).renameTo(new File("resources\\" + newName));
    }

    public void load() throws FileNotFoundException {
        loadBlocks();
        loadPlayer();
        loadBalls();
        loadPaddle();
    }

    private void loadBlocks() throws FileNotFoundException {
        gamePanel.getBlocks().clear();
        for (File blockFile : blocksFile.listFiles()) {
            loadBlock(blockFile);
        }
    }

    private void loadBlock(File blockFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(blockFile);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int type = scanner.nextInt();
        List<Block> blockList = checkForLine(y);
        if (type == 0) blockList.add(new GlassBlock(x, y, 50, 20));
        if (type == 1) blockList.add(new InvisibleBlock(x, y, 50, 20));
        if (type == 2) blockList.add(new PrizeBlock(x, y, 50, 20));
        if (type == 3) blockList.add(new WinkerBlock(x, y, 50, 20));
        if (type == 4) blockList.add(new WoodenBlock(x, y, 50, 20));
        scanner.close();
    }

    /// if line with this y doesnt exist create one
    private List<Block> checkForLine(int y){
        for (List<Block> blockList : gamePanel.getBlocks()) {
            try {
                if (blockList.get(0).y == y){
                   return blockList;
                }
            }
            catch (Exception ignored){ }
        }
        gamePanel.getBlocks().add(new ArrayList<>());
        return gamePanel.getBlocks().get(gamePanel.getBlocks().size()-1);
    }

    private void loadPlayer() throws FileNotFoundException {
        Scanner scanner = new Scanner(playerFile);
        String name = scanner.next();
        int lives = scanner.nextInt();
        int score = scanner.nextInt();
        gamePanel.getPlayer().setLives(lives);
        gamePanel.getPlayer().setScore(score);
        scanner.close();
    }

    private void loadBalls() throws FileNotFoundException {
        gamePanel.getBalls().clear();
        for (File ballFile : ballsFile.listFiles()) {
            loadBall(ballFile);
        }
    }

    private void loadBall(File ballFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(ballFile);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        double xVelocity = scanner.nextDouble();
        int yVelocity = scanner.nextInt();
        int fireMode = scanner.nextInt();
        int fastMode = scanner.nextInt();
        int slowMode = scanner.nextInt();
        Ball ball = new Ball(x, y, 15, 15, xVelocity, yVelocity);
        if (fireMode == 1) ball.setFireMode(true);
        if (fastMode == 1) ball.setFastMode(true);
        if (slowMode == 1) ball.setSlowMode(true);
        gamePanel.getBalls().add(ball);
        scanner.close();
    }

    private void loadPaddle() throws FileNotFoundException {
        Scanner scanner = new Scanner(paddleFile);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int dizzyMode = scanner.nextInt();
        int bigMode = scanner.nextInt();
        int smallMode = scanner.nextInt();
        Paddle paddle = new Paddle(x, y, 80, 20);
        if (dizzyMode == 1) paddle.setDizzyMode(true);
        if (bigMode == 1) paddle.setBigSizeMode(true);
        if (smallMode == 1) paddle.setSmallSizeMode(true);
        gamePanel.setPaddle(paddle);
        scanner.close();
    }

    public static void deleteFile(String path){
        File file = new File(path);
        for (File file1 : file.listFiles()) {
            file1.delete();
        }
    }

    public static File getFile(String path, String fileName){
        File dir = new File(path);
        for (File file : dir.listFiles()) {
            if (file.getName().equals(fileName)) return file;
        }
        return null;
    }

    /// for table
    public static List<Player> getPlayers() throws FileNotFoundException {
        File playersFile = new File("resources\\players.txt");
        List<Player> players = new ArrayList<>();
        Scanner scanner = new Scanner(playersFile);
        while (scanner.hasNextLine()){
            players.add(readPlayer(scanner.nextLine()));
        }
        scanner.close();
        return players;
    }

    /// name, score
    private static Player readPlayer(String data){
        String[] tmp = data.split(" ");
        String name = tmp[0];
        int score = Integer.parseInt(tmp[1]);
        Player player = new Player(name);
        player.setScore(score);
        return player;
    }
}

package logic;

import model.Ball;
import model.Player;
import model.blocks.Block;
import panel.GamePanel;

import java.io.*;
import java.util.List;

public class ModelSaver {
    private GamePanel gamePanel;
    private File blocksFile;
    //    private File prizesFile;
    private File playerFile;
    private File ballsFile;
    private File paddleFile;

    public ModelSaver(GamePanel gamePanel) throws IOException {
        this.gamePanel = gamePanel;

        String name = gamePanel.getPlayer().getName();
        File dir = new File("resources\\" + name);
        if (!dir.exists()) dir.mkdir();
        this.blocksFile = new File("resources\\" + name + "\\blocks");
        if (!blocksFile.exists()) blocksFile.mkdir();
        this.ballsFile = new File("resources\\" + name + "\\balls");
        if (!ballsFile.exists()) ballsFile.mkdir();
        this.playerFile = new File("resources\\" + name + "\\player.txt");
        if (!playerFile.exists()) playerFile.createNewFile();
        this.paddleFile = new File("resources\\" + name + "\\paddle.txt");
        if (!paddleFile.exists()) paddleFile.createNewFile();
    }

    public void save() throws FileNotFoundException {
        saveBlocks();
        savePlayer();
        saveBalls();
        savePaddle();
        saveScore(gamePanel);
    }

    private void saveBlocks() throws FileNotFoundException {
        ModelLoader.deleteFile(blocksFile.getPath());
        int tmp = 0;
        for (List<Block> blockList : gamePanel.getBlocks()) {
            for (int i = 0; i < blockList.size(); i++) {
                saveBlock(blockList.get(i), i + tmp);
            }
            tmp += blockList.size();
        }
    }

    private void saveBlock(Block block, int num) throws FileNotFoundException {
        File blockFile = new File(blocksFile + "\\" + num + ".txt");
        PrintStream printStream = new PrintStream(new FileOutputStream(blockFile));
        printStream.println(block.json());
        printStream.flush();
        printStream.close();
    }

    private void savePlayer() throws FileNotFoundException {
        PrintStream printStream = new PrintStream(new FileOutputStream(playerFile));
        printStream.println(gamePanel.getPlayer().json());
        printStream.flush();
        printStream.close();
    }

//    private void saveBall() throws FileNotFoundException {
//        PrintStream printStream = new PrintStream(new FileOutputStream(ballFile));
//        for (int i = 0; i < ; i++) {
//
//        }
//        printStream.println(gamePanel.getBall().json());
//        printStream.flush();
//        printStream.close();
//    }

    private void saveBalls() throws FileNotFoundException {
        ModelLoader.deleteFile(ballsFile.getPath());
        for (int i = 0; i < gamePanel.getBalls().size(); i++) {
            saveBall(gamePanel.getBalls().get(i), i);
        }
    }

    private void saveBall(Ball ball, int num) throws FileNotFoundException {
        File blockFile = new File(ballsFile + "\\" + num + ".txt");
        PrintStream printStream = new PrintStream(new FileOutputStream(blockFile));
        printStream.println(ball.json());
        printStream.flush();
        printStream.close();
    }


    private void savePaddle() throws FileNotFoundException {
        PrintStream printStream = new PrintStream(new FileOutputStream(paddleFile));
        printStream.println(gamePanel.getPaddle().json());
        printStream.flush();
        printStream.close();
    }

    public static void saveScore(GamePanel gamePanel) throws FileNotFoundException {
        File playersFile = new File("resources\\players.txt");
        List<Player> players = ModelLoader.getPlayers();
        PrintStream printStream = new PrintStream(new FileOutputStream(playersFile));
        Player player = checkForThePlayer(players, gamePanel);
        players.add(player);
        for (Player player1 : players) {
            printStream.println(player1.getName() + " " + player1.getScore());
        }
        printStream.flush();
        printStream.close();
    }

    /// check for the player if already exist in the table and return if it has more score;
    private static Player checkForThePlayer(List<Player> players, GamePanel gamePanel) {
        Player player = gamePanel.getPlayer();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(player.getName())) {
                Player player1 = players.remove(i);
                if (player1.getScore() > player.getScore()) {
                    return player1;
                }
            }
        }
        return player;
    }
}

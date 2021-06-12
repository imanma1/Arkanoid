package panel;

import logic.ModelLoader;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.List;

public class ScoreTablePanel extends JPanel {

    public ScoreTablePanel() throws FileNotFoundException {
        super();
        this.setFocusable(true);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.BLACK);
        addScores();
    }

    private void addScores() throws FileNotFoundException {
            List<Player> players = ModelLoader.getPlayers();
            sortPlayers(players);
            for (Player player : players) {
                JLabel label = new JLabel(player.getName() + " : " + player.getScore());
                label.setFont(new Font("Consolas", Font.PLAIN, 15));
                label.setForeground(Color.WHITE);
                this.add(label);
            }
//        for (int i = 0; i < 3; i++) {
//            JLabel label = new JLabel("iman : 56" + i);
//            label.setFont(new Font("Consolas", Font.PLAIN, 15));
//            label.setForeground(Color.WHITE);
//            this.add(label);
//        }
    }

    private void sortPlayers(List<Player> players){
        for (int i = 0; i < players.size(); i++) {
            for (int j = i+1; j < players.size(); j++) {
                Player player1 = players.get(i);
                Player player2 = players.get(j);
                if (player2.getScore()>player1.getScore()){
                    players.set(i, player2);
                    players.set(j, player1);
                }
            }
        }
    }
}

package frame;

import panel.GamePanel;
import panel.ScoreTablePanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.FileNotFoundException;

public class GameFrame extends JFrame {

    private GamePanel gamePanel;
    private ScoreTablePanel tablePanel;

    public GameFrame(String name) throws FileNotFoundException {
        super("Arkanoid");
        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);
        panel.setLayout(new BorderLayout(1,0));
        gamePanel = new GamePanel(name);
        panel.add(gamePanel, BorderLayout.WEST);
        tablePanel = new ScoreTablePanel();
        panel.add(tablePanel, BorderLayout.EAST);

        this.add(panel);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.YELLOW);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public GamePanel getPanel() {
        return gamePanel;
    }
}

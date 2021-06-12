package frame;

import logic.ModelLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

public class MainMenuFrame extends JFrame implements ActionListener {
    JButton newGameButton;
    JButton loadButton;

    public MainMenuFrame(){
        super();

        newGameButton = new JButton("New game");
        loadButton = new JButton("Load game");
        newGameButton.addActionListener(this);
        loadButton.addActionListener(this);

        this.setLayout(new GridLayout(2, 1));
        this.add(newGameButton);
        this.add(loadButton);

        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(300,300));
    }

    public String getPlayerName(String msg){
        String result;
        do {
            result = JOptionPane.showInputDialog(this, msg);
        } while (result == null || result.length() == 0);
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton){
            try {
                newGame();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
        if (e.getSource() == loadButton){
            try {
                loadTheGame();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }

    public void newGame() throws FileNotFoundException {
        String name = getPlayerName("Enter player name:");
        this.dispose();
        new GameFrame(name);
    }

    public void loadTheGame() throws FileNotFoundException {
        String name = getPlayerName("Enter player name:");
        if (ModelLoader.getFile("resources", name) == null){
            JOptionPane.showMessageDialog(null, "This file doesn't exist", "error" ,JOptionPane.WARNING_MESSAGE);
        } else {
            String newName = getPlayerName("Enter new player name:");
            this.dispose();
            GameFrame gameFrame = new GameFrame(newName);
            ModelLoader.rename(name, newName);
            ModelLoader modelLoader = new ModelLoader(gameFrame.getPanel(), newName);
            try {
                modelLoader.load();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }
}

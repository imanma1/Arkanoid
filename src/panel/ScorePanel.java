package panel;

import model.Player;

import java.awt.*;

public class ScorePanel extends Rectangle {
    Player player;

    public ScorePanel(Player player){
        this.player = player;
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN, 20));

        g.drawString("Lives: " + player.getLives() + "    " + "Score: " + player.getScore(), 180, 500);
    }
}

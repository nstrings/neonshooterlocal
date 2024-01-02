package window;

import main.Game;
import utils.Constants;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public Game game;

    public GamePanel(Game game) {
        this.game = game;

        //Set the dimensions and focus the window
        Dimension size = new Dimension(Constants.WIDTH, Constants.HEIGHT);
        setPreferredSize(size);

        setFocusable(true);
        requestFocus();

        //Add the input listener
        Controls controls = new Controls(game);
        addKeyListener(controls);
        addMouseListener(controls);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.display(g);
    }
}
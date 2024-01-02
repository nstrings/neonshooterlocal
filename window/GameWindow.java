package window;

import javax.swing.*;

public class GameWindow extends JFrame {

    private JFrame gameWindow;

    public GameWindow(GamePanel gamePanel){
        //Create the game window using the gamePanel we've created earlier
        gameWindow = new JFrame("Neon Vector Shooter");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);

        gameWindow.add(gamePanel);

        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }
}
package window;

import main.Game;
import utils.Vector;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controls implements KeyListener, MouseListener {

    private Game game;

    public Controls(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Call the corresponding functionalities to each key
        if (game.gameOver == false){
            if (e.getKeyChar() == 'w') game.player.setVelocity(Vector.UP);
            if (e.getKeyChar() == 'a') game.player.setVelocity(Vector.LEFT);
            if (e.getKeyChar() == 's') game.player.setVelocity(Vector.DOWN);
            if (e.getKeyChar() == 'd') game.player.setVelocity(Vector.RIGHT);
            if (e.getKeyChar() == ' ') game.player.isShooting = true;
        }
        else {
            if (e.getKeyChar() == 'r') game.restart();
            if (e.getKeyCode() == 40) game.difficulty = game.difficulty < 1 ? game.difficulty + 1 : 2;
            if (e.getKeyCode() == 38) game.difficulty = game.difficulty > 0 ? game.difficulty - 1 : 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (game.gameOver == false) {
            if (
                    (e.getKeyChar() == 'w' && game.player.velocity == Vector.UP) ||
                    (e.getKeyChar() == 'a' && game.player.velocity == Vector.LEFT) ||
                    (e.getKeyChar() == 's' && game.player.velocity == Vector.DOWN) ||
                    (e.getKeyChar() == 'd' && game.player.velocity == Vector.RIGHT)
            ) {
                game.player.velocity = Vector.ZERO;
            }
            if (e.getKeyChar() == ' ') game.player.isShooting = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

package main;

import gameobjects.Bullet;
import gameobjects.Player;
import gameobjects.enemies.Enemy;
import gameobjects.enemies.SlideEnemy;
import gameobjects.enemies.SpikeEnemy;
import gameobjects.enemies.TrackerEnemy;
import utils.BufferedImages;
import utils.Constants;
import utils.Vector;

import java.awt.*;
import java.util.ArrayList;

public class Game implements Runnable {

    private Thread gameThread;
    private int width;
    private int height;

    public Player player;
    public ArrayList<Enemy> enemies;
    public int enemySpawnTime;

    public int score;
    public int lives;
    public boolean gameOver;
    public int difficulty;

    public Game(){
        width = Constants.WIDTH;
        height = Constants.HEIGHT;
        difficulty = 0;
        restart();
    }

    public void restart(){
        player = new Player(width/4, height/2, 0.04f);
        enemies = new ArrayList<Enemy>();
        enemySpawnTime = Constants.ENEMY_SPAWN_TIMES[difficulty];

        score = 0;
        lives = 3;
        gameOver = false;
    }

    public void initializeGame() {
        gameThread = new Thread(this);
        gameThread.start();
        Constants.playSound("./res/sounds/looperman.wav", -10, true);
    }

    public synchronized void display(Graphics g){
        drawBackground(g);

        if (gameOver == false){
            for (Bullet bullet : player.bullets){
                bullet.display(g);
            }
            for (Enemy enemy : enemies){
                enemy.display(g);
            }
            player.display(g);
        }

        drawUI(g);
    }

    public synchronized void update(){
        if (gameOver == false){
            removeFlagged();
            spawnEnemies();

            for (Bullet bullet : player.bullets){
                bullet.move();
            }
            for (Enemy enemy : enemies){
                enemy.move();
            }
            player.move();
            player.shoot();

            collision();
        }
    }

    //Draw the background to the middle of the screen
    public void drawBackground(Graphics g){
        int bw = BufferedImages.backgroundImg.getWidth();
        int bh = BufferedImages.backgroundImg.getHeight();
        int bx = (width - bw)/2;
        int by = (height - bh)/2;
        g.drawImage(BufferedImages.backgroundImg, bx, by, bw, bh, null);
    }

    //Draw UI elements, based on the state of the game
    public void drawUI(Graphics g){
        if (gameOver == false){
            Constants.displayText(g, "Lives: " + lives, 20, 30, 20, 2, false);
            Constants.displayText(g, "Score: " + score, width/2, 30, 20, 2, true);
        }
        else {
            Constants.displayText(g, "Game Over", width/2, height/2 - 100, 120, 4, true);

            Constants.displayText(g, "Difficulty", width/2, height/2 + 20, 48, 3, true);
            Constants.displayText(g, "Easy", width/2 - 30, height/2 + 70, 32, 2, false);
            Constants.displayText(g, "Normal", width/2 - 30, height/2 + 110, 32, 2, false);
            Constants.displayText(g, "Hard", width/2 - 30, height/2 + 150, 32, 2, false);
            //Move the arrow to the current difficult (40 * difficulty, because the texts are 40 pixel apart)
            g.drawImage(BufferedImages.arrowImg, width/2 - 60, height/2 + 48 + 40 * difficulty, 20, 20, null);

            Constants.displayText(g, "Continue? Press [R] to restart", width/2, height - 20, 24, 2, true);
        }
    }

    public void spawnEnemies(){
        if (enemySpawnTime <= 0){
            //Random coordinates for the enemy
            float x = (float) (Math.random() * width);
            float y = (float) (Math.random() * height);

            //Pick a random type of enemy
            Enemy enemy;
            double type = Math.random();
            if (type < 0.4){
                enemy = new TrackerEnemy(x, y, 0.4f, player);
            } else if (type < 0.7){
                enemy = new SlideEnemy(x, y, 0.4f);
            } else {
                enemy = new SpikeEnemy(x, y, 0.4f);
            }

            //Only create the enemy and set the waiting time (enemySpawnTime), if the enemy is far enough from the player
            if (enemy.distance(player) > player.w){
                enemies.add(enemy);
                enemySpawnTime = (int) (Constants.ENEMY_SPAWN_TIMES[difficulty] * (Math.random() / 2 + 0.5f));
            }
        }
        else {
            enemySpawnTime--;
        }
    }

    public void removeFlagged(){
        for(int i = enemies.size() - 1; i >= 0; i--) {
            if (enemies.get(i).removeFlag) enemies.remove(i);
        }
        for(int i = player.bullets.size() - 1; i >= 0; i--) {
            if (player.bullets.get(i).removeFlag) player.bullets.remove(i);
        }
    }

    public void collision(){
        //Check if the enemies-bullets and enemies-player are close enough, and act accordingly
        for(Enemy enemy : enemies){
            for (Bullet bullet : player.bullets){
                if (enemy.distance(bullet) < (enemy.w + bullet.w) * 0.25f && bullet.removeFlag == false){
                    enemy.removeFlag = true;
                    bullet.removeFlag = true;
                    Constants.playSound("./res/sounds/Explosion.wav", -20, false);
                    score++;
                    break;
                }
            }

            if (player.distance(enemy) < (player.w + enemy.w) * 0.3f && enemy.removeFlag == false){
                enemy.removeFlag = true;
                Constants.playSound("./res/sounds/Explosion.wav", -20, false);

                lives--;
                if (lives <= 0){
                    gameOver = true;
                }
            }
        }
    }

    @Override
    public void run() {
        //Save the current time
        long previousTime = System.currentTimeMillis();
        while(true){
            //Save the new current time
            long currentTime = System.currentTimeMillis();
            //Calculate the elapsed time between the previous and current time
            //If it's greater than the duration between frames, update the previous time to the current and call the functions
            if(currentTime - previousTime >= Constants.FRAME_DURATION){
                previousTime = currentTime;

                update();
                Main.gamePanel.repaint();
            }
        }
    }
}

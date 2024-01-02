package gameobjects;

import main.Main;
import utils.BufferedImages;
import utils.Constants;
import utils.Vector;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends GameObject{

    public Vector velocity;
    public ArrayList<Bullet> bullets;

    public boolean isShooting;
    public int shootCooldown;

    public Player(float x, float y, float size) {
        super(x, y, size, BufferedImages.playerImg, 0);
        speed = 8;

        velocity = new Vector(0, 0);
        bullets = new ArrayList<>();
    }

    //Change the players coordinates based on its velocity
    //If it goes out of screen, move it back on the other side
    public void move(){
        x += velocity.x * speed;
        y += velocity.y * speed;
        if (x < -w/2 || x >= Constants.WIDTH + w/2)  x = Constants.WIDTH - x;
        if (y < -h/2 || y >= Constants.HEIGHT + h/2) y = Constants.HEIGHT - y;
    }

    //If it can shoot, create a bullet and set its velocity towards the mouse (also play shoot sound)
    public void shoot(){
        if (shootCooldown > 0){
            shootCooldown--;
        }
        else if (isShooting && shootCooldown <= 0){
            Vector mouse = Constants.getMouse();
            Vector direction = new Vector(mouse.x - x, mouse.y - y).normalized();
            float dx = direction.x * w/4;
            float dy = direction.y * h/4;

            bullets.add(new Bullet(x + dx, y + dy, 0.5f, direction.angle()));
            shootCooldown = Constants.PLAYER_SHOOT_COOLDOWNS[Main.game.difficulty];

            Constants.playSound("./res/sounds/Shoot.wav", -20, false);
        }
    }

    //Change and rotate the player towards the velocity
    public void setVelocity(Vector velocity){
        this.velocity = velocity;
        rotation = velocity.angle();
        rotate();
    }
}

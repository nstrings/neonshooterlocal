package gameobjects.enemies;

import gameobjects.GameObject;

import java.awt.image.BufferedImage;

//Basic parent class, so the other type of enemies can inherit from it
public class Enemy extends GameObject {
    public Enemy(float x, float y, float size, BufferedImage img) {
        super(x, y, size, img, 0);
        opacity = 0f;
    }

    public void move(){}
}

package gameobjects;

import utils.BufferedImages;
import utils.Vector;

public class Bullet extends GameObject {

    public Vector velocity;

    public Bullet(float x, float y, float size, float rotation) {
        super(x, y, size, BufferedImages.bulletImg, rotation);
        speed = 20;

        velocity = new Vector((float) Math.cos(rotation), (float) Math.sin(rotation));
    }

    //Move the bullet by its velocity
    public void move(){
        x += velocity.x * speed;
        y += velocity.y * speed;
    }
}

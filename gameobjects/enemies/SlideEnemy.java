package gameobjects.enemies;

import utils.BufferedImages;
import utils.Constants;
import utils.Vector;

public class SlideEnemy extends Enemy{

    private Vector velocity;

    public SlideEnemy(float x, float y, float size) {
        super(x, y, size, BufferedImages.slideEnemyImg);

        speed = 12;
        rotation = (float) (Math.random() * Math.PI * 2);
        //Calculate the velocity, based on the rotation
        velocity = new Vector((float) Math.cos(rotation), (float) Math.sin(rotation));
    }

    //After picking a random direction (in the constructor), appear (increase the opacity) and move in that random direction
    @Override
    public void move(){
        rotation += 0.03f;
        rotate();
        if (opacity == 1){
            x += velocity.x * speed;
            y += velocity.y * speed;

            //If it goes out of screen, remove it
            if (x > Constants.WIDTH + w || x < -w || y > Constants.HEIGHT + h || y < -h) {
                removeFlag = true;
            }
        }
        else {
            opacity += 0.03;
            if (opacity > 1){
                opacity = 1;
            }
        }
    }
}

package gameobjects.enemies;

import gameobjects.GameObject;
import utils.BufferedImages;
import utils.Vector;

public class TrackerEnemy extends Enemy{

    public GameObject target;
    public Vector velocity;
    public float thrustForce = 2f;

    public TrackerEnemy(float x, float y, float size, GameObject target) {
        super(x, y, size, BufferedImages.trackerEnemyImg);
        this.target = target;

        speed = 9;
        velocity = new Vector(0, 0);
    }

    @Override
    public void move(){
        if (opacity == 1){
            //Calculate the direction towards the player and create a force, which pushes the enemy towards the player
            Vector force = new Vector(target.x - x, target.y - y).normalized();

            //Change the velocity, based on the force
            velocity.x += force.x * thrustForce;
            velocity.y += force.y * thrustForce;
            velocity.clampMagnitude(speed);

            //Change the position based on the velocity
            x += velocity.x;
            y += velocity.y;

            //Rotate towards the velocity
            rotation = velocity.angle();
            rotate();
        }
        else {
            opacity += 0.03;
            if (opacity > 1){
                opacity = 1;
            }
        }
    }
}

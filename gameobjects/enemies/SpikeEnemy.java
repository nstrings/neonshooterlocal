package gameobjects.enemies;

import utils.BufferedImages;
import utils.Vector;

public class SpikeEnemy extends Enemy{
    public SpikeEnemy(float x, float y, float size) {
        super(x, y, size, BufferedImages.spikeEnemyImg);
    }

    //Appear and rotate
    @Override
    public void move(){
        rotation += 0.02f;
        rotate();
        if (opacity != 1){
            opacity += 0.03;
            if (opacity > 1){
                opacity = 1;
            }
        }
    }
}

package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BufferedImages {

    public static BufferedImage backgroundImg;
    public static BufferedImage playerImg;
    public static BufferedImage bulletImg;

    public static BufferedImage trackerEnemyImg;
    public static BufferedImage spikeEnemyImg;
    public static BufferedImage slideEnemyImg;

    public static BufferedImage arrowImg;

    public static void loadImages() {
        backgroundImg = loadImage("./res/images/Background.png");
        playerImg = loadImage("./res/images/Player.png");
        bulletImg = loadImage("./res/images/Bullet.png");

        trackerEnemyImg = loadImage("./res/images/TrackerEnemy.png");
        spikeEnemyImg = loadImage("./res/images/SpikeEnemy.png");
        slideEnemyImg = loadImage("./res/images/SlideEnemy.png");

        arrowImg = loadImage("./res/images/Arrow.png");
    }

    private static BufferedImage loadImage(String path){
        File image = new File(path);
        try {
            BufferedImage bufferedImage = ImageIO.read(image);
            return bufferedImage;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}

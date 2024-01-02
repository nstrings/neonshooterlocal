package gameobjects;

import utils.BufferedImages;
import utils.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject {
    public float x, y;
    public float size;
    public BufferedImage img;
    public float opacity;
    private BufferedImage displayImg;

    public float w, h;
    public boolean removeFlag;

    public float rotation;
    public float speed;

    public GameObject(float x, float y, float size, BufferedImage img, float rotation) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.img = img;
        this.rotation = rotation;
        opacity = 1f;
        rotate();
    }

    public void display(Graphics g) {
        if (removeFlag == false){
            ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g.drawImage(displayImg, (int)(x - w/2), (int)(y - h/2), (int) w, (int) h, null);
        }
    }

    public void rotate(){
        displayImg = Constants.rotateImageByAngle(img, rotation);
        w = displayImg.getWidth() * size;
        h = displayImg.getHeight() * size;
    }

    public float distance(GameObject other){
        float dx = x - other.x;
        float dy = y - other.y;
        return (float) Math.sqrt(dx*dx + dy*dy);
    }
}
package utils;

import main.Main;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Constants {
    //Windows
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int FPS = 60;
    public static final float FRAME_DURATION = 1000 / Constants.FPS;

    //Player
    public static int[] PLAYER_SHOOT_COOLDOWNS = { FPS / 12, FPS / 10, FPS / 8 };

    //Enemy
    public static int[] ENEMY_SPAWN_TIMES = { FPS * 2, FPS, FPS / 2 };

    //Display text with different size, shadow and alignment
    public static void displayText(Graphics g, String text, float x, float y, int textSize, int shadow, boolean isCentered){
        g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, textSize));

        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int width = metrics.stringWidth(text);

        int nx = isCentered ? (int)x - width/2 : (int)x;

        g.setColor(Color.black);
        g.drawString(text, nx + shadow , (int)y + shadow);
        g.setColor(Color.white);
        g.drawString(text, nx, (int)y);
    }

    //Play the desired sound file at the a certain volume (can also loop it)
    public static void playSound(String file, float volume, boolean loop){
        File soundFile = new File(file);
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
        if (loop){
            Clip finalClip = clip;
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        finalClip.setFramePosition(0);
                        finalClip.start();
                    }
                }
            });
        }
        clip.start();
    }

    //Rotate any image by a certain degree of angle
    //https://stackoverflow.com/questions/8639567/java-rotating-images
    public static BufferedImage rotateImageByAngle(BufferedImage img, double angle) {
        double sin = Math.abs(Math.sin(angle));
        double cos = Math.abs(Math.cos(angle));
        int w = (int) Math.floor(img.getWidth() * cos + img.getHeight() * sin);
        int h = (int) Math.floor(img.getHeight() * cos + img.getWidth() * sin);
        final BufferedImage rotatedImage = new BufferedImage(w, h, img.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(angle,0, 0);
        at.translate(-img.getWidth() / 2, -img.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(img, rotatedImage);

        return rotatedImage;
    }

    //Find the coordinates of the mouse on the screen
    //G https://stackoverflow.com/questions/29187546/java-get-mouse-coordinates-within-window
    public static Vector getMouse(){
        Point windowLocationOnScreen = Main.gamePanel.getLocationOnScreen();
        Point mouseLocationOnScreen = MouseInfo.getPointerInfo().getLocation();
        int mouseX = mouseLocationOnScreen.x - windowLocationOnScreen.x;
        int mouseY = mouseLocationOnScreen.y - windowLocationOnScreen.y;
        return new Vector(mouseX, mouseY);
    }
}

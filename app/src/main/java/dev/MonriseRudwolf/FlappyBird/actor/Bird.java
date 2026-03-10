/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Monrise
 */

package dev.MonriseRudwolf.FlappyBird.actor;

import dev.MonriseRudwolf.FlappyBird.core.GameStates;
import java.awt.*;
import java.awt.image.BufferedImage;
import dev.MonriseRudwolf.FlappyBird.core.SpriteSheet;

public class Bird {
    private int x, y;
    private int velocity = 0;
    private final int gravity = 1;
    private final int size = 30;

    private BufferedImage[] frames;
    private int frame = 0;
    private int animTimer = 0;

    public Bird(int x, int y, SpriteSheet sheet) {
        this.x = x;
        this.y = y;

        // Cargar frames desde GameAtlas
        frames = new BufferedImage[SpriteSheet.GameAtlas.BIRD_YELLOW.length];
        for (int i = 0; i < SpriteSheet.GameAtlas.BIRD_YELLOW.length; i++) {
            Rectangle r = SpriteSheet.GameAtlas.BIRD_YELLOW[i];
            frames[i] = sheet.getSprite(r.x, r.y, r.width, r.height);
        }
    }

    public void update(GameStates state) {
        if(state == GameStates.Game_Over){
            frame = 1;
            
        }
        
        velocity += gravity;
        y += velocity;

        animTimer++;
        if (animTimer > 5) {
            frame++;
            animTimer = 0;
            if (frame >= frames.length) {
                frame = 0;
            }
        }
    }

    public void jump() {
        velocity = -12;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
    
    public void deathJump() {
        velocity = -8;
    }
    
    public void Reset() {
        y = 300;
        velocity = 0;
    }

    public void draw(Graphics g){

        Graphics2D g2 = (Graphics2D) g;
        
        double angle = Math.toRadians(velocity * 3);
        
        if(angle > Math.toRadians(90)){
            angle = Math.toRadians(90);
        }

        if(angle < Math.toRadians(-25)){
            angle = Math.toRadians(-25);
        }

        g2.rotate(angle, x + 17, y + 12);

        g2.drawImage(frames[frame], x, y, null);

        g2.rotate(-angle, x + 17, y + 12);
    }

    public int getY() {
        return y;
    }
}
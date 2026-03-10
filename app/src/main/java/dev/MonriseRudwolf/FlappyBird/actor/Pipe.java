/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.MonriseRudwolf.FlappyBird.actor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import dev.MonriseRudwolf.FlappyBird.core.SpriteSheet;
/**
 *
 * @author Monrise
 */

public class Pipe {
    private int x;
    private final int gapY;
    private int gapHeight; // ahora configurable
    private final int speed = 3;
    private boolean passed = false;

    private final BufferedImage pipeHeadUp;
    private final BufferedImage pipeHeadDown;
    private final BufferedImage pipeBody;

    public Pipe(int x, SpriteSheet sheet, int gapHeight) {
        this.x = x;
        this.gapY = new Random().nextInt(300) + 100;
        this.gapHeight = gapHeight;

        Rectangle rHeadUp   = SpriteSheet.GameAtlas.PIPE_HEAD_UP;
        Rectangle rHeadDown = SpriteSheet.GameAtlas.PIPE_HEAD_DOWN;
        Rectangle rBody     = SpriteSheet.GameAtlas.PIPE_BODY;

        pipeHeadUp   = sheet.getSprite(rHeadUp.x, rHeadUp.y, rHeadUp.width, rHeadUp.height);
        pipeHeadDown = sheet.getSprite(rHeadDown.x, rHeadDown.y, rHeadDown.width, rHeadDown.height);
        pipeBody     = sheet.getSprite(rBody.x, rBody.y, rBody.width, rBody.height);
    }

    public void update() {
        x -= speed;
    }

    public void draw(Graphics g) {
        int width = pipeBody.getWidth();
        int offsetX = (width - pipeHeadUp.getWidth()) / 2;

        // Tubería superior
        int topY = gapY - gapHeight;
        for (int y = topY - pipeBody.getHeight(); y > -pipeBody.getHeight(); y -= pipeBody.getHeight()) {
            g.drawImage(pipeBody, x, y, null);
        }
        g.drawImage(pipeHeadDown, x + offsetX, topY - pipeHeadDown.getHeight(), null);

        // Tubería inferior
        int bottomY = gapY + gapHeight;
        g.drawImage(pipeHeadUp, x + offsetX, bottomY, null);
        for (int y = bottomY + pipeHeadUp.getHeight(); y < 600; y += pipeBody.getHeight()) {
            g.drawImage(pipeBody, x, y, null);
        }
    }

    public boolean collides(Rectangle bird) {
        int width = pipeBody.getWidth();
        Rectangle top = new Rectangle(x, 0, width, gapY - gapHeight);
        Rectangle bottom = new Rectangle(x, gapY + gapHeight, width, 600);
        return top.intersects(bird) || bottom.intersects(bird);
    }

    public boolean isOffScreen() {
        return x + pipeBody.getWidth() < 0;
    }

    public boolean isPassed(int birdX) {
        if (!passed && x + pipeBody.getWidth() < birdX) {
            passed = true;
            return true;
        }
        return false;
    }

    // Permite cambiar el tamaño del hueco dinámicamente
    public void setGapHeight(int gapHeight) {
        this.gapHeight = gapHeight;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.MonriseRudwolf.FlappyBird.core;

/**
 *
 * @author Monrise
 */

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class SpriteSheet {

    private final BufferedImage sheet;

    public SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }

    public BufferedImage getSprite(int x, int y, int w, int h) {
        if (x + w > sheet.getWidth() || y + h > sheet.getHeight()) {
            throw new RuntimeException(
                "Sprite fuera del sheet: " + x + "," + y
            );
        }
        return sheet.getSubimage(x, y, w, h);
    }

    // ===========================
    // GAME ATLAS
    // ===========================
    public static class GameAtlas {
        // Fondos
        public static final Rectangle BACKGROUND_DAY   = new Rectangle(0, 0, 288, 512);
        public static final Rectangle BACKGROUND_NIGHT = new Rectangle(292, 0, 288, 512);

        // Suelo
        public static final Rectangle GROUND = new Rectangle(584, 0, 336, 112);

        // Tuberías divididas en tres partes 
        public static final Rectangle PIPE_HEAD_UP   = new Rectangle(168, 646, 52, 26);   // boca inferior
        public static final Rectangle PIPE_HEAD_DOWN = new Rectangle(112, 940, 52, 26);   // boca superior
        public static final Rectangle PIPE_BODY      = new Rectangle(170, 672, 48, 294); // tramo repetible

        // Pájaro amarillo (3 frames)
        public static final Rectangle[] BIRD_YELLOW = {
            new Rectangle(230, 658, 34, 24),
            new Rectangle(230, 710, 34, 24),
            new Rectangle(174, 982, 34, 24)
        };

        // Texto UI
        public static final Rectangle GAME_OVER   = new Rectangle(790, 118, 194, 49);
        public static final Rectangle GET_READY   = new Rectangle(192, 180, 192, 42);
        public static final Rectangle BUTTON_OK   = new Rectangle(924, 89, 80, 28);
        public static final Rectangle BUTTON_PLAY = new Rectangle(210, 300, 80, 30);

        // Medallas
        public static final Rectangle MEDAL_BRONZE = new Rectangle(420, 300, 44, 44);
        public static final Rectangle MEDAL_SILVER = new Rectangle(470, 300, 44, 44);
        public static final Rectangle MEDAL_GOLD   = new Rectangle(520, 300, 44, 44);

        // Dígitos (0–9)
        public static final Rectangle[] DIGITS = {
            new Rectangle(250, 200, 20, 30), // 0
            new Rectangle(270, 200, 20, 30), // 1
            new Rectangle(290, 200, 20, 30), // 2
            new Rectangle(310, 200, 20, 30), // 3
            new Rectangle(330, 200, 20, 30), // 4
            new Rectangle(350, 200, 20, 30), // 5
            new Rectangle(370, 200, 20, 30), // 6
            new Rectangle(390, 200, 20, 30), // 7
            new Rectangle(410, 200, 20, 30), // 8
            new Rectangle(430, 200, 20, 30)  // 9
        };
    }
}
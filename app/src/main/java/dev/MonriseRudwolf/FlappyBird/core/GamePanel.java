/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Monrise
 */
package dev.MonriseRudwolf.FlappyBird.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dev.MonriseRudwolf.FlappyBird.actor.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private final Timer timer;

    private Bird bird;
    private final ArrayList<Pipe> pipes;

    private GameStates state = GameStates.Menu;

    public int score = 0;
    private int pipeSpawnTimer = 0;

    // SPRITES
    private BufferedImage background;
    private final BufferedImage ground;
    private final BufferedImage gameOverText;
    private final BufferedImage buttonOk;
    private final SpriteSheet sheet;

    private int groundX = 0;
    private int backgroundX = 0;
    private int currentGapHeight = 120;

    public GamePanel() {
        addKeyListener(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (state != null) {
                        switch (state) {
                            case Menu -> state = GameStates.Playing;
                            case Playing -> bird.jump();
                            case Game_Over -> resetGame();
                            default -> {}
                        }
                    }
                }
            }
        });
        setFocusable(true);


        setPreferredSize(new Dimension(400, 600));
        setFocusable(true);
        addKeyListener(this);

        // CARGAR SPRITESHEET
        BufferedImage sheetImg = ResourcesLoader.load("/sprites/flappy_sprites.png");
        sheet = new SpriteSheet(sheetImg);

        // =======================
        // BACKGROUND
        // =======================
        background = sheet.getSprite(
            SpriteSheet.GameAtlas.BACKGROUND_DAY.x,
            SpriteSheet.GameAtlas.BACKGROUND_DAY.y,
            SpriteSheet.GameAtlas.BACKGROUND_DAY.width,
            SpriteSheet.GameAtlas.BACKGROUND_DAY.height
        );

        // =======================
        // GROUND
        // =======================
        ground = sheet.getSprite(
            SpriteSheet.GameAtlas.GROUND.x,
            SpriteSheet.GameAtlas.GROUND.y,
            SpriteSheet.GameAtlas.GROUND.width,
            SpriteSheet.GameAtlas.GROUND.height
        );

        bird = new Bird(100, 300, sheet);

        // =======================
        // UI ELEMENTS
        // =======================
        gameOverText = sheet.getSprite(
            SpriteSheet.GameAtlas.GAME_OVER.x,
            SpriteSheet.GameAtlas.GAME_OVER.y,
            SpriteSheet.GameAtlas.GAME_OVER.width,
            SpriteSheet.GameAtlas.GAME_OVER.height
        );

        buttonOk = sheet.getSprite(
            SpriteSheet.GameAtlas.BUTTON_OK.x,
            SpriteSheet.GameAtlas.BUTTON_OK.y,
            SpriteSheet.GameAtlas.BUTTON_OK.width,
            SpriteSheet.GameAtlas.BUTTON_OK.height
        );

        pipes = new ArrayList<>();

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (state == GameStates.Playing) {
            bird.update(state);
            if (bird.getY() > 520 || bird.getY() < -1) {
                    state = GameStates.Game_Over;
            }
            // SPAWN DE PIPES
            pipeSpawnTimer++;

            if (pipeSpawnTimer > 90) {
                pipes.add(new Pipe(400, sheet, currentGapHeight));
                pipeSpawnTimer = 0;
            }
            
            if (score > 20) currentGapHeight = 100;
            if (score > 25) currentGapHeight = 90;
            if (score > 50) {
                currentGapHeight = 80;
                background = sheet.getSprite(
                    SpriteSheet.GameAtlas.BACKGROUND_NIGHT.x,
                    SpriteSheet.GameAtlas.BACKGROUND_NIGHT.y,
                    SpriteSheet.GameAtlas.BACKGROUND_NIGHT.width,
                    SpriteSheet.GameAtlas.BACKGROUND_NIGHT.height
                );
            if (score > 100) {
                background = sheet.getSprite(
                    SpriteSheet.GameAtlas.BACKGROUND_DAY.x,
                    SpriteSheet.GameAtlas.BACKGROUND_DAY.y,
                    SpriteSheet.GameAtlas.BACKGROUND_DAY.width,
                    SpriteSheet.GameAtlas.BACKGROUND_DAY.height
                );
            }
        }

            
            // ACTUALIZAR PIPES
            for (int i = 0; i < pipes.size(); i++) {

                Pipe pipe = pipes.get(i);
                pipe.update();

                if (pipe.collides(bird.getBounds())) {
                    state = GameStates.Game_Over;
                }

                if (pipe.isPassed(100)) {
                    score++;
                }

                if (pipe.isOffScreen()) {
                    pipes.remove(i);
                    i--;
                }
            }

            // SCROLL DEL SUELO
            groundX -= 2;
            backgroundX -= 1;

            if (groundX <= -336) {
                groundX = 0;
            }
            if (backgroundX <= -380) {
                backgroundX = 0;
            }
        }

        repaint();
    }

    private void resetGame() {
        bird.Reset();
        pipes.clear();
        score = 0;
        pipeSpawnTimer = 0;
        state = GameStates.Playing;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // BACKGROUND
        g.drawImage(background, backgroundX, 0, getWidth(), getHeight(), null);
        g.drawImage(background, backgroundX + 380, 0, getWidth(), getHeight(), null);

        // PIPES
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }

        // BIRD
        bird.draw(g);

        // GROUND
        g.drawImage(ground, groundX, 540, null);
        g.drawImage(ground, groundX + 336, 540, null);
        g.drawImage(ground, groundX + 670, 540, null);

        // SCORE
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));

        if (state == GameStates.Playing) {
            g.drawString("" + score, 190, 60);
        }

        if (state == GameStates.Menu) {
            g.drawString("Flappy Birdo", 120, 250);
            g.drawString("Presiona espacio", 80, 300);
            g.drawString("Presiona aqui", 115, 350);
            g.drawString("By Rodolfo", 135, 550);
        }

        if (state == GameStates.Game_Over) {
            g.drawImage(gameOverText, 110, 200, null);
            g.drawString("Score: " + score, 130, 300);
            g.drawString("Intentalo de nuevo", 80, 350);
            //g.drawImage(buttonOk, 160, 350, 160, 56, null);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            state = GameStates.Menu;
            setFocusable(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (null != state) switch (state) {
                case Menu -> state = GameStates.Playing;
                case Playing -> bird.jump();
                case Game_Over -> resetGame();
                default -> {}
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}
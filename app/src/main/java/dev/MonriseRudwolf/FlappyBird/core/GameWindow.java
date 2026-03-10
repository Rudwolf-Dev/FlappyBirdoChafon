/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.MonriseRudwolf.FlappyBird.core;
import javax.swing.JFrame;
/**
 *
 * @author Monrise
 */
public class GameWindow extends JFrame {
    public GameWindow() {
        setTitle("Flappy Birdo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(new GamePanel());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

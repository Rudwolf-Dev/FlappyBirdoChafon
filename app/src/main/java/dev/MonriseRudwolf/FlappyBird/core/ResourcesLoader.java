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
import java.io.IOException;
import javax.imageio.ImageIO;

public class ResourcesLoader {

    public static BufferedImage load(String path) {
        try {
            return ImageIO.read(
                ResourcesLoader.class.getResource(path)
            );
        } catch (IOException e) {
            return null;
        }
    }
}
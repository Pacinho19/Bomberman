package pl.pacinho.bomberman.logic;

import pl.pacinho.bomberman.model.CellType;
import pl.pacinho.bomberman.model.PlayerDirection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Images {

    private static ImageIcon loadGIF(String name){
        URL url = Images.class.getClassLoader().getResource("img/" + name+".gif");
        ImageIcon imageIcon = new ImageIcon(url);
        return imageIcon;
    }

    private static BufferedImage loadPNG(String name) {
        try {
            return ImageIO.read(Images.class.getClassLoader().getResource("img/" + name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedImage wallConstant = loadPNG("WallConstant.png");
    private static BufferedImage wallDestructible = loadPNG("WallDestructible.png");
    private static BufferedImage empty = loadPNG("Empty.png");


    public static BufferedImage getImage(CellType cellType){
        switch (cellType){
            case WALL:
                return wallConstant;
            case WALL_DESTRUCTIBLE:
                return wallDestructible;
            case EMPTY:
                return empty;
            case PLAYER:
                return playerRight;
        }
        return null;
    }

    private static BufferedImage playerRight = loadPNG("PlayerRight.png");
    private static BufferedImage playerLeft = loadPNG("PlayerLeft.png");
    private static BufferedImage playerDown = loadPNG("PlayerDown.png");
    private static BufferedImage playerUp = loadPNG("PlayerUp.png");

    public static BufferedImage getPlayerImageByDirection(PlayerDirection direction) {
        switch (direction) {
            case RIGHT:
                return playerRight;
            case LEFT:
                return playerLeft;
            case UP:
                return playerUp;
            case DOWN:
                return playerDown;
        }
        return null;
    }
}
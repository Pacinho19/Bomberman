package pl.pacinho.bomberman.logic;

import pl.pacinho.bomberman.model.CellType;
import pl.pacinho.bomberman.model.PlayerDirection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Images {

    private static final BufferedImage wallConstant = loadPNG("WallConstant.png");
    private static final BufferedImage wallDestructible = loadPNG("WallDestructible.png");
    private static final BufferedImage empty = loadPNG("Empty.png");
    private static final BufferedImage bomb = loadPNG("Bomb.png");
    private static final BufferedImage playerRight = loadPNG("PlayerRight.png");
    private static final BufferedImage playerLeft = loadPNG("PlayerLeft.png");
    private static final BufferedImage playerDown = loadPNG("PlayerDown.png");
    private static final BufferedImage playerUp = loadPNG("PlayerUp.png");
    private static final BufferedImage bombeExplosionCenter = loadPNG("BombExploCenter.png");
    private static final BufferedImage bombeExplosionHorizontal = loadPNG("BombExploHorizontal.png");
    private static final BufferedImage bombeExplosionVertical = loadPNG("BombExploVertical.png");
    private static final BufferedImage death = loadPNG("Death.png");

    private static ImageIcon loadGIF(String name) {
        URL url = Images.class.getClassLoader().getResource("img/" + name + ".gif");
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

    public static BufferedImage getImage(CellType cellType) {
        switch (cellType) {
            case WALL:
                return wallConstant;
            case WALL_DESTRUCTIBLE:
                return wallDestructible;
            case EMPTY:
                return empty;
            case PLAYER:
                return playerRight;
            case BOMB:
            return bomb;
            case BOMB_EXPLOSION_CENTER:
                return bombeExplosionCenter;
            case BOMB_EXPLOSION_HORIZONTAL:
                return bombeExplosionHorizontal;
            case BOMB_EXPLOSION_VERTICAL:
                return bombeExplosionVertical;
            case DEATH:
                return death;

        }
        return null;
    }

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
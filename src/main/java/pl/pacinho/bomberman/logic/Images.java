package pl.pacinho.bomberman.logic;

import pl.pacinho.bomberman.model.CellType;
import pl.pacinho.bomberman.model.PlayerEnemyDirection;

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
    private static final BufferedImage door = loadPNG("Door.png");
    private static final BufferedImage playerInDoor = loadPNG("PlayerInDoor.png");
    private static final BufferedImage playerOnBomb = loadPNG("PlayerOnBomb.png");
    private static final BufferedImage bombBonus =loadPNG("BombBonus.png");
    private static final ImageIcon enemyCoin = loadGIF("EnemyCoin");

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
            case BOMB_EXPLOSION_CENTER:
                return bombeExplosionCenter;
            case BOMB_EXPLOSION_HORIZONTAL:
                return bombeExplosionHorizontal;
            case BOMB_EXPLOSION_VERTICAL:
                return bombeExplosionVertical;
            case WALL_DESTRUCTIBLE:
                return wallDestructible;
            case WALL:
                return wallConstant;
            case EMPTY:
                return empty;
            case PLAYER:
                return playerRight;
            case BOMB:
                return bomb;
            case DEATH:
                return death;
            case DOOR:
                return door;
            case PLAYER_IN_DOOR:
                return playerInDoor;
            case PLAYER_ON_BOMB:
                return playerOnBomb;
            case BOMB_BONUS:
                return bombBonus;
        }
        return null;
    }

    public static BufferedImage getPlayerImageByDirection(PlayerEnemyDirection direction) {
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

    public static ImageIcon getImageGIF(CellType cellType) {
        switch (cellType) {
            case ENEMY_COIN:
                return enemyCoin;
        }
        return null;
    }
}
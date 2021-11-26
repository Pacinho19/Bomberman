package pl.pacinho.logic;

import pl.pacinho.model.CellType;

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

    public static BufferedImage getImage(CellType cellType){
        switch (cellType){
            case WALL:
                return loadPNG("WallConstant.png");
            case WALL_DESTROY:
                return loadPNG("WallDestroy.png");
            case EMPTY:
                return loadPNG("Empty.png");
        }
        return null;
    }
}
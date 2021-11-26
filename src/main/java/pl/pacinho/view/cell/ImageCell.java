package pl.pacinho.view.cell;

import pl.pacinho.logic.Images;
import pl.pacinho.model.CellType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageCell extends Cell {

    private BufferedImage image;

    public ImageCell(CellType cellType, int idx) {
        super(cellType,idx);
        image = Images.getImage(cellType);
        init();
    }

    private void init() {
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
                image.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH),
             0,0,this);
    }

}
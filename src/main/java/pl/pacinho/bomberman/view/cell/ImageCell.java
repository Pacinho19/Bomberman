package pl.pacinho.bomberman.view.cell;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.bomberman.logic.Images;
import pl.pacinho.bomberman.model.CellType;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageCell extends Cell {

    @Setter
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
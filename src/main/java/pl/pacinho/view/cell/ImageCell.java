package pl.pacinho.view.cell;

import pl.pacinho.logic.Images;
import pl.pacinho.model.CellType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageCell extends JPanel{

    private BufferedImage image;

    public ImageCell(CellType cellType) {
        this.setDoubleBuffered(true);
        image = Images.getImage(cellType);
        init();
    }

    private void init() {
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = getSize();

        g.fillRect(0, 0, d.width, d.height);
        g.drawImage(image,
                (int) (d.width * 0.1),
                (int) (d.height * 0.1),
                (int) (d.width - (d.width * 0.2)),
                (int) (d.height - (d.height * 0.2)),
                this);
    }

}
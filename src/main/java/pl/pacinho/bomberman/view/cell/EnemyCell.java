package pl.pacinho.bomberman.view.cell;

import lombok.Setter;
import pl.pacinho.bomberman.logic.Images;
import pl.pacinho.bomberman.model.CellType;
import pl.pacinho.bomberman.model.PlayerEnemyDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyCell extends Cell{

    private PlayerEnemyDirection direction;

    private ImageIcon image;

    public EnemyCell(CellType cellType, int idx) {
        super(cellType, idx);
        image = Images.getImageGIF(cellType);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();

        g.setColor(new Color(52,129,1));
        g.fillRect(0, 0, d.width, d.height);
        g.drawImage(image.getImage(),
                (int) (d.width * 0.1),
                (int) (d.height * 0.1),
                (int) (d.width - (d.width * 0.2)),
                (int) (d.height - (d.height * 0.2)),
                this);
//                (int) (d.width * 0.1),
//                (int) (d.height * 0.1),
//                (int) (d.width - (d.width * 0.2)),
//                (int) (d.height - (d.height * 0.2)),
//                this);
    }


}
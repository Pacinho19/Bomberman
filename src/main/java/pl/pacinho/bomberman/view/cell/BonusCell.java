package pl.pacinho.bomberman.view.cell;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.bomberman.logic.Images;
import pl.pacinho.bomberman.logic.MonsterMoveThread;
import pl.pacinho.bomberman.model.BonusType;
import pl.pacinho.bomberman.model.CellType;
import pl.pacinho.bomberman.model.EnemyType;
import pl.pacinho.bomberman.model.PlayerEnemyDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BonusCell extends Cell {

    @Getter
    @Setter
    private PlayerEnemyDirection direction;

    @Getter
    @Setter
    private MonsterMoveThread monsterMoveThread;

    private BufferedImage image;

    public BonusCell(CellType cellType, BonusType bonusType, int idx) {
        super(cellType, idx);
        image = Images.getBonusImage(bonusType);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
                image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH),
                0, 0, this);
    }

}
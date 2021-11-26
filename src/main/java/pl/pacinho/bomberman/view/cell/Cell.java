package pl.pacinho.bomberman.view.cell;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.bomberman.model.CellType;

import javax.swing.*;

public abstract class Cell extends JPanel {

    @Getter
    CellType cellType;

    public Cell(CellType cellType, int idx) {
        this.cellType = cellType;
        this.idx = idx;
        this.setDoubleBuffered(true);
    }

    @Getter
    @Setter
    int idx;
}
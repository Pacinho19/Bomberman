package pl.pacinho.bomberman.view.cell;

import pl.pacinho.bomberman.model.CellType;

public class EmptyCell extends ImageCell {
    public EmptyCell(int idx) {
        super(CellType.EMPTY, idx);
    }
}
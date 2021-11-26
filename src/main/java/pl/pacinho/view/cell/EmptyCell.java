package pl.pacinho.view.cell;

import pl.pacinho.model.CellType;

public class EmptyCell extends ImageCell {
    public EmptyCell(int idx) {
        super(CellType.EMPTY, idx);
    }
}
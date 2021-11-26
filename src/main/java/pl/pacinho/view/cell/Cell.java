package pl.pacinho.view.cell;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.model.CellType;

import javax.swing.*;

public abstract class Cell extends JPanel {

    @Getter
    CellType cellType;

    @Getter
    @Setter
    int idx;
}
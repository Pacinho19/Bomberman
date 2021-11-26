package pl.pacinho.bomberman.view.cell;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.bomberman.logic.Images;
import pl.pacinho.bomberman.model.CellType;
import pl.pacinho.bomberman.model.PlayerDirection;

public class PlayerCell extends ImageCell {

    @Getter
    private PlayerDirection direction;

    public PlayerCell(CellType cellType, int idx) {
        super(cellType, idx);
        setDirection(PlayerDirection.RIGHT);
    }

    public void setDirection(PlayerDirection direction) {
        this.direction = direction;
        this.setImage(Images.getPlayerImageByDirection(direction));
    }
}
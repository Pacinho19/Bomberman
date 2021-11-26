package pl.pacinho.bomberman.view.cell;

import lombok.Getter;
import pl.pacinho.bomberman.logic.Images;
import pl.pacinho.bomberman.model.CellType;
import pl.pacinho.bomberman.model.PlayerEnemyDirection;

public class PlayerCell extends ImageCell {

    @Getter
    private PlayerEnemyDirection direction;

    @Getter
    private int bombCount = 1;

    @Getter
    private int bombRange= 1;

    public PlayerCell(CellType cellType, int idx) {
        super(cellType, idx);
        setDirection(PlayerEnemyDirection.RIGHT);
    }

    public void setDirection(PlayerEnemyDirection direction) {
        this.direction = direction;
        this.setImage(Images.getPlayerImageByDirection(direction));
    }

    public void addBomb() {
        bombCount++;
    }
    public void incrementBombRange() {
        bombRange++;
    }

}
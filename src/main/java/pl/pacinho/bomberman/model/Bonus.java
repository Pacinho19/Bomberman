package pl.pacinho.bomberman.model;

import lombok.*;

@Getter
@Setter
public class Bonus {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private CellType cellType;
    private BonusType bonusType;
    private int idx;

    public Bonus() {
        this.cellType=CellType.BONUS;
    }

    public Bonus(BonusType bonusType) {
        this.bonusType = bonusType;
    }
}
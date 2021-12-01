package pl.pacinho.bomberman.logic;

import lombok.Getter;
import pl.pacinho.bomberman.model.Bonus;
import pl.pacinho.bomberman.model.BonusType;
import pl.pacinho.bomberman.model.EnemyType;
import pl.pacinho.bomberman.model.LevelData;
import pl.pacinho.bomberman.view.cell.PlayerCell;

import java.util.HashMap;

public class Levels {

    @Getter
    private static HashMap<Integer, LevelData> levelsMap = new HashMap<Integer, LevelData>() {
        {
            put(1, new LevelData(
                    new Bonus(BonusType.BOMB),
                    new HashMap<EnemyType, Integer>() {
                        {
                            put(EnemyType.BALON, 1);
                        }
                    }));
            put(2, new LevelData(
                    new Bonus(BonusType.BOMB_RANGE),
                    new HashMap<EnemyType, Integer>() {
                        {
                            put(EnemyType.BALON, 2);
                            put(EnemyType.COIN, 2);
                        }
                    }));
        }
    };

}

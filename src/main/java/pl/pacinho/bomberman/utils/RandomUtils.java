package pl.pacinho.bomberman.utils;

import pl.pacinho.bomberman.model.CellType;
import pl.pacinho.bomberman.model.PlayerEnemyDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    public static int getInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static PlayerEnemyDirection getEnemyDirection() {
        return Arrays.asList(PlayerEnemyDirection.values())
                .get(getInt(0, PlayerEnemyDirection.values().length));
    }

    private static final List<CellType> bonuses = new ArrayList<CellType>(){
        {
            add(CellType.BOMB_BONUS);
            add(CellType.BOMB_BONUS);
        }
    };
    public static CellType getBonus(){
        return bonuses.get(getInt(0, bonuses.size()-1));
    }
}
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
}
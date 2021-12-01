package pl.pacinho.bomberman.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LevelData {

    private Bonus bonus;
    private HashMap<EnemyType, Integer> enemyCount;
}

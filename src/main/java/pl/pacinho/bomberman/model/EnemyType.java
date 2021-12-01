package pl.pacinho.bomberman.model;

import lombok.Getter;

public enum EnemyType {

    COIN(150),
    BALON(200);

    @Getter
    private int moveDelay;

    EnemyType(int moveDelay) {
        this.moveDelay = moveDelay;
    }
}

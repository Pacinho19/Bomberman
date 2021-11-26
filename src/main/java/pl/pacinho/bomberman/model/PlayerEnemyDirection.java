package pl.pacinho.bomberman.model;

import java.awt.event.KeyEvent;
import java.util.Arrays;

public enum PlayerEnemyDirection {

    NONE(-1),
    UP(KeyEvent.VK_UP),
    DOWN(KeyEvent.VK_DOWN),
    LEFT(KeyEvent.VK_LEFT),
    RIGHT(KeyEvent.VK_RIGHT);

    private int keyEvent;

    PlayerEnemyDirection(int keyEvent) {
        this.keyEvent = keyEvent;
    }

    public static PlayerEnemyDirection findByKey(KeyEvent e) {
        return Arrays.asList(values())
                .stream()
                .filter(d -> d.keyEvent == e.getKeyCode())
                .findFirst()
                .orElse(NONE);
    }

}
package pl.pacinho.bomberman.logic;

import pl.pacinho.bomberman.controller.BoardController;
import pl.pacinho.bomberman.model.CellType;
import pl.pacinho.bomberman.model.PlayerEnemyDirection;
import pl.pacinho.bomberman.utils.RandomUtils;
import pl.pacinho.bomberman.view.cell.Cell;
import pl.pacinho.bomberman.view.cell.EmptyCell;
import pl.pacinho.bomberman.view.cell.EnemyCell;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MonsterMoveThread extends Thread implements ActionListener {


    private BoardController controller;
    private EnemyCell enemyCell;
    private Timer timer;

    public MonsterMoveThread(BoardController controller, EnemyCell enemyCell) {
        this.controller = controller;
        this.enemyCell = enemyCell;
        timer = new Timer(150, this);
        enemyCell.setDirection(PlayerEnemyDirection.RIGHT);
    }

    @Override
    public void run() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int nextPosition = 0;
        if (enemyCell.getDirection() == PlayerEnemyDirection.RIGHT) {
            nextPosition = enemyCell.getIdx() + 1;
        } else if (enemyCell.getDirection() == PlayerEnemyDirection.LEFT) {
            nextPosition = enemyCell.getIdx() - 1;
        } else if (enemyCell.getDirection() == PlayerEnemyDirection.UP) {
            nextPosition = enemyCell.getIdx() - controller.getBoardSize();
        } else if (enemyCell.getDirection() == PlayerEnemyDirection.DOWN) {
            nextPosition = enemyCell.getIdx() + controller.getBoardSize();
        }
        setEnemyDirection(nextPosition);
        controller.refresh();
    }

    private void setEnemyDirection(int nextIdx) {

        Cell cell = Arrays.stream(controller.getGameBoard().getComponents())
                .map(c -> (Cell) c)
                .filter(c -> c.getIdx() == nextIdx)
                .findFirst()
                .orElse(null);

        if (cell == null) {
            PlayerEnemyDirection direction = RandomUtils.getEnemyDirection();
            enemyCell.setDirection(direction);
            return;
        }


        Cell cell2 = Arrays.stream(controller.getGameBoard().getComponents())
                .map(c -> (Cell) c)
                .filter(c -> c.getIdx() == enemyCell.getIdx())
                .findFirst()
                .orElse(null);

        if (cell2 != null
                && (cell2.getCellType() == CellType.BOMB_EXPLOSION_VERTICAL
                || cell2.getCellType() == CellType.BOMB_EXPLOSION_HORIZONTAL)){
            controller.getEnemies().remove(enemyCell);
            timer.stop();
            Thread.interrupted();
            return;
        }


        if (cell.getCellType() == CellType.BOMB_EXPLOSION_VERTICAL
                || cell.getCellType() == CellType.BOMB_EXPLOSION_HORIZONTAL) {
            controller.getGameBoard().remove(enemyCell.getIdx());
            controller.getGameBoard().add(new EmptyCell(enemyCell.getIdx()), enemyCell.getIdx());
            controller.getEnemies().remove(enemyCell);
            timer.stop();
            Thread.interrupted();
            return;
        }

        if (cell.getCellType() != CellType.WALL
                && cell.getCellType() != CellType.WALL_DESTRUCTIBLE
                && cell.getCellType() != CellType.BOMB
                && cell.getCellType() != CellType.BOMB_EXPLOSION_VERTICAL
                && cell.getCellType() != CellType.BOMB_EXPLOSION_HORIZONTAL) {
            controller.getGameBoard().remove(enemyCell.getIdx());
            controller.getGameBoard().add(new EmptyCell(enemyCell.getIdx()), enemyCell.getIdx());
            controller.getGameBoard().remove(cell);
            controller.getGameBoard().add(enemyCell, nextIdx);
            enemyCell.setIdx(nextIdx);
        }

        if (cell.getCellType() == CellType.PLAYER) {
            JOptionPane.showMessageDialog(controller.getGameBoard(), "Game Over !");
            controller.setPlayerCell(null);
            Thread.interrupted();
            timer.stop();
            return;
        }

        PlayerEnemyDirection direction = RandomUtils.getEnemyDirection();
        enemyCell.setDirection(direction);
    }
}
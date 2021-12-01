package pl.pacinho.bomberman.logic;

import pl.pacinho.bomberman.controller.BoardController;
import pl.pacinho.bomberman.model.CellType;
import pl.pacinho.bomberman.model.ExplosionDirection;
import pl.pacinho.bomberman.view.cell.BonusCell;
import pl.pacinho.bomberman.view.cell.Cell;
import pl.pacinho.bomberman.view.cell.EnemyCell;
import pl.pacinho.bomberman.view.cell.ImageCell;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BombExplosionThread extends Thread {

    private BoardController boardController;
    private int idx;
    private JPanel gameBoard;
    private List<Cell> explosionCellsIdx;
    private boolean playerKill = false;

    private boolean left = true;
    private boolean right = true;
    private boolean up = true;
    private boolean down = true;

    public BombExplosionThread(BoardController boardController, int idx) {
        this.boardController = boardController;
        this.idx = idx;
        explosionCellsIdx = new ArrayList<>();
        gameBoard = boardController.getGameBoard();
        this.setName("Bomb " + idx);
    }

    @Override
    public void run() {

        if (!boardController.getPlayerCell().isBombDetonate()) {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
            }
        }

        Component[] components = gameBoard.getComponents();
        Cell cell2 = (Cell) components[idx];
        if (cell2.getCellType().equals(CellType.PLAYER)
                || cell2.getCellType().equals(CellType.PLAYER_ON_BOMB)) {
            playerKill = true;
        }

        gameBoard.remove(idx);
        Cell center = new ImageCell(CellType.BOMB_EXPLOSION_CENTER, idx);
        gameBoard.add(center, idx);
        boardController.removeBomb(idx);

        explosionCellsIdx.add(center);

        for (int i = 0; i < boardController.getPlayerCell().getBombRange(); i++) {
            if (right) {
                int idxRight = idx + (i + 1);
                addExplosionCell(CellType.BOMB_EXPLOSION_HORIZONTAL, idxRight, ExplosionDirection.RIGHT);
            }

            if (left) {
                int idxLeft = idx - (i + 1);
                addExplosionCell(CellType.BOMB_EXPLOSION_HORIZONTAL, idxLeft, ExplosionDirection.LEFT);
            }

            if (up) {
                int idxUp = idx - ((i + 1) * boardController.getBoardSize());
                addExplosionCell(CellType.BOMB_EXPLOSION_VERTICAL, idxUp, ExplosionDirection.UP);
            }

            if (down) {
                int idxDown = idx + ((i + 1) * boardController.getBoardSize());
                addExplosionCell(CellType.BOMB_EXPLOSION_VERTICAL, idxDown, ExplosionDirection.DOWN);
            }
        }
        boardController.refresh();

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
        }

        for (Cell cell : explosionCellsIdx) {
            if (cell.getIdx() == boardController.getFinishDoorIdx()
                    || (boardController.getBonus() != null && cell.getIdx() == boardController.getBonus().getIdx())) {
                continue;
            }
            gameBoard.remove(cell);
            gameBoard.add(new ImageCell(CellType.EMPTY, cell.getIdx()), cell.getIdx());
        }


        if (playerKill) {
            JOptionPane.showMessageDialog(boardController.getGameBoard(), "Game Over !");
            boardController.setPlayerCell(null);
        }

        boardController.refresh();
    }

    private void addExplosionCell(CellType cellType, int idx, ExplosionDirection explosionDirection) {
        Component[] components = gameBoard.getComponents();
        Cell nextCell = (Cell) components[idx];
        if (nextCell.getCellType() == CellType.WALL
                || nextCell.getCellType() == CellType.BOMB
                || nextCell.getCellType() == CellType.WALL_DESTRUCTIBLE
                || nextCell.getCellType() == CellType.ENEMY
                || nextCell.getCellType() == CellType.BOMB_EXPLOSION_HORIZONTAL
                || nextCell.getCellType() == CellType.BOMB_EXPLOSION_VERTICAL
                || nextCell.getCellType() == CellType.BOMB_EXPLOSION_CENTER) {
            switch (explosionDirection) {
                case UP:
                    up = false;
                    break;
                case DOWN:
                    down = false;
                    break;
                case LEFT:
                    left = false;
                    break;
                case RIGHT:
                    right = false;
                    break;
            }

            if (nextCell.getCellType() == CellType.WALL
                    || nextCell.getCellType() == CellType.BOMB
                    || nextCell.getCellType() == CellType.BOMB_EXPLOSION_HORIZONTAL
                    || nextCell.getCellType() == CellType.BOMB_EXPLOSION_VERTICAL
                    || nextCell.getCellType() == CellType.BOMB_EXPLOSION_CENTER) {
                return;
            }
        }

        Cell imageCell = new ImageCell(cellType, idx);
        if (nextCell.getCellType() == CellType.PLAYER) {
            playerKill = true;
            imageCell = new ImageCell(CellType.DEATH, idx);
        } else if (idx == boardController.getFinishDoorIdx()) {
            imageCell = new ImageCell(CellType.DOOR, idx);
        } else if (boardController.getBonus() != null && idx == boardController.getBonus().getIdx()) {
            imageCell = new BonusCell(CellType.BONUS, boardController.getBonus().getBonusType(), idx);
        } else if (nextCell.getCellType() == CellType.ENEMY) {
            EnemyCell enemyCell = (EnemyCell) nextCell;
            //enemyCell.getMonsterMoveThread().interrupt();
            boardController.getEnemies().remove(nextCell);
        }
        gameBoard.remove(idx);
        gameBoard.add(imageCell, idx);
        explosionCellsIdx.add(imageCell);
    }

}
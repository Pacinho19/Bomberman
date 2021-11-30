package pl.pacinho.bomberman.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lombok.Getter;
import lombok.Setter;
import pl.pacinho.bomberman.logic.BombExplosionThread;
import pl.pacinho.bomberman.logic.Images;
import pl.pacinho.bomberman.logic.MonsterMoveThread;
import pl.pacinho.bomberman.model.Bonus;
import pl.pacinho.bomberman.model.CellType;
import pl.pacinho.bomberman.model.PlayerEnemyDirection;
import pl.pacinho.bomberman.utils.RandomUtils;
import pl.pacinho.bomberman.view.Board;
import pl.pacinho.bomberman.view.cell.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardController {

    private final int enemiesCount = 1;
    private Board board;
    @Getter
    @Setter
    private PlayerCell playerCell;
    @Getter
    private JPanel gameBoard;
    private List<Integer> bombsIdx;
    @Getter
    private List<EnemyCell> enemies;
    @Getter
    private int boardSize;
    @Getter
    private int finishDoorIdx;

    @Getter
    private Bonus bonus;


    public BoardController(Board board) {
        this.board = board;
        bombsIdx = new ArrayList<>();
        enemies = new ArrayList<>();
        gameBoard = board.getBoardPanel();
        boardSize = board.getBoardSize();
    }

    public void createGameBoard() {
        boolean wallSkip = true;
        for (int row = 0; row < boardSize; row++) {
            boolean nextWall = true;
            wallSkip = !wallSkip;
            for (int col = 0; col < boardSize; col++) {
                int idx = row * boardSize + col;
                if (row == 0
                        || row == boardSize - 1
                        || col == 0
                        || col == boardSize - 1) {
                    board.getBoardPanel().add(new ImageCell(CellType.WALL, idx));
                    continue;
                }

                if (!wallSkip
                        && row > 1 && row < boardSize - 1
                        && col > 1 && col < boardSize - 1) {
                    gameBoard.add(nextWall ? new ImageCell(CellType.WALL, idx) : new EmptyCell(idx));
                    nextWall = !nextWall;
                    continue;
                } else {
                    gameBoard.add(new EmptyCell(idx));
                }
            }
        }

        addPlayer();
        addDestructibleWalls();
        initFinishDoorIndex();
        addBonus();
        IntStream.range(0, enemiesCount).forEach(i -> addEnemies());
        refresh();
    }

    private void addBonus() {
        List<Cell> cells = getCells()
                .stream()
                .filter(c -> c.getCellType() == CellType.WALL_DESTRUCTIBLE)
                .collect(Collectors.toList());


//        Cell cell = cells.get(RandomUtils.getInt(0, cells.size()));
        Cell cell = cells.get(RandomUtils.getInt(1, 2));
        cell.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        bonus = new Bonus(RandomUtils.getBonus(), cell.getIdx());
    }

    private void addEnemies() {
        List<Cell> cells = getCells()
                .stream()
                .filter(c -> c.getCellType() == CellType.EMPTY)
                .collect(Collectors.toList());

        Cell cell = cells.get(RandomUtils.getInt(0, cells.size()));
        gameBoard.remove(cell);
        EnemyCell enemyCell = new EnemyCell(CellType.ENEMY_COIN, cell.getIdx());
        gameBoard.add(enemyCell, cell.getIdx());

        enemies.add(enemyCell);
        MonsterMoveThread monsterMoveThread = new MonsterMoveThread(this, enemyCell);
        monsterMoveThread.start();
        enemyCell.setMonsterMoveThread(monsterMoveThread);
    }

    private void initFinishDoorIndex() {
        List<Cell> cells = getCells()
                .stream()
                .filter(c -> c.getCellType() == CellType.WALL_DESTRUCTIBLE)
                .collect(Collectors.toList());

//        Cell cell = cells.get(RandomUtils.getInt(0, cells.size()));
        Cell cell = cells.get(RandomUtils.getInt(0, 1));
        cell.setBorder(BorderFactory.createLineBorder(Color.RED));
        finishDoorIdx = cell.getIdx();
        System.out.println(finishDoorIdx);
    }

    private void addDestructibleWalls() {
        List<Cell> cells = getCells()
                .stream()
                .filter(c -> c instanceof EmptyCell
                        && c.getIdx() != playerCell.getIdx() + 1
                        && c.getIdx() != playerCell.getIdx() + boardSize)
                .collect(Collectors.toList());

        int maxDestructibleCells = cells.size() / 2;

        for (int i = 0; i < maxDestructibleCells; i++) {
            Cell cell = takeCell(RandomUtils.getInt(0, cells.size()), cells);
            gameBoard.remove(cell.getIdx());
            gameBoard.add(new ImageCell(CellType.WALL_DESTRUCTIBLE, cell.getIdx()), cell.getIdx());
        }


    }

    private Cell takeCell(int idx, List<Cell> cells) {
        Cell cell = cells.get(idx);
        cells.remove(idx);
        return cell;
    }

    private List<Cell> getCells() {
        return Arrays.stream(gameBoard.getComponents())
                .map(c -> (Cell) c)
                .collect(Collectors.toList());
    }

    private Cell getCellAt(int idx) {
        return getCells()
                .stream()
                .filter(c -> c.getIdx() == idx)
                .findFirst()
                .orElse(null);
    }


    private void addPlayer() {
        int pos = boardSize + 1;
        gameBoard.remove(pos);
        playerCell = new PlayerCell(CellType.PLAYER, pos);
        gameBoard.add(playerCell, pos);
    }

    public void keyPressed(KeyEvent e) {
        if (playerCell == null) {
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT
                || e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerCell.setDirection(PlayerEnemyDirection.findByKey(e));
            playerMove();
        } else if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            Cell cellAt = getCellAt(playerCell.getIdx());
            if (cellAt == null) {
                return;
            }
            if (cellAt.getCellType() == CellType.PLAYER) {
                setBomb();
            }
        }
        refresh();
    }

    private void setBomb() {
        if (bombsIdx.size() == playerCell.getBombCount()) {
            return;
        }
        bombsIdx.add(playerCell.getIdx());
        new BombExplosionThread(this, playerCell.getIdx())
                .start();

        gameBoard.remove(playerCell.getIdx());
        gameBoard.add(new ImageCell(CellType.PLAYER_ON_BOMB, playerCell.getIdx()), playerCell.getIdx());
    }

    public void playerMove() {
        if (playerCell.getDirection() == PlayerEnemyDirection.RIGHT) {
            int nextPosition = playerCell.getIdx() + 1;
            movePlayerCell(nextPosition);
        } else if (playerCell.getDirection() == PlayerEnemyDirection.LEFT) {
            int nextPosition = playerCell.getIdx() - 1;
            movePlayerCell(nextPosition);
        } else if (playerCell.getDirection() == PlayerEnemyDirection.DOWN) {
            int nextPosition = playerCell.getIdx() + boardSize;
            movePlayerCell(nextPosition);
        } else if (playerCell.getDirection() == PlayerEnemyDirection.UP) {
            int nextPosition = playerCell.getIdx() - boardSize;
            movePlayerCell(nextPosition);
        }
    }

    private void movePlayerCell(int nextPosition) {
        Cell nextCell = (Cell) gameBoard.getComponents()[nextPosition];

        if (nextCell.getCellType() == CellType.WALL
                || nextCell.getCellType() == CellType.WALL_DESTRUCTIBLE) {
            return;
        }

        if (nextCell.getCellType() == CellType.BOMB_EXPLOSION_CENTER
                || nextCell.getCellType() == CellType.BOMB_EXPLOSION_HORIZONTAL
                || nextCell.getCellType() == CellType.BOMB_EXPLOSION_VERTICAL) {
            gameBoard.remove(playerCell.getIdx());
            gameBoard.add(new ImageCell(CellType.DEATH, playerCell.getIdx()), playerCell.getIdx());
            JOptionPane.showMessageDialog(board, "Game Over!");
            playerCell = null;
            return;
        } else if (nextCell.getCellType() == CellType.DOOR && enemies.isEmpty()) {
            gameBoard.remove(playerCell.getIdx());
            gameBoard.add(new EmptyCell(playerCell.getIdx()), playerCell.getIdx());
            gameBoard.remove(nextPosition);
            gameBoard.add(new ImageCell(CellType.PLAYER_IN_DOOR, nextPosition), nextPosition);
            refresh();
            JOptionPane.showMessageDialog(board, "Level Complete!");
            playerCell = null;
            return;
        } else if (nextCell.getCellType() == CellType.DOOR && !enemies.isEmpty()) {
            gameBoard.remove(playerCell.getIdx());
            if (bombsIdx.contains(playerCell.getIdx())) {
                gameBoard.add(new ImageCell(CellType.BOMB, playerCell.getIdx()), playerCell.getIdx());
            } else {
                gameBoard.add(new EmptyCell(playerCell.getIdx()), playerCell.getIdx());
            }
            gameBoard.remove(nextPosition);
            gameBoard.add(new ImageCell(CellType.PLAYER_IN_DOOR, nextPosition), nextPosition);
            playerCell.setIdx(nextPosition);
            return;
        } else if (playerCell.getIdx() == finishDoorIdx) {
            gameBoard.remove(playerCell.getIdx());
            gameBoard.add(new ImageCell(CellType.DOOR, playerCell.getIdx()), playerCell.getIdx());
            gameBoard.remove(nextPosition);
            gameBoard.add(playerCell, nextPosition);
            playerCell.setIdx(nextPosition);
            return;
        } else if (nextCell.getCellType() == CellType.BOMB_BONUS) {
            playerCell.addBomb();
            bonus = null;
        }

        if (nextCell.getCellType() != CellType.EMPTY
                && nextCell.getCellType() != CellType.BOMB_BONUS) {
            return;
        }

        gameBoard.remove(playerCell.getIdx());
        if (bombsIdx.contains(playerCell.getIdx())) {
            gameBoard.add(new ImageCell(CellType.BOMB, playerCell.getIdx()), playerCell.getIdx());
        } else {
            gameBoard.add(new EmptyCell(playerCell.getIdx()), playerCell.getIdx());
        }
        gameBoard.remove(nextPosition);
        playerCell.setIdx(nextPosition);
        gameBoard.add(playerCell, nextPosition);
    }

    public void refresh() {
        board.repaint();
        board.revalidate();
        board.validate();
    }

    public void removeBomb(int idx) {
        int i = bombsIdx.indexOf(idx);
        bombsIdx.remove(i);
    }
}
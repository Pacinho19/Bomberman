package pl.pacinho.bomberman.controller;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.bomberman.logic.BombExplosionThread;
import pl.pacinho.bomberman.model.CellType;
import pl.pacinho.bomberman.model.PlayerDirection;
import pl.pacinho.bomberman.utils.RandomUtils;
import pl.pacinho.bomberman.view.Board;
import pl.pacinho.bomberman.view.cell.Cell;
import pl.pacinho.bomberman.view.cell.EmptyCell;
import pl.pacinho.bomberman.view.cell.ImageCell;
import pl.pacinho.bomberman.view.cell.PlayerCell;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BoardController {

    private Board board;

    @Getter
    @Setter
    private PlayerCell playerCell;
    @Getter
    private JPanel gameBoard;

    private List<Integer> bombsIdx;

    @Getter
    private int boardSize;

    public BoardController(Board board) {
        this.board = board;
        bombsIdx = new ArrayList<>();
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
        refresh();
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


    private void addPlayer() {
        int pos = boardSize + 1;
        gameBoard.remove(pos);
        playerCell = new PlayerCell(CellType.PLAYER, pos);
        gameBoard.add(playerCell, pos);
    }

    public void keyPressed(KeyEvent e) {
        if(playerCell==null){
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT
                || e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerCell.setDirection(PlayerDirection.findByKey(e));
            playerMove();
        } else if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            setBomb();
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
    }

    public void playerMove() {
        if (playerCell.getDirection() == PlayerDirection.RIGHT) {
            int nextPosition = playerCell.getIdx() + 1;
            movePlayerCell(nextPosition);
        } else if (playerCell.getDirection() == PlayerDirection.LEFT) {
            int nextPosition = playerCell.getIdx() - 1;
            movePlayerCell(nextPosition);
        } else if (playerCell.getDirection() == PlayerDirection.DOWN) {
            int nextPosition = playerCell.getIdx() + boardSize;
            movePlayerCell(nextPosition);
        } else if (playerCell.getDirection() == PlayerDirection.UP) {
            int nextPosition = playerCell.getIdx() - boardSize;
            movePlayerCell(nextPosition);
        }
    }

    private void movePlayerCell(int nextPosition) {
        Cell nextCell = (Cell) gameBoard.getComponents()[nextPosition];

        if(nextCell.getCellType() == CellType.BOMB_EXPLOSION_CENTER
        || nextCell.getCellType() == CellType.BOMB_EXPLOSION_HORIZONTAL
        || nextCell.getCellType() == CellType.BOMB_EXPLOSION_VERTICAL){
            gameBoard.remove(playerCell.getIdx());
            gameBoard.add(new ImageCell(CellType.DEATH, playerCell.getIdx()), playerCell.getIdx());
            JOptionPane.showMessageDialog(board, "Game Over !");
            playerCell = null;
        }

        if (nextCell.getCellType() != CellType.EMPTY) {
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
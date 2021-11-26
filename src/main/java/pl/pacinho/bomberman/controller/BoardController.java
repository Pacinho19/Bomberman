package pl.pacinho.bomberman.controller;

import pl.pacinho.bomberman.model.CellType;
import pl.pacinho.bomberman.model.PlayerDirection;
import pl.pacinho.bomberman.utils.RandomUtils;
import pl.pacinho.bomberman.view.Board;
import pl.pacinho.bomberman.view.cell.Cell;
import pl.pacinho.bomberman.view.cell.EmptyCell;
import pl.pacinho.bomberman.view.cell.ImageCell;
import pl.pacinho.bomberman.view.cell.PlayerCell;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BoardController {

    private Board board;

    private PlayerCell playerCell;

    public BoardController(Board board) {
        this.board = board;
    }

    public void createGameBoard() {
        int boardSize = board.getBoardSize();
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
                    board.getBoardPanel().add(nextWall ? new ImageCell(CellType.WALL, idx) : new EmptyCell(idx));
                    nextWall = !nextWall;
                    continue;
                } else {
                    board.getBoardPanel().add(new EmptyCell(idx));
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
                        && c.getIdx() != playerCell.getIdx() + board.getBoardSize())
                .collect(Collectors.toList());

        int maxDestructibleCells = cells.size() / 2;

        for (int i = 0; i < maxDestructibleCells; i++) {
            Cell cell = takeCell(RandomUtils.getInt(0, cells.size()), cells);
            board.getBoardPanel().remove(cell.getIdx());
            board.getBoardPanel().add(new ImageCell(CellType.WALL_DESTRUCTIBLE, cell.getIdx()), cell.getIdx());
        }


    }

    private Cell takeCell(int idx, List<Cell> cells) {
        Cell cell = cells.get(idx);
        cells.remove(idx);
        return cell;
    }

    private List<Cell> getCells() {
        return Arrays.stream(board.getBoardPanel().getComponents())
                .map(c -> (Cell) c)
                .collect(Collectors.toList());
    }


    private void addPlayer() {
        int pos = board.getBoardSize() + 1;
        board.getBoardPanel().remove(pos);
        playerCell = new PlayerCell(CellType.PLAYER, pos);
        board.getBoardPanel().add(playerCell, pos);
    }

    private void refresh() {
        board.repaint();
        board.revalidate();
        board.validate();
    }


    public void keyPressed(KeyEvent e) {
         if (e.getKeyCode() == KeyEvent.VK_RIGHT
                || e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerCell.setDirection(PlayerDirection.findByKey(e));
        }
    }
}
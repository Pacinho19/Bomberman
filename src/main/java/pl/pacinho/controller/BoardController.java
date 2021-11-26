package pl.pacinho.controller;

import pl.pacinho.model.CellType;
import pl.pacinho.view.Board;
import pl.pacinho.view.cell.EmptyCell;
import pl.pacinho.view.cell.ImageCell;

public class BoardController {

    private Board board;

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
        refresh();
    }

    private void refresh() {
        board.repaint();
        board.revalidate();
        board.validate();
    }


}
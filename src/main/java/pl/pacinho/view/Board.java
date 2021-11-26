package pl.pacinho.view;

import lombok.Getter;
import pl.pacinho.controller.BoardController;

import javax.swing.*;
import java.awt.*;

public class Board extends JFrame {

    @Getter
    private JPanel boardPanel;
    private BoardController boardController;

    @Getter
    private int boardSize = 21;

    public Board() {

        this.setTitle("Bomberman");
        this.setSize(1000, 1000);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        init();
        initView();

        boardController = new BoardController(this);
        boardController.createGameBoard();
    }

    private void init() {
        boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
    }

    private void initView() {
        Container main = this.getContentPane();
        main.setLayout(new BorderLayout());
        main.add(boardPanel, BorderLayout.CENTER);
    }
}
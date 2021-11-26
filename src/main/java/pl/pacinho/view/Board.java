package pl.pacinho.view;

import lombok.Getter;
import pl.pacinho.controller.BoardController;

import javax.swing.*;
import java.awt.*;

public class Board extends JFrame {

    private JPanel boardPanel;
    private BoardController boardController;

    @Getter
    private int size = 20;

    public Board() {

        this.setTitle("Pacman");
        this.setSize(1000, 1000);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        init();
        initView();

        boardController = new BoardController(this);
        boardController.createGameBoard();
    }

    private void init() {
        boardPanel = new JPanel(new GridLayout(size, size));
    }

    private void initView() {
        Container main = this.getContentPane();
        main.setLayout(new BorderLayout());

        main.add(boardPanel, BorderLayout.CENTER);
    }
}
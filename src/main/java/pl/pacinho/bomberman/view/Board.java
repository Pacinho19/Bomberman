package pl.pacinho.bomberman.view;

import lombok.Getter;
import pl.pacinho.bomberman.controller.BoardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JFrame {

    @Getter
    private JPanel boardPanel;
    private BoardController boardController;

    @Getter
    private int boardSize = 31;

    private Board self = this;
    public Board() {

        this.setTitle("Bomberman");
        this.setSize(1000, 1000);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        init();
        initView();
        initActions();

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

    private void initActions() {
        self.setFocusable(true);
        self.requestFocusInWindow();
        self.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boardController.keyPressed(e);
            }
        });

    }
}
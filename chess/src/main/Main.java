package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {


        Board board = new Board();

        Game game = new Game(board);
        board.addMouseListener(game);

        boolean isWhiteTurn = true;
        JFrame frame = new JFrame();
        frame.setLayout(new GridBagLayout());

        frame.setMinimumSize(new Dimension(950, 950));

        frame.add(board);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
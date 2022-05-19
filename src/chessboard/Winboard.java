package chessboard;

import chess.ChessColor;
import controller.Countdown;
import util.StepSaver;

import javax.swing.*;
import java.awt.*;

public class Winboard extends JFrame {
    private final int width;
    private final int heigth;
    private ChessGameFrame chessGameFrame;
    static JLabel WinText;

    public Winboard(int width, int heigth, ChessGameFrame chessGameFrame) {
        this.width = width;
        this.heigth = heigth;
        setSize(width,heigth);
        setLocationRelativeTo(null);
        setVisible(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.chessGameFrame=chessGameFrame;
        addLabel();
        addRestart();
        addExit();
    }

    private void addRestart() {
        JButton button = new JButton("Restart");
        button.addActionListener(e -> restart());
        button.setLocation(width/2-50, heigth / 10 + 100);
        button.setSize(width/5, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        setLayout(null);
        add(button);
    }

    private void restart() {
        if (chessGameFrame.getChessboard()!= null) {
            chessGameFrame.remove(chessGameFrame.getChessboard());
        }
        StepSaver.initiate();
        chessGameFrame.addChessboard();
        chessGameFrame.checkLabel.setVisible(false);
        chessGameFrame.setStatusLabel(ChessColor.WHITE);
        repaint();
        Countdown.restart();
        chessGameFrame.setVisible(true);
        chessGameFrame.winboard.setVisible(false);
    }

    private void addLabel() {
        WinText = new JLabel("WHITE WIN");
        WinText.setLocation(width/2-50, heigth/10+50);
        WinText.setSize(width/5, 40);
        WinText.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(WinText);
    }

    public static void setWinText(ChessColor color){
        if (color==ChessColor.WHITE){
            WinText.setText("Black Win");
        }
        else if (color==ChessColor.BLACK){
            WinText.setText("White Win");
        }
    }

    private void addExit(){
        JButton button = new JButton("Exit");
            button.addActionListener(e -> Runtime.getRuntime().halt(0));
        button.setLocation(width/2-50, heigth / 10 + 150);
        button.setSize(width/5, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        setLayout(null);
        add(button);
    }
}

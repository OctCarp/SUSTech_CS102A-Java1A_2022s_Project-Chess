package chessboard;

import chess.ChessColor;
import chess.ChessComponent;
import controller.GameController;
import util.StepSaver;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

import static chess.ChessColor.BLACK;

public class ReplayFrame extends JFrame {

    private final int WIDTH;
    private final int HEIGTH;
    private ReplayBoard replayBoard;
    protected JButton previousBtn;
    protected JButton nextBtn;
    protected JLabel playerLabel;
    public final int REPLAY_BOARD_SIZE;

    public ReplayFrame(int width, int height) {
        setTitle("Replay"); //设置标题

        this.WIDTH = width;
        this.HEIGTH = height;
        this.REPLAY_BOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setLayout(null);
        addPrevious();
        addNext();
        addPlayer();
    }

    public void setReplayList() {
        addReplayBoard();
        replayBoard.setReplayBoardList();
        previousListener();
        nextListener();
    }
    private void addPlayer() {
        playerLabel = new JLabel("WHITE");
        playerLabel.setLocation(HEIGTH, HEIGTH / 10 );
        playerLabel.setSize(150, 60);
        playerLabel.setFont(new Font("Rockwell", Font.BOLD, 12));
        add(playerLabel);
    }

    private void addPrevious() {
        previousBtn = new JButton("Previous");
        previousBtn.setLocation(HEIGTH, HEIGTH / 10 + 110);
        previousBtn.setSize(150, 60);
        previousBtn.setFont(new Font("Rockwell", Font.BOLD, 12));
        add(previousBtn);
    }

    private void previousListener() {
        previousBtn.addActionListener(e -> replayBoard.previousStep());
    }

    private void nextListener() {
        nextBtn.addActionListener(e -> replayBoard.nextStep());
    }

    private void addNext() {
        nextBtn = new JButton("Next");
        nextBtn.setLocation(HEIGTH, HEIGTH / 10 + 180);
        nextBtn.setSize(150, 60);
        nextBtn.setFont(new Font("Rockwell", Font.BOLD, 12));
        add(nextBtn);
    }

    public void addReplayBoard() {
        replayBoard = new ReplayBoard(REPLAY_BOARD_SIZE, REPLAY_BOARD_SIZE);
        replayBoard.setLocation(HEIGTH / 10, HEIGTH / 10);
        replayBoard.replayFrame = this;
        add(replayBoard);
    }

    public void setPreviousVisible(boolean visible) {
        previousBtn.setVisible(visible);
    }

    public void setNextVisible(boolean visible) {
        nextBtn.setVisible(visible);
    }
    public  void setPlayerLabel(ChessColor color) {
        if (color == BLACK) {
            playerLabel.setText("BLACK");
        } else {
            playerLabel.setText("WHITE");
        }
    }
}

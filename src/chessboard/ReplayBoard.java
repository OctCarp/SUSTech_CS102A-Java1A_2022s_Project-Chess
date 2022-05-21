package chessboard;

import chess.*;
import util.Step;
import util.StepSaver;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class ReplayBoard extends JComponent {
    private static int REPLAY_CHESS_SIZE;
    private ChessColor replayColor=ChessColor.WHITE;
    public static ChessComponent[][] replayComponents = new ChessComponent[8][8];
    public static LinkedList<Step> replayList;
    public  int stepNumber;
    public int currentNumber;
    public ReplayFrame replayFrame;

    public ReplayBoard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        REPLAY_CHESS_SIZE = width / 8;
        currentNumber=0;
    }
    public void setReplayBoardList(){
        replayList= StepSaver.stepList;
        stepNumber=replayList.size();
        nextStep();
        replayFrame.setPreviousVisible(false);
    }
    public void previousStep() {
        currentNumber--;
        replayFrame.setNextVisible(true);
        if (currentNumber >= 1) {
            if (currentNumber == 1) {
                replayFrame.setPreviousVisible(false);
            }
            Step step = replayList.get(currentNumber - 1);
            paintByStep(step);
        } else {
            currentNumber = 1;
        }
    }

    public void nextStep() {
        currentNumber++;
        replayFrame.setPreviousVisible(true);
        if (currentNumber <= stepNumber) {
            if (currentNumber == stepNumber) {
                replayFrame.setNextVisible(false);
            }
            replayFrame.setPreviousVisible(true);
            Step step = replayList.get(currentNumber - 1);
            paintByStep(step);
        } else currentNumber = stepNumber;
    }

    private void paintByStep(Step step) {
        replayFrame.setPlayerLabel(step.getPlayer());
        ChessComponent[][] chessComponents1 = step.getChessComponents1();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessComponent chess = chessComponents1[i][j];
                if (chess instanceof RookChessComponent) {
                    RookOnBoard(i, j, chess.getChessColor());
                } else if (chess instanceof QueenChessComponent) {
                    QueenOnBoard(i, j, chess.getChessColor());
                } else if (chess instanceof KingChessComponent) {
                    KingOnBoard(i, j, chess.getChessColor());
                } else if (chess instanceof BishopChessComponent) {
                    BishopOnBoard(i, j, chess.getChessColor());
                } else if (chess instanceof KnightChessComponent) {
                    KnightOnBoard(i, j, chess.getChessColor());
                } else if (chess instanceof PawnChessComponent) {
                    PawnOnBoard(i, j, chess.getChessColor());
                } else if (chess instanceof EmptySlotComponent) {
                    EmptyOnBoard(i, j);
                }
                replayComponents[i][j].repaint();
            }
        }
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (replayComponents[row][col] != null) {
            remove(replayComponents[row][col]);
        }
        add(replayComponents[row][col] = chessComponent);
    }

    private void EmptyOnBoard(int row, int col) {
        putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(row, col), calculatePoint(row, col), REPLAY_CHESS_SIZE));
    }


    private void RookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, REPLAY_CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void KnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, REPLAY_CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void BishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, REPLAY_CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void QueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, REPLAY_CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void KingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, REPLAY_CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void PawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, REPLAY_CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public static Point calculatePoint(int row, int col) {
        return new Point(col * REPLAY_CHESS_SIZE, row * REPLAY_CHESS_SIZE);
    }
}

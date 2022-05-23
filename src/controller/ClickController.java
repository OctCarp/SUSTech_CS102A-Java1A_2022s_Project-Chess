package controller;


import audio.AudioPlay;
import chess.*;
import chessboard.ChessGameFrame;
import chessboard.Chessboard;
import chessboard.ChessboardPoint;
import chessboard.Winboard;
import util.Step;
import util.StepSaver;

import javax.swing.*;
import java.awt.*;

import static chessboard.Chessboard.*;

public class ClickController {
    private Chessboard chessboard;
    private ChessComponent first;

    public ClickController() {

    }

    public void removeFirst(ChessComponent chessComponent) {
        chessComponent.setSelected(false);
        ChessComponent recordFirst = first;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponents[i][j].setSquareColor(chessComponents[i][j].getBackColor(new ChessboardPoint(i, j)));
                chessComponents[i][j].repaint();
            }
        }
        if (first != null) {
            recordFirst.repaint();
            first = null;
        }
    }

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (first.canMoveTo(chessComponents, new ChessboardPoint(i, j))) {
                            if (chessComponent.getChessColor() != chessComponents[i][j].getChessColor()
                                    || (first instanceof KingChessComponent && first.getChessColor() == ChessColor.WHITE
                                    && chessComponents[i][j] instanceof RookChessComponent
                                    && chessComponent.getChessColor() == ChessColor.WHITE && queenSideW == true && (i == 7 && j == 0))
                                    || (first instanceof KingChessComponent && first.getChessColor() == ChessColor.WHITE
                                    && chessComponents[i][j] instanceof RookChessComponent
                                    && chessComponent.getChessColor() == ChessColor.WHITE && kingSideW == true && (i == 7 && j == 7))
                                    || (first instanceof KingChessComponent && first.getChessColor() == ChessColor.BLACK
                                    && chessComponents[i][j] instanceof RookChessComponent
                                    && chessComponent.getChessColor() == ChessColor.BLACK && queenSideB == true && (i == 0 && j == 0))
                                    || (first instanceof KingChessComponent && first.getChessColor() == ChessColor.BLACK
                                    && chessComponents[i][j] instanceof RookChessComponent
                                    && chessComponent.getChessColor() == ChessColor.BLACK && kingSideB == true && (i == 0 && j == 7))
                            ) {
                                chessComponents[i][j].setSquareColor(BackColor.ATTACKED.getColor());
                                chessComponents[i][j].setAttacked(true);
                                chessComponents[i][j].repaint();
                            }
                        }
                    }
                }
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                removeFirst(chessComponent);
                chessboard.removeAttacked();
            } else if (first instanceof KingChessComponent && chessComponent instanceof RookChessComponent && first.getChessColor() == chessComponent.getChessColor()
                    && (first.getChessColor() == ChessColor.WHITE &&
                    chessComponent.getChessboardPoint().getX() == 7 && chessComponent.getChessboardPoint().getY() == 0 && queenSideW == true)) {
                chessboard.castling(first, chessComponent);
                queenSideW = false;
                kingSideW = false;
                chessboard.turn++;
                first = null;
            } else if (first instanceof KingChessComponent && chessComponent instanceof RookChessComponent && first.getChessColor() == chessComponent.getChessColor()
                    && (first.getChessColor() == ChessColor.WHITE &&
                    chessComponent.getChessboardPoint().getX() == 7 && chessComponent.getChessboardPoint().getY() == 7 && kingSideW == true)) {
                chessboard.castling(first, chessComponent);
                queenSideW = false;
                kingSideW = false;
                chessboard.turn++;
                first = null;
            } else if (first instanceof KingChessComponent && chessComponent instanceof RookChessComponent && first.getChessColor() == chessComponent.getChessColor()
                    && (first.getChessColor() == ChessColor.BLACK &&
                    chessComponent.getChessboardPoint().getX() == 0 && chessComponent.getChessboardPoint().getY() == 0 && queenSideB == true)) {
                chessboard.castling(first, chessComponent);
                queenSideB = false;
                kingSideB = false;
                chessboard.turn++;
                first = null;
            } else if (first instanceof KingChessComponent && chessComponent instanceof RookChessComponent && first.getChessColor() == chessComponent.getChessColor()
                    && (first.getChessColor() == ChessColor.BLACK &&
                    chessComponent.getChessboardPoint().getX() == 0 && chessComponent.getChessboardPoint().getY() == 7 && kingSideB == true)) {
                chessboard.castling(first, chessComponent);
                kingSideB = false;
                queenSideB = false;
                chessboard.turn++;
                first = null;
            } else if (first instanceof PawnChessComponent && ((PawnChessComponent) first).PassingSoldier(chessComponents, chessComponent.getChessboardPoint())) {
                ChessComponent[][] chessComponents1 = chessboard.recordComponents(chessComponents);
                Step oneStep = new Step(chessboard.getCurrentColor(), chessComponents1);
                if (StepSaver.stepList.size() != 0) {
                    ChessboardPoint lastMovedPoint = StepSaver.stepList.getLast().getMoveChessPoint();
                    oneStep.setMovedChessPoint(new ChessboardPoint(lastMovedPoint.getX(), lastMovedPoint.getY()));
                }
                if (first.getChessColor() == ChessColor.BLACK) {
                    chessboard.swapChessComponents(first, chessComponents[chessComponent.getChessboardPoint().getX() - 1][chessComponent.getChessboardPoint().getY()]);
                    chessboard.swapChessComponents(first, chessComponent);
                } else {
                    chessboard.swapChessComponents(first, chessComponents[chessComponent.getChessboardPoint().getX() + 1][chessComponent.getChessboardPoint().getY()]);
                    chessboard.swapChessComponents(first, chessComponent);
                }
                chessboard.swapColor();
                oneStep.setMoveChessPoint(first.getChessboardPoint());
                StepSaver.stepList.add(oneStep);
                AudioPlay.playHit();


                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        chessComponents[i][j].setSquareColor(chessComponents[i][j].getBackColor(new ChessboardPoint(i, j)));
                        chessComponents[i][j].setAttacked(false);
                        chessComponents[i][j].repaint();
                    }
                }
                Chessboard.turn++;
                first.setMove(Chessboard.turn);
                chessboard.setCanMoveToW();
                chessboard.setCanMoveToB();
                ChessGameFrame.setStatusLabelCheck(chessboard);
                first.setSelected(false);
                Countdown.restart();
                first = null;
            } else if (first instanceof PawnChessComponent && (chessComponent.getChessboardPoint().getX() == 0 || chessComponent.getChessboardPoint().getX() == 7) && first.canMoveTo(chessComponents, chessComponent.getChessboardPoint())) {
                if (chessComponent instanceof KingChessComponent) {
                    Winboard.setWinText(chessComponent.getChessColor());
                    ChessComponent[][] chessComponents1 = chessboard.recordComponents(chessComponents);
                    Step oneStep = new Step(chessboard.getCurrentColor(), chessComponents1);
                    StepSaver.stepList.add(oneStep);
                    chessboard.swapChessComponents(first, chessComponent);
                    chessboard.swapColor();
                    ChessComponent[][] chessComponents2 = chessboard.recordComponents(chessComponents);
                    Step checkStep = new Step(chessboard.getCurrentColor(), chessComponents2);
                    StepSaver.stepList.add(checkStep);
                    Winboard.setReplayList();
                    chessboard.chessGameFrame.winboard.setVisible(true);
                    chessboard.chessGameFrame.setVisible(false);
                } else {
                    ChessComponent[][] chessComponents1 = chessboard.recordComponents(chessComponents);
                    Step oneStep = new Step(chessboard.getCurrentColor(), chessComponents1);
                    int row = first.getChessboardPoint().getX();
                    int column = first.getChessboardPoint().getY();
                    Object[] ChessComponents = {"Queen", "Rook", "Bishop", "Knight"};
                    String ChessComponent = (String) JOptionPane.showInputDialog(null, "Which to Promote", "Promotion", JOptionPane.QUESTION_MESSAGE, null, ChessComponents, ChessComponents[0]);
                    while (ChessComponent == null) {
                        ChessComponent = (String) JOptionPane.showInputDialog(null, "You must choose one", "Promotion", JOptionPane.ERROR_MESSAGE, null, ChessComponents, ChessComponents[0]);
                    }
                    if (ChessComponent.equals("Queen")) {
                        chessboard.putChessOnBoard
                                (new QueenChessComponent(new ChessboardPoint(first.getChessboardPoint().getX(), first.getChessboardPoint().getY()),
                                        Chessboard.calculatePoint(first.getChessboardPoint().getX(), first.getChessboardPoint().getY()),
                                        first.getChessColor(), this, Chessboard.CHESS_SIZE));
                    } else if (ChessComponent.equals("Rook")) {
                        chessboard.putChessOnBoard
                                (new RookChessComponent(new ChessboardPoint(first.getChessboardPoint().getX(), first.getChessboardPoint().getY()),
                                        Chessboard.calculatePoint(first.getChessboardPoint().getX(), first.getChessboardPoint().getY()),
                                        first.getChessColor(), this, Chessboard.CHESS_SIZE));
                    } else if (ChessComponent.equals("Bishop")) {
                        chessboard.putChessOnBoard
                                (new BishopChessComponent(new ChessboardPoint(first.getChessboardPoint().getX(), first.getChessboardPoint().getY()),
                                        Chessboard.calculatePoint(first.getChessboardPoint().getX(), first.getChessboardPoint().getY()),
                                        first.getChessColor(), this, Chessboard.CHESS_SIZE));
                    } else if (ChessComponent.equals("Knight")) {
                        chessboard.putChessOnBoard
                                (new KnightChessComponent(new ChessboardPoint(first.getChessboardPoint().getX(), first.getChessboardPoint().getY()),
                                        Chessboard.calculatePoint(first.getChessboardPoint().getX(), first.getChessboardPoint().getY()),
                                        first.getChessColor(), this, Chessboard.CHESS_SIZE));
                    }
                    first = chessComponents[row][column];
                    first.moved++;
                    chessboard.swapChessComponents(first, chessComponent);
                    chessboard.swapColor();
                    oneStep.setMoveChessPoint(first.getChessboardPoint());
                    AudioPlay.playHit();
                    StepSaver.stepList.add(oneStep);


                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            chessComponents[i][j].setSquareColor(chessComponents[i][j].getBackColor(new ChessboardPoint(i, j)));
                            chessComponents[i][j].setAttacked(false);
                            chessComponents[i][j].repaint();
                        }
                    }
                    Chessboard.turn++;
                    first.setMove(Chessboard.turn);
                    chessboard.setCanMoveToW();
                    chessboard.setCanMoveToB();
                    ChessGameFrame.setStatusLabelCheck(chessboard);
                    first.setSelected(false);
                    Countdown.restart();
                    first = null;
                }
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                if (chessComponent instanceof KingChessComponent) {
                    Winboard.setWinText(chessComponent.getChessColor());
                    ChessComponent[][] chessComponents1 = chessboard.recordComponents(chessComponents);
                    Step oneStep = new Step(chessboard.getCurrentColor(), chessComponents1);
                    oneStep.setCastling(chessboard);
                    StepSaver.stepList.add(oneStep);
                    chessboard.swapChessComponents(first, chessComponent);
                    ChessComponent[][] chessComponents2 = chessboard.recordComponents(chessComponents);
                    Step checkStep = new Step(chessboard.getCurrentColor(), chessComponents2);
                    StepSaver.stepList.add(checkStep);
                    Winboard.setReplayList();
                    chessboard.chessGameFrame.winboard.setVisible(true);
                    chessboard.chessGameFrame.setVisible(false);
                }
                first.moved++;
                ChessComponent[][] chessComponents1 = chessboard.recordComponents(chessComponents);
                Step oneStep = new Step(chessboard.getCurrentColor(), chessComponents1);
                oneStep.setCastling(chessboard);

                /*if (StepSaver.stepList.size() != 0) {
                    ChessboardPoint lastMovedPoint = StepSaver.stepList.getLast().getMoveChessPoint();
                    if (lastMovedPoint != null) {
                        oneStep.setMovedChessPoint(new ChessboardPoint(lastMovedPoint.getX(), lastMovedPoint.getY()));
                    }
                }*/
                if (first instanceof KingChessComponent) {
                    if (first.getChessColor() == ChessColor.WHITE) {
                        queenSideW = false;
                        kingSideW = false;
                    } else if (first.getChessColor() == ChessColor.BLACK) {
                        queenSideB = false;
                        kingSideB = false;
                    }
                }
                if (first instanceof RookChessComponent) {
                    if (first.getChessColor() == ChessColor.WHITE) {
                        if (first.getChessboardPoint().getX() == 7 && first.getChessboardPoint().getY() == 0) {
                            queenSideW = false;
                        } else if (first.getChessboardPoint().getX() == 7 && first.getChessboardPoint().getY() == 7) {
                            kingSideW = false;
                        }
                    } else {
                        if (first.getChessboardPoint().getX() == 0 && first.getChessboardPoint().getY() == 0) {
                            queenSideB = false;
                        } else if (first.getChessboardPoint().getX() == 0 && first.getChessboardPoint().getY() == 7) {
                            kingSideB = false;
                        }
                    }
                }

                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                AudioPlay.playHit();


                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        chessComponents[i][j].setSquareColor(chessComponents[i][j].getBackColor(new ChessboardPoint(i, j)));
                        chessComponents[i][j].setAttacked(false);
                        chessComponents[i][j].repaint();
                    }
                }
                Chessboard.turn++;
                first.setMove(Chessboard.turn);
                oneStep.setMoveChessPoint(new ChessboardPoint(first.getChessboardPoint().getX(), first.getChessboardPoint().getY()));
                StepSaver.stepList.add(oneStep);
                chessboard.setCanMoveToW();
                chessboard.setCanMoveToB();
                ChessGameFrame.setStatusLabelCheck(chessboard);
                first.setSelected(false);
                Countdown.restart();
                first = null;
            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }

    public ChessComponent getFirst() {
        return first;
    }
}
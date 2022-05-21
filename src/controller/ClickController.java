package controller;


import audio.AudioPlay;
import chess.*;
import chessboard.ChessGameFrame;
import chessboard.Chessboard;
import chessboard.ChessboardPoint;
import chessboard.Winboard;
import util.Step;
import util.StepSaver;

import java.awt.*;

import static chessboard.Chessboard.chessComponents;

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
                                    ||(first instanceof KingChessComponent
                                    && chessComponents[i][j] instanceof RookChessComponent
                                    &&chessComponent.getChessColor()== chessComponents[i][j].getChessColor())) {
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
            }
            else if (first instanceof KingChessComponent&&chessComponent instanceof RookChessComponent&&first.getChessColor()==chessComponent.getChessColor()){
                if (!first.moved&&!chessComponent.moved){
                    chessboard.castling(first,chessComponent);
                    if (first.moved){
                        first = null;
                        chessboard.turn++;
                    }
                }
            }
            else if (first instanceof PawnChessComponent&&((PawnChessComponent) first).PassingSoldier(chessComponents,chessComponent.getChessboardPoint())){
                        ChessComponent[][] chessComponents1 = chessboard.recordComponents(chessComponents);
                        Step oneStep = new Step(chessboard.getCurrentColor(), chessComponents1);
                        StepSaver.stepList.add(oneStep);
                        if (first.getChessColor()==ChessColor.BLACK){
                        chessboard.swapChessComponents(first, chessComponents[chessComponent.getChessboardPoint().getX()-1][chessComponent.getChessboardPoint().getY()]);
                        chessboard.swapChessComponents(first,chessComponent);
                        }
                        else {
                            chessboard.swapChessComponents(first, chessComponents[chessComponent.getChessboardPoint().getX()+1][chessComponent.getChessboardPoint().getY()]);
                            chessboard.swapChessComponents(first,chessComponent);
                        }
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
                        chessboard.setCanMoveToW();
                        chessboard.setCanMoveToB();
                        chessboard.CheckMake();
                        ChessGameFrame.setStatusLabelCheck(chessboard);
                        first.setSelected(false);
                        Countdown.restart();
                        first = null;
            }
              else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                if (chessComponent instanceof KingChessComponent) {
                    Winboard.setWinText(chessComponent.getChessColor());
                    chessboard.chessGameFrame.winboard.setVisible(true);
                    chessboard.chessGameFrame.setVisible(false);
                }
                if (first.moved!=true){
                    first.moved=true;
                }
                ChessComponent[][] chessComponents1 = chessboard.recordComponents(chessComponents);
                Step oneStep = new Step(chessboard.getCurrentColor(), chessComponents1);
                StepSaver.stepList.add(oneStep);
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
                chessboard.setCanMoveToW();
                chessboard.setCanMoveToB();
                chessboard.CheckMake();
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

    public ChessComponent getFirst(){
        return first;
    }
}

package controller;


import chess.*;
import chessboard.ChessGameFrame;
import chessboard.Chessboard;
import chessboard.ChessboardPoint;
import chessboard.Winboard;

import java.awt.*;

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
                Chessboard.chessComponents[i][j].setSquareColor(Chessboard.chessComponents[i][j].getBackColor(new ChessboardPoint(i, j)));
                Chessboard.chessComponents[i][j].repaint();
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
                        if (first.canMoveTo(Chessboard.chessComponents, new ChessboardPoint(i, j))) {
                            if (chessComponent.getChessColor() != Chessboard.chessComponents[i][j].getChessColor()
                                    ||(first instanceof KingChessComponent
                                    &&Chessboard.chessComponents[i][j] instanceof RookChessComponent
                                    &&chessComponent.getChessColor()== Chessboard.chessComponents[i][j].getChessColor())) {
                                Chessboard.chessComponents[i][j].setSquareColor(BackColor.ATTACKED.getColor());
                                Chessboard.chessComponents[i][j].setAttacked(true);
                                Chessboard.chessComponents[i][j].repaint();
                            }
                        }
                    }
                }
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                removeFirst(chessComponent);
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        Chessboard.chessComponents[i][j].setAttacked(false);
                    }
                }
            }
            else if (first instanceof KingChessComponent&&chessComponent instanceof RookChessComponent&&first.getChessColor()==chessComponent.getChessColor()){
                if (!first.moved&&!chessComponent.moved){
                    chessboard.castling(first,chessComponent);
                    if (first.moved){
                        first = null;
                    }
                }

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
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();


                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        Chessboard.chessComponents[i][j].setSquareColor(Chessboard.chessComponents[i][j].getBackColor(new ChessboardPoint(i, j)));
                        Chessboard.chessComponents[i][j].setAttacked(false);
                        Chessboard.chessComponents[i][j].repaint();
                    }
                }
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

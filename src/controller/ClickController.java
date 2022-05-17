package controller;


import chess.ChessColor;
import chess.ChessComponent;
import chessboard.ChessGameFrame;
import chessboard.Chessboard;
import chessboard.ChessboardPoint;

import java.awt.*;

public class ClickController {
    private  Chessboard chessboard;
    private ChessComponent first;
    public ClickController(){

    }

    public void removeFirst(ChessComponent chessComponent) {
        chessComponent.setSelected(false);
        ChessComponent recordFirst = first;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Chessboard.chessComponents[i][j].setSquareColor(Chessboard.chessComponents[i][j].getBackColor(new ChessboardPoint(i,j)));
                Chessboard.chessComponents[i][j].repaint();
            }
        }
        if (first!=null) {
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
                for (int i = 0; i <8 ; i++) {
                    for (int j = 0; j <8 ; j++) {
                        if(first.canMoveTo(Chessboard.chessComponents,new ChessboardPoint(i,j))){
                            if(chessComponent.getChessColor()!=Chessboard.chessComponents[i][j].getChessColor()) {
                                Chessboard.chessComponents[i][j].setSquareColor(Color.YELLOW);
                                Chessboard.chessComponents[i][j].repaint();
                            }
                        }
                    }
                }
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
               removeFirst(chessComponent);
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();


               for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        Chessboard.chessComponents[i][j].setSquareColor(Chessboard.chessComponents[i][j].getBackColor(new ChessboardPoint(i,j)));
                        Chessboard.chessComponents[i][j].repaint();
                    }
                }
               chessboard.setCanMoveToW();
               chessboard.setCanMoveToB();
               chessboard.CheckMake();
                ChessGameFrame.setStatusLabelCheck(chessboard);
               first.setSelected(false);
               Countdown.restart();
               first=null;
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
}

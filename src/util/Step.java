package util;

import chess.ChessColor;
import chess.ChessComponent;
import chess.EmptySlotComponent;
import view.Chessboard;
import view.ChessboardPoint;

import java.io.Serializable;

public class Step implements Serializable {
    private ChessComponent chess;
    private ChessboardPoint start;
    private ChessboardPoint end;
    private ChessColor color;
    private ChessComponent eatChess;
    int ate=0;

    public Step() {
    }

    public Step(ChessComponent chess, ChessboardPoint start, ChessboardPoint end) {
        this.chess = chess;
        this.start = start;
        this.end = end;
        this.color = chess.getChessColor();
        ate = 0;
    }

    public Step(ChessComponent chess, ChessboardPoint start, ChessboardPoint end, ChessComponent eatChess) {
        this.chess = chess;
        this.start = start;
        this.end = end;
        this.color = chess.getChessColor();
        this.eatChess = eatChess;
        ate = 1;
    }

    public int colorInt() {
        if (chess.getChessColor() == ChessColor.BLACK) {
            return 1;
        } else if (chess.getChessColor() == ChessColor.WHITE) {
            return 2;
        } else return 0;
    }


    public ChessComponent getChess() {
        return chess;
    }

    public void setChess(ChessComponent chess) {
        this.chess = chess;
        start = new ChessboardPoint(chess.getX(),chess.getY());
        this.color=chess.getChessColor();

    }

    public ChessboardPoint getStart() {
        return start;
    }

    public void setStart(ChessboardPoint start) {
        this.start = new ChessboardPoint(start.getX(),start.getY());
    }

    public ChessboardPoint getEnd() {
        return end;
    }

    public void setEnd(ChessboardPoint end) {
        this.end = new ChessboardPoint(end.getX(),end.getY());
    }

    public ChessColor getColor() {
        return color;
    }
    public void setEatChess(ChessComponent eatChess){
        this.eatChess=eatChess;
        this.end = new ChessboardPoint(eatChess.getX(),eatChess.getY());
        ate=1;
    }

    public ChessComponent getEatChess() {
        return eatChess;
    }

    public String toString() {
        return colorInt() + " " + start.getX() + start.getY() + " " + end.getX() + end.getY() + " " + ate + "" + eatChess.getName();
    }
}
package util;

import chess.*;
import chessboard.Chessboard;
import chessboard.ChessboardPoint;


public class Step {
    ChessColor player;
    ChessComponent[][] chessComponents1 = new ChessComponent[8][8];
    private ChessboardPoint movedChessPoint;
    private ChessboardPoint moveChessPoint;
    private boolean castlingWhite=true;
    private boolean castlingBlack=true;

    public void setPlayer(ChessColor player) {
        this.player = player;
    }

    public void setCastling(Chessboard chessboard) {
        this.castlingBlack=chessboard.castlingBlack;
        this.castlingWhite=chessboard.castlingWhite;
    }

    public Step() {
    }

    public Step(ChessColor player, ChessComponent[][] chessComponents) {
        this.player = player;
        setChessComponents(chessComponents);
        if (StepSaver.stepList.size() != 0) {
            ChessboardPoint lastMovedPoint = StepSaver.stepList.getLast().getMoveChessPoint();
            if (lastMovedPoint != null) {
                this.setMovedChessPoint(new ChessboardPoint(lastMovedPoint.getX(), lastMovedPoint.getY()));
            }
        }
    }

    public void setChessComponents(ChessComponent[][] chessComponents1) {
        this.chessComponents1 = chessComponents1;
    }

    public ChessboardPoint getMoveChessPoint() {
        return moveChessPoint;
    }

    public ChessboardPoint getMovedChessPoint() {
        return movedChessPoint;
    }

    public ChessComponent[][] getChessComponents1() {
        return chessComponents1;
    }

    public ChessColor getPlayer() {
        return player;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                sb.append(chessComponents1[i][j].toString());
            }
            sb.append(String.format("\n"));
        }
        if(castlingBlack==true){
            sb.append(String.format("T"));
        }else {
            sb.append(String.format("F"));
        }
        if(castlingWhite==true){
            sb.append(String.format("t\n"));
        }else {
            sb.append(String.format("f\n"));
        }
        if (player == ChessColor.BLACK) {
            sb.append(String.format("x"));
        } else {
            sb.append(String.format("y"));
        }
        return sb.toString();
    }

    public void setMovedChessPoint(ChessboardPoint movedChessPoint) {
        this.movedChessPoint = movedChessPoint;
    }

    public void setMoveChessPoint(ChessboardPoint moveChessPoint) {
        this.moveChessPoint = moveChessPoint;
    }

    public boolean isCastlingBlack() {
        return castlingBlack;
    }

    public boolean isCastlingWhite() {
        return castlingWhite;
    }

    public void setCastlingBlack(boolean castlingBlack) {
        this.castlingBlack = castlingBlack;
    }

    public void setCastlingWhite(boolean castlingWhite) {
        this.castlingWhite = castlingWhite;
    }
}

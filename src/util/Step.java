package util;

import chess.*;


public class Step {
    ChessColor player;
    ChessComponent[][] chessComponents1 = new ChessComponent[8][8];

    public void setPlayer(ChessColor player) {
        this.player = player;
    }

    public Step() {
    }

    public Step(ChessColor player, ChessComponent[][] chessComponents) {
        this.player = player;
        setChessComponents(chessComponents);
    }

    public void setChessComponents(ChessComponent[][] chessComponents1) {
        this.chessComponents1 = chessComponents1;
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
        if (player == ChessColor.BLACK) {
            sb.append(String.format("x"));
        } else {
            sb.append(String.format("y"));
        }
        return sb.toString();
    }
}

package util;

import chess.ChessComponent;
import chessboard.Chessboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoardLoader {
    private Chessboard chessboard;
    public static List<String> boardStrings;
    private static ChessComponent[][] chessComponents2;
    private static BufferedReader boardReader;

    private BoardLoader(Chessboard chessboard) {
        super();
        this.chessboard = chessboard;
    }

    public ChessComponent[][] getChessboard() {
        return chessComponents2;
    }

    public static void readBoard(String s) {
        try {
            boardReader = new BufferedReader(new FileReader(s));
            boardStrings=board();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* private static void initiate(String s) throws IOException {
        if (steps != null) {
            steps.clear();
        }
        StepSaver.initiate();
        steps = new ArrayList<>();
        chessComponents2 = new ChessComponent[8][8];
        gameReader = new BufferedReader(new FileReader(s));
    }*/

    public static List<String> board() throws IOException {
            List<String> chessBoard = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                chessBoard.add(boardReader.readLine());
            }
            boardStrings=chessBoard;
            return chessBoard;
        }

    public Step loadStep(List<String> chessStep) {
/*        Step theStep = new Step();
        ChessComponent[][] chessComponents3 = new ChessComponent[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char c = chessStep.get(i).charAt(j);
                ChessComponent chess = type(c, i, j);
                chessComponents3[i][j] = chess;
            }
        }
        char player = chessStep.get(8).charAt(0);
        if (player == 'b') {
            theStep.setPlayer(ChessColor.BLACK);
        } else {
            theStep.setPlayer(ChessColor.WHITE);
        }
        return theStep;


    */
        return null;
    }
}

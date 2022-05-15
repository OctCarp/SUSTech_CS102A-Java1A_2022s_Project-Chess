package util;

import chess.ChessComponent;
import chessboard.Chessboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardLoader {
    static boolean one01 = false;//not 8*8
    static boolean one02 = false;//not chess
    static boolean one03 = false;//no player
    public static boolean one04 = false;//not txt
    private Chessboard chessboard;
    public static List<String> boardStrings;
    private static ChessComponent[][] chessComponents2;
    private static BufferedReader boardReader;
    public static String wrong;

    private BoardLoader(Chessboard chessboard) {
        super();
        this.chessboard = chessboard;
    }

    public static void initLoader() {
        one01 = false;
        one02 = false;
        one03 = false;
        one04 = false;
        wrong = null;
    }

    static List<Character> oriChess = Arrays.asList('K', 'k', 'Q', 'q', 'R', 'r', 'N', 'n', 'B', 'b', 'P', 'p', '_');

    public ChessComponent[][] getChessboard() {
        return chessComponents2;
    }

    public static void readBoard(String s) {
        try {
            boardReader = new BufferedReader(new FileReader(s));
            boardStrings = board();
            check(boardStrings);
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
        boardStrings = chessBoard;
        return chessBoard;
    }

    public static void check(List<String> boardStrings) {
        try {
            for (int i = 0; i < 8; i++) {
                String s = boardStrings.get(i);
                if (s.length() != 8) {
                    one01 = true;
                }
            }
        } catch (NullPointerException e) {
            one01 = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            one01 = true;
        }
        for (int i = 0; i < boardStrings.size()-1; i++) {
            String s = boardStrings.get(i);
            if (s != null) {
                for (int j = 0; j < s.length(); j++) {
                    if (!oriChess.contains(s.charAt(j))) {
                        one02 = true;
                    }
                }
            }
        }
        try {
            String s = boardStrings.get(8);
            char player = s.charAt(0);
            if (!(player == 'x' || player == 'y')) {
                one03 = true;
            }
        } catch (NullPointerException e) {
            one03 = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            one03 = true;
        }

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

    public static boolean legal() {
        if (one01 == false && one02 == false && one03 == false && one04 == false) {
            return true;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Wrong!\n"));
            if (one01 == true) {
                sb.append("101 ");
            }
            if (one02 == true) {
                sb.append("102 ");
            }
            if (one03 == true) {
                sb.append("103 ");
            }
            if (one04 == true) {
                sb.append("104 ");
            }
            wrong = sb.toString();
            return false;
        }
    }
}

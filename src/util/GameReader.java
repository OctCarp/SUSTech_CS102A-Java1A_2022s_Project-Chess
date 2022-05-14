package util;

import chess.*;
import chessboard.Chessboard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameReader {
    private Chessboard chessboard;
    private static List<Step> steps;
    private static ChessComponent[][] chessComponents2;
    private static BufferedReader gameReader;

    private GameReader(Chessboard chessboard) {
        super();
        this.chessboard = chessboard;
    }

    public List<Step> getSteps() {
        return steps;
    }


    public ChessComponent[][] getChessboard() {
        return chessComponents2;
    }

    public void readGame(String s) {
        try {
            initiate(s);
            readStep();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initiate(String s) throws IOException {
        if (steps != null) {
            steps.clear();
        }
        StepSaver.initiate();
        steps = new ArrayList<>();
        chessComponents2 = new ChessComponent[8][8];
        gameReader = new BufferedReader(new FileReader(s));
    }

    private void readStep() throws IOException {
        gameReader.readLine();
        String s;

        int maxCnt = 0;

        int nextColor = -1;

        while ((s = gameReader.readLine()) != null && !s.startsWith("===")) {

            List<String> chessStep = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                chessStep.add(gameReader.readLine());
            }
            steps.add(loadStep(chessStep));

            /*String[] split = s.split(" ");
            int sid = Integer.parseInt(split[0]);
            boolean isCheat = Boolean.parseBoolean(split[1]);
            int x = Integer.parseInt(split[2]);
            int y = Integer.parseInt(split[3]);
            Color color = parseColor(Integer.parseInt(split[4]));
            Step sx = new Step(sid, isCheat, x, y, color);
            steps.add(sx);
            GameSaver.recordStep(sx);
            maxCnt = Math.max(sid, maxCnt);

            nextColor *= -1;*/
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
}


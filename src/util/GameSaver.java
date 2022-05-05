package util;

import chess.ChessComponent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameSaver extends Util{
    private static int gid = 0;
    private static BufferedWriter gameSaver;
   private static List<Step> stepList;

    private GameSaver(){
        super();
    }

    public static void initiate(){
        if(stepList!=null){
            stepList.clear();
        }
        stepList = new ArrayList<>();
        gid++;
    }

    public static void recordStep(Step s){
        stepList.add(s);
    }

    public static void saveGame(String fileName, ChessComponent[][] chessboard){
        try{
            gameSaver = new BufferedWriter(new FileWriter(fileName));
            gameSaver.write("Game-Id: "+gid+System.lineSeparator());
            saveStepsIntoFile(fileName,chessboard);
            saveChessboardIntoFile(fileName,chessboard);
            gameSaver.flush();
            gameSaver.close();
        }catch (IOException e){

            //todo: fail!
            e.printStackTrace();
        }

    }

    private static void saveStepsIntoFile(String fileName, ChessComponent[][] chessboard) throws IOException {
        gameSaver.write("Reversi-GameSteps:"+System.lineSeparator());
        for (Step s: stepList){
            gameSaver.write(s.toString()+System.lineSeparator());
        }
        gameSaver.write("=========="+System.lineSeparator());
    }

    private static void saveChessboardIntoFile(String fileName, ChessComponent[][] chessboard) throws IOException {
        for (int i = 0; i< 8; i++){
            for (int j = 0; j < 8; j++) {
                gameSaver.write(String.format("%3s",chessboard[i][j].toString()));
            }
            gameSaver.write(System.lineSeparator());
        }
    }
}
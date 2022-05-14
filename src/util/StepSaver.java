package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class StepSaver extends Util{
    private static BufferedWriter gameSaver;
    public static LinkedList<Step> stepList;

    private StepSaver(){
        super();
    }
    public static void initiate(){
        if(stepList!=null){
            stepList.clear();
        }
        stepList = new LinkedList<>();
    }

    public static void recordStep(Step s){
        stepList.add(s);
    }


    public static void saveGame(String fileName){
        try{
            gameSaver = new BufferedWriter(new FileWriter(fileName));
            saveStepsIntoFile(fileName);
            gameSaver.flush();
            gameSaver.close();
        }catch (IOException e){

            //todo: fail!
            e.printStackTrace();
        }

    }

    private static void saveStepsIntoFile(String fileName) throws IOException {
        gameSaver.write("GameSteps:"+System.lineSeparator());
        for (Step s: stepList){
            gameSaver.write(s.toString()+System.lineSeparator());
        }
        gameSaver.write("=========="+System.lineSeparator());
    }

}
package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class StepSaver extends Util {
    private static BufferedWriter gameSaver;
    public static LinkedList<Step> stepList;

    private StepSaver() {
        super();
    }

    public static void initiate() {
        if (stepList != null) {
            stepList.clear();
        }
        stepList = new LinkedList<>();
    }
}
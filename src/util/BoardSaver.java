package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BoardSaver {
    private static BufferedWriter boardSaver;

    public static void saveGame(String fileName) {
        try {
            boardSaver = new BufferedWriter(new FileWriter("./saves/general"+fileName));
            saveBoardIntoFile(fileName);
            boardSaver.flush();
            boardSaver.close();
        } catch (IOException e) {

            //todo: fail!
            e.printStackTrace();
        }
    }

    private static void saveBoardIntoFile(String fileName) throws IOException {
        boardSaver.write(StepSaver.stepList.getLast().toString());
    }
}

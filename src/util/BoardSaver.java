package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BoardSaver {
    private static BufferedWriter boardSaver;

    public static void saveGame(String fileName) {
        try {
            //FIXME: save path
            boardSaver = new BufferedWriter(new FileWriter("./resources/saves/general/" + fileName));
            saveBoardIntoFile(fileName);
            boardSaver.flush();
            boardSaver.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveBoardIntoFile(String fileName) throws IOException {
        boardSaver.write(StepSaver.stepList.getLast().toString());
    }
}

import chessboard.ChessGameFrame;
import chessboard.Homescreen;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Homescreen homescreen= null;
            try {
                homescreen = new Homescreen(1000,760);
            } catch (IOException e) {
                e.printStackTrace();
            }
            homescreen.setVisible(true);
        });
    }
}

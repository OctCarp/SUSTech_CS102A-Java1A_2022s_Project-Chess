import chessboard.ChessGameFrame;
import view.AllFrames;
import view.frame.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1000,760);
            mainFrame.setVisible(true);
        });
    }
}

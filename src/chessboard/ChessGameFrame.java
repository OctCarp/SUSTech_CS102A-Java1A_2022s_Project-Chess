package chessboard;

import chess.ChessColor;
import controller.GameController;
import util.BoardLoader;
import util.BoardSaver;
import util.StepSaver;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static chess.ChessColor.BLACK;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private Chessboard chessboard;
    private GameController gameController;
    static JLabel statusLabel;


    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题

        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        StepSaver.initiate();

        addChessboard();
        addLabel();
        addSaveButton();
        addLoadButton();
        addRestart();
        addRegretButton();
    }

    private void addRestart() {
        JButton button = new JButton("Restart");
        button.addActionListener(e -> restart());
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void restart() {
        if (chessboard != null) {
            this.remove(chessboard);
        }
        addChessboard();
        repaint();
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("BLACK");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }
    public static void setStatusLabel(ChessColor color){
        if(color==BLACK){
            statusLabel.setText("BLACK");
        }else{
            statusLabel.setText("WHITE");
        }
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addSaveButton() {
        JButton button = new JButton("Save");
        add(button);
        button.addActionListener(e -> {
            String filePath = JOptionPane.showInputDialog(this, "input the name here");
            BoardSaver.saveGame(filePath + ".txt");
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addRegretButton() {
        JButton button = new JButton("Regret");
        button.addActionListener((e) -> chessboard.regretStep());
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        JFileChooser chooser = new JFileChooser();
        add(button);
        button.addActionListener(e -> {
            String path = readPath();
            BoardLoader.readBoard(path);
            loadGame();
            chessboard.loadChessGame(BoardLoader.boardStrings);
        });
    }
    private String readPath() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        fc.showOpenDialog(this);

        return fc.getSelectedFile().getAbsolutePath();
    }
    private void loadGame() {
        if (chessboard != null) {
            this.remove(chessboard);
        }
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }
}
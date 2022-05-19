package chessboard;

import chess.ChessColor;
import controller.Countdown;
import controller.GameController;
import util.BoardLoader;
import util.BoardSaver;
import util.Step;
import util.StepSaver;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static chess.ChessColor.BLACK;
import static util.BoardLoader.initLoader;

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
    public static JLabel count;
    static JLabel checkLabel;
    public Winboard winboard;

    Countdown cd;
    Thread t;

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题

        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;
        this.winboard=new Winboard(600,300,this);

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        StepSaver.initiate();
        addCount();
        countTxt();
        addChessboard();
        addLabel();
        addSaveButton();
        addLoadButton();
        addRestart();
        addRegretButton();
        addLabelCheck();
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
        StepSaver.initiate();
        addChessboard();
        checkLabel.setVisible(false);
        setStatusLabel(ChessColor.WHITE);
        repaint();
        Countdown.restart();
    }

    private void addCount() {
        count = new JLabel();
        count.setLocation(HEIGTH + 70, HEIGTH / 10);
        count.setSize(100, 60);
        count.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(count);
    }

    private void countTxt() {
        cd = new Countdown();
        t = new Thread(cd);
        t.start();
    }

    private void setCountBoard(Countdown cd, Chessboard chessboard) {
        cd.setChessboard(chessboard);
    }

    /**
     * 在游戏面板中添加棋盘
     */
    public void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        setCountBoard(cd, chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        chessboard.chessGameFrame=this;
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("WHITE");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(100, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private void addLabelCheck(){
        checkLabel = new JLabel();
        checkLabel.setLocation(HEIGTH, HEIGTH/10 +60 );
        checkLabel.setSize(200, 60);
        checkLabel.setFont(new Font("Rockwell", Font.BOLD, 10));
        setVisible(false);
        add(checkLabel);
    }

    public static void setStatusLabelCheck(Chessboard chessboard){
        if (chessboard.CheckKing(chessboard.getKingB())){
            checkLabel.setText("Check King Black");
            checkLabel.setVisible(true);
        }
        else if (chessboard.CheckKing(chessboard.getKingW())){
            checkLabel.setText("Check King White");
            checkLabel.setVisible(true);
        }
        else checkLabel.setVisible(false);
    }

    public static void setStatusLabel(ChessColor color) {
        if (color == BLACK) {
            statusLabel.setText("BLACK");
        } else {
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

                StepSaver.stepList.add(new Step(chessboard.getCurrentColor(), chessboard.getChessComponents()));
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
            try {
                String path = readPath();
                BoardLoader.readBoard(path);
                if (BoardLoader.legal()) {
                    loadGame();
                    chessboard.loadGame(BoardLoader.boardStrings);
                    BoardLoader.initLoader();
                } else {
                    JOptionPane.showMessageDialog(this, BoardLoader.wrong);
                    BoardLoader.initLoader();
                }
            } catch (NullPointerException w) {
                JOptionPane.showMessageDialog(this, "no file selected");
            }
        });
    }

    private String readPath() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("./saves"));
        fc.showOpenDialog(this);
        try {
            String s = fc.getSelectedFile().getName();
            String[] ss = s.split("\\.");
            if (ss.length == 2) {
                if (!ss[1].equals("txt")) {
                    BoardLoader.one04 = true;
                }
            }else BoardLoader.one04 = true;
            return fc.getSelectedFile().getAbsolutePath();
        } catch (NullPointerException e) {
            return null;
        }
    }

    private void loadGame() {
        if (chessboard != null) {
            this.remove(chessboard);
        }
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        setCountBoard(cd, chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }

    public Chessboard getChessboard(){
        return chessboard;
    }
}
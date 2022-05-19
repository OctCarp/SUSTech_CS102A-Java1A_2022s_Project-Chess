package chessboard;

import util.BoardLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Homescreen extends JFrame implements ActionListener {
    private final int WIDTH;
    private final int HEIGTH;

    public Homescreen(int width, int heigth) throws IOException {
        setTitle("Chess game start");
        WIDTH = width;
        HEIGTH = heigth;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        FlowLayout layout = new FlowLayout();
        this.setLayout(null);
        setVisible(true);
        JButton startBtn =new JButton("Start Game");
        JButton loadBtn=new JButton("Load Game");
        add(startBtn);
        setStart(startBtn);
        add(loadBtn);
        setLoad(loadBtn);
        startBtn.addActionListener(this);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame chessGameFrame=new ChessGameFrame(1000, 760);
                chessGameFrame.setVisible(true);
                setVisible(false);
            }
        });
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame chessGameFrame=new ChessGameFrame(1000, 760);
                chessGameFrame.setVisible(true);
                try {
                    String path = chessGameFrame.readPath();
                    BoardLoader.readBoard(path);
                    if (BoardLoader.legal()) {
                        chessGameFrame.loadGame();
                        chessGameFrame.getChessboard().loadGame(BoardLoader.boardStrings);
                        BoardLoader.initLoader();
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(chessGameFrame, BoardLoader.wrong);
                        BoardLoader.initLoader();
                    }
                } catch (NullPointerException w) {
                    JOptionPane.showMessageDialog(chessGameFrame, "no file selected");
                }
            }
        });

    }

    public void setStart(JButton start){
        start.setLocation(WIDTH/2-100, 600);
        start.setSize(200, 60);
        start.setFont(new Font("Rockwell", Font.BOLD, 20));
    }
    public void setLoad(JButton load){
        load.setLocation(WIDTH/2-100, 500);
        load.setSize(200, 60);
        load.setFont(new Font("Rockwell", Font.BOLD, 20));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

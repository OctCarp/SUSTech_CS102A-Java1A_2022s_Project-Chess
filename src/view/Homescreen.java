package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
        JButton Start=new JButton("Start game");
        add(Start);
        setStart(Start);
        Start.addActionListener(this);
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChessGameFrame chessGameFrame=new ChessGameFrame(1000, 760);
                chessGameFrame.setVisible(true);
                setVisible(false);
            }
        });

    }

    public void setStart(JButton start){
        start.setLocation(WIDTH/2-100, 600);
        start.setSize(200, 60);
        start.setFont(new Font("Rockwell", Font.BOLD, 20));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

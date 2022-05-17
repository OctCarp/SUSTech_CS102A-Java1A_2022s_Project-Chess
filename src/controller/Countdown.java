package controller;

import chess.ChessColor;
import chessboard.Chessboard;
import util.Step;
import util.StepSaver;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

import static chessboard.ChessGameFrame.count;

public class Countdown implements Runnable {
    JLabel j1 = count;
    Chessboard chessboard;
    static long init=30;
    public static long midTime = init;
    public static ChessColor color;
    private ClickController controller;
    /**
     * 方式1
     */
    private static void time1() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                midTime--;
                long ss = midTime % 60;
                System.out.println("还剩" + ss + "秒");
            }
        }, 0, 1000);
    }

    @Override
    public void run() {
        while (midTime > 0) {
            midTime--;
            long ss = midTime;
            j1.setText(String.format("%d", ss));
            try {
                Thread.sleep(1000);
                if (midTime == 0) {
                    StepSaver.stepList.add(new Step(chessboard.getCurrentColor(), chessboard.getChessComponents()));
                    chessboard.removeSelect();
                    chessboard.swapColor();
                    restart();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void restart() {
        midTime = init;
    }

    /**
     * 方式2
     */

    public Countdown() {

    }

    public  void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
    }


}



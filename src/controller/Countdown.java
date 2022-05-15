package controller;

import chess.ChessColor;
import chess.ChessComponent;
import chessboard.ChessGameFrame;
import chessboard.Chessboard;
import util.Step;
import util.StepSaver;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static chessboard.ChessGameFrame.count;

public class Countdown implements Runnable {
    JLabel j2 = count;
    Chessboard chessboard;
    public static long midTime = 5;
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
            j2.setText(String.format("%d", ss));
            try {
                Thread.sleep(1000);
                if (midTime == 0) {
                    chessboard.swapColor();
                    restart();
                    chessboard.removeSelect();
                    StepSaver.stepList.add(new Step(chessboard.getCurrentColor(), chessboard.getChessComponents()));

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void restart() {
        midTime = 5;
    }

    /**
     * 方式2
     */

    public Countdown() {
        color = ChessColor.BLACK;
    }

    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public static void setColor(ChessColor color) {
        Countdown.color = color;
    }

    public static void swapC() {
        color = color == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
    }
}



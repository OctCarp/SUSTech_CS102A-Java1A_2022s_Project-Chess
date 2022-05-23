package controller;

import chess.ChessColor;
import chessboard.ChessGameFrame;
import chessboard.Chessboard;
import util.Step;
import util.StepSaver;

import javax.swing.*;

import static chessboard.ChessGameFrame.count;
import static chessboard.ChessGameFrame.setResume;

public class Countdown extends Thread {
    JLabel j1 = count;
    Chessboard chessboard;
    static long init = 15;
    public static long midTime = init;
    public static ChessColor color;

    private final Object lock = new Object();

    private boolean pause = false;

    public void pauseThread() {
        this.pause = true;
    }

    public void resumeThread() {
        this.pause = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    void onPause() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            if (pause) {
                onPause();
            }
            this.resumeThread();
            midTime--;
            long ss = midTime;
            j1.setText(String.format("%d", ss));
            try {
                Thread.sleep(1000);
                if (midTime == 0) {
                    StepSaver.stepList.add(new Step(chessboard.getCurrentColor(), chessboard.getChessComponents()));
                    chessboard.removeSelect();
                    chessboard.removeAttacked();
                    chessboard.swapColor();
                    restart();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void changePause() {
        if (pause) {
            resumeThread();
            ChessGameFrame.setPause();
        } else {
            pauseThread();
            setResume();
        }
    }

    public static void restart() {
        midTime = init;
    }

    public Countdown() {

    }

    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    //本类有参考网络
}



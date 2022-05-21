package controller;

import chess.ChessColor;
import chessboard.ChessGameFrame;
import chessboard.Chessboard;
import util.Step;
import util.StepSaver;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

import static chessboard.ChessGameFrame.count;
import static chessboard.ChessGameFrame.setResume;

public class Countdown extends Thread {
    JLabel j1 = count;
    Chessboard chessboard;
    static long init = 30;
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

    private final Object lock = new Object();

    //标志线程阻塞情况
    private boolean pause = false;

    /**
     * 设置线程是否阻塞
     */
    public void pauseThread() {
        this.pause = true;
    }

    /**
     * 调用该方法实现恢复线程的运行
     */
    public void resumeThread() {
        this.pause = false;
        synchronized (lock) {
            //唤醒线程
            lock.notify();
        }
    }

    /**
     * 这个方法只能在run 方法中实现，不然会阻塞主线程，导致页面无响应
     */
    void onPause() {
        synchronized (lock) {
            try {
                //线程 等待/阻塞
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        super.run();
        //标志线程开启
        //一直循环
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
        if (pause == true) {
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

    /**
     * 方式2
     */

    public Countdown() {

    }

    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
    }


}



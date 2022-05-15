package chessboard;


import chess.*;
import controller.ClickController;
import controller.Countdown;
import util.StepSaver;
import util.Step;


import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */

    private static final int CHESSBOARD_SIZE = 8;

    public static ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor=ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    public static int CHESS_SIZE;
    public static LinkedList<Step> stepList;


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        // FIXME: Initialize chessboard for testing only.
        initChess();
    }


    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }


    public void regretStep() {
        stepList = StepSaver.stepList;
        if (stepList != null) {
            Step step = stepList.pollLast();
            if (step != null) {
                ChessComponent[][] chessComponents1 = step.getChessComponents1();
                swapColor(step.getPlayer());
                Countdown.restart();
                //     initiateEmptyChessboard();
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        ChessComponent chess = chessComponents1[i][j];
                        if (chess instanceof RookChessComponent) {
                            RookOnBoard(i, j, chess.getChessColor());
                        } else if (chess instanceof QueenChessComponent) {
                            QueenOnBoard(i, j, chess.getChessColor());
                        } else if (chess instanceof KingChessComponent) {
                            KingOnBoard(i, j, chess.getChessColor());
                        } else if (chess instanceof BishopChessComponent) {
                            BishopOnBoard(i, j, chess.getChessColor());
                        } else if (chess instanceof KnightChessComponent) {
                            KnightOnBoard(i, j, chess.getChessColor());
                        } else if (chess instanceof PawnChessComponent) {
                            PawnOnBoard(i, j, chess.getChessColor());
                        } else if (chess instanceof EmptySlotComponent) {
                            EmptyOnBoard(i, j);
                        }
                        chessComponents[i][j].repaint();
                    }
                }
            }
        } else {

        }
    }

    public void simpleSwap(ChessComponent chess1, ChessComponent chess2) {
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        ChessComponent[][] chessComponents1 = recordComponents(chessComponents);
        Step oneStep = new Step(currentColor, chessComponents1);
        StepSaver.stepList.add(oneStep);
        simpleSwap(chess1, chess2);
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void EmptyOnBoard(int row, int col) {
        putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(row, col), calculatePoint(row, col), clickController, CHESS_SIZE));
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        ChessGameFrame.setStatusLabel(currentColor);
    }

    public void swapColor(ChessColor color) {
        currentColor = color;
        ChessGameFrame.setStatusLabel(currentColor);
    }

    private void RookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void KnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void BishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void QueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void KingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void PawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    public static Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessboard) {

        char player = chessboard.get(8).charAt(0);
        if (player == 'x') {
            swapColor(ChessColor.BLACK);
        } else {
            swapColor(ChessColor.WHITE);
        }
        ChessComponent[][] chessComponents2 = new ChessComponent[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char c = chessboard.get(i).charAt(j);
                ChessComponent chess = type(c, i, j);
                chessComponents2[i][j] = chess;
            }
        }
        StepSaver.initiate();
        Step board = new Step();
        board.setChessComponents(chessComponents2);

        if (player == 'x') {
            board.setPlayer(ChessColor.BLACK);
        } else {
            board.setPlayer(ChessColor.WHITE);
        }
        StepSaver.stepList.add(board);
        regretStep();
    }

    public void initChess() {
        initiateEmptyChessboard();
        RookOnBoard(0, 0, ChessColor.BLACK);
        RookOnBoard(0, 7, ChessColor.BLACK);
        RookOnBoard(7, 0, ChessColor.WHITE);
        RookOnBoard(7, 7, ChessColor.WHITE);
        KnightOnBoard(0, 1, ChessColor.BLACK);
        KnightOnBoard(0, 6, ChessColor.BLACK);
        KnightOnBoard(7, 1, ChessColor.WHITE);
        KnightOnBoard(7, 6, ChessColor.WHITE);
        BishopOnBoard(0, 2, ChessColor.BLACK);
        BishopOnBoard(0, 5, ChessColor.BLACK);
        BishopOnBoard(7, 2, ChessColor.WHITE);
        BishopOnBoard(7, 5, ChessColor.WHITE);
        QueenOnBoard(0, 3, ChessColor.BLACK);
        QueenOnBoard(7, 3, ChessColor.WHITE);
        KingOnBoard(0, 4, ChessColor.BLACK);
        KingOnBoard(7, 4, ChessColor.WHITE);
        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
            PawnOnBoard(1, i, ChessColor.BLACK);
        }
        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
            PawnOnBoard(6, i, ChessColor.WHITE);
        }
    }

    public ChessComponent[][] recordComponents(ChessComponent[][] chessComponents) {
        ChessComponent[][] chessComponents1 = new ChessComponent[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessComponent chess = chessComponents[i][j];
                int x = chess.getX();
                int y = chess.getY();
                ChessColor color = chess.getChessColor();
                ChessComponent chess1;
                if (chess instanceof RookChessComponent) {
                    chess1 = new RookChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), color, clickController, CHESS_SIZE);
                } else if (chess instanceof QueenChessComponent) {
                    chess1 = new QueenChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), color, clickController, CHESS_SIZE);
                } else if (chess instanceof KingChessComponent) {
                    chess1 = new KingChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), color, clickController, CHESS_SIZE);
                } else if (chess instanceof BishopChessComponent) {
                    chess1 = new BishopChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), color, clickController, CHESS_SIZE);
                } else if (chess instanceof KnightChessComponent) {
                    chess1 = new KnightChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), color, clickController, CHESS_SIZE);
                } else if (chess instanceof PawnChessComponent) {
                    chess1 = new PawnChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), color, clickController, CHESS_SIZE);
                } else {
                    chess1 = new EmptySlotComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), clickController, CHESS_SIZE);
                }
                chessComponents1[i][j] = chess1;
            }
        }
        return chessComponents1;
    }

    public ChessComponent type(char c, int x, int y) {
        switch (c) {
            case 'K':
                return new KingChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.BLACK, clickController, CHESS_SIZE);
            case 'k':
                return new KingChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.WHITE, clickController, CHESS_SIZE);
            case 'Q':
                return new QueenChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.BLACK, clickController, CHESS_SIZE);
            case 'q':
                return new QueenChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.WHITE, clickController, CHESS_SIZE);
            case 'R':
                return new RookChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.BLACK, clickController, CHESS_SIZE);
            case 'r':
                return new RookChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.WHITE, clickController, CHESS_SIZE);
            case 'B':
                return new BishopChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.BLACK, clickController, CHESS_SIZE);
            case 'b':
                return new BishopChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.WHITE, clickController, CHESS_SIZE);
            case 'N':
                return new KnightChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.BLACK, clickController, CHESS_SIZE);
            case 'n':
                return new KnightChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.WHITE, clickController, CHESS_SIZE);
            case 'P':
                return new PawnChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.BLACK, clickController, CHESS_SIZE);
            case 'p':
                return new PawnChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.WHITE, clickController, CHESS_SIZE);
        }
        return new EmptySlotComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), clickController, CHESS_SIZE);
    }


    public ChessComponent getChessComponents(int x,int y){
        return chessComponents[x][y];
    }
    public void removeSelect(){
        for (int i = 0; i <8 ; i++) {
            for (int j = 0; j <8 ; j++) {
                chessComponents[i][j].removeSelected();
            }
        }
    }

}
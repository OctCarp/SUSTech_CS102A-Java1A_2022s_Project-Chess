package chessboard;


import audio.AudioPlay;
import chess.*;
import controller.ClickController;
import controller.Countdown;
import util.StepSaver;
import util.Step;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static chessboard.ChessGameFrame.setStatusLabelCheck;

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
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    public static int CHESS_SIZE;
    public static LinkedList<Step> stepList;
    public static List<ChessboardPoint> CanMoveToW = new ArrayList<>();
    public static List<ChessboardPoint> CanMoveToB = new ArrayList<>();
    public static ChessComponent KingW;
    public static ChessComponent KingB;
    public static boolean kingSideB = true;
    public static boolean kingSideW = true;
    public static boolean queenSideB = true;
    public static boolean queenSideW = true;

    public ChessGameFrame chessGameFrame;
    public static int turn = 0;


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        StepSaver.initiate();
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

    public List<ChessboardPoint> getCanMoveToW() {
        return CanMoveToW;
    }

    public List<ChessboardPoint> getCanMoveToB() {
        return CanMoveToB;
    }

    public void setCanMoveToB() {
        List<ChessboardPoint> canMoveToB = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                for (int M = 0; M <= 7; M++) {
                    for (int N = 0; N <= 7; N++) {
                        if (chessComponents[i][j].getChessColor() == ChessColor.BLACK && chessComponents[i][j] instanceof PawnChessComponent) {
                            canMoveToB.add(offset(i, j, -1, 1));
                            canMoveToB.add(offset(i, j, 1, 1));
                        } else if (chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(M, N)) && chessComponents[i][j].getChessColor() == ChessColor.BLACK)
                            canMoveToB.add(new ChessboardPoint(M, N));
                    }
                }
            }
        }
        canMoveToB.removeIf(Objects::isNull);
        CanMoveToB = canMoveToB;
    }

    public void setCanMoveToW() {
        List<ChessboardPoint> canMoveToW = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                for (int M = 0; M <= 7; M++) {
                    for (int N = 0; N <= 7; N++) {
                        if (chessComponents[i][j].getChessColor() == ChessColor.WHITE && chessComponents[i][j] instanceof PawnChessComponent) {
                            canMoveToW.add(offset(i, j, -1, -1));
                            canMoveToW.add(offset(i, j, 1, -1));
                        }
                        if (chessComponents[i][j].canMoveTo(chessComponents, new ChessboardPoint(M, N)) && chessComponents[i][j].getChessColor() == ChessColor.WHITE)
                            canMoveToW.add(new ChessboardPoint(M, N));
                    }
                }
            }
        }
        canMoveToW.removeIf(Objects::isNull);
        CanMoveToW = canMoveToW;
    }

    public void regretStep() {
        stepList = StepSaver.stepList;
        if (stepList != null) {
            Step step = stepList.pollLast();
            if (step != null) {
                turn--;
                if (turn < 0) {
                    turn = 0;
                }
                queenSideB = step.isQueenSideB();
                kingSideB = step.isKingSideB();
                queenSideW = step.isQueenSideW();
                kingSideW = step.isKingSideW();
                ChessboardPoint chessboardPoint1 = step.getMovedChessPoint();
                ChessComponent[][] chessComponents1 = step.getChessComponents1();
                swapColor(step.getPlayer());
                removeSelect();
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
                        if (chessboardPoint1 != null) {
                            if (chessboardPoint1.getX() == i && chessboardPoint1.getY() == j) {
                                chessComponents[i][j].setMove(turn);
                            }
                        }
                        chessComponents[i][j].repaint();
                    }
                }
                System.out.println(turn);

                setCanMoveToB();
                setCanMoveToW();
                setStatusLabelCheck(this);
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
        /*ChessComponent[][] chessComponents1 = recordComponents(chessComponents);
        Step oneStep = new Step(currentColor, chessComponents1);
        StepSaver.stepList.add(oneStep);*/
        simpleSwap(chess1, chess2);
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public static boolean CheckKing(ChessComponent King) {
        if (King.getChessColor() == ChessColor.BLACK) {
            for (int i = 0; i < CanMoveToW.size(); i++) {
                if (CanMoveToW.get(i).getX() == King.getChessboardPoint().getX() && CanMoveToW.get(i).getY() == King.getChessboardPoint().getY())
                    return true;
            }
        } else {
            for (int i = 0; i < CanMoveToB.size(); i++) {
                if (CanMoveToB.get(i).getX() == King.getChessboardPoint().getX() && CanMoveToB.get(i).getY() == King.getChessboardPoint().getY())
                    return true;
            }
        }
        return false;
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
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, this);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        if (color == ChessColor.WHITE) {
            KingW = chessComponent;
        }
        if (color == ChessColor.BLACK) {
            KingB = chessComponent;
        }
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

        char player = chessboard.get(9).charAt(0);
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
        char queenSideB = chessboard.get(8).charAt(0);
        char kingSideB = chessboard.get(8).charAt(1);
        char queenSideW = chessboard.get(8).charAt(2);
        char kingSideW = chessboard.get(8).charAt(3);
        StepSaver.initiate();
        Step board = new Step();
        if (queenSideB == 'T') {
            board.setQueenSideB(true);
        } else {
            board.setQueenSideB(false);
        }
        if (kingSideB == 'T') {
            board.setKingSideB(true);
        } else {
            board.setKingSideB(false);
        }
        if (queenSideW == 't') {
            board.setQueenSideW(true);
        } else {
            board.setQueenSideW(false);
        }
        if (kingSideW == 't') {
            board.setKingSideW(true);
        } else {
            board.setKingSideW(false);
        }
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
                    chess1 = new KingChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), color, clickController, CHESS_SIZE, this);
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
                return new KingChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.BLACK, clickController, CHESS_SIZE, this);
            case 'k':
                return new KingChessComponent((new ChessboardPoint(x, y)), calculatePoint(x, y), ChessColor.WHITE, clickController, CHESS_SIZE, this);
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


    public ChessComponent getChessComponents(int x, int y) {
        return chessComponents[x][y];
    }

    public static ChessComponent getKingW() {
        return KingW;
    }

    public static ChessComponent getKingB() {
        return KingB;
    }

    public ClickController getClickController() {
        return clickController;
    }

    public void removeSelect() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponents[i][j].removeSelected();
            }
        }
    }

    public void removeAttacked() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponents[i][j].setAttacked(false);
                chessComponents[i][j].repaint();
            }
        }
    }

    public boolean Threaten(ChessboardPoint chessboardPoint, ChessColor color) {
        if (color == ChessColor.BLACK) {
            for (int i = 0; i < CanMoveToW.size(); i++) {
                if (CanMoveToW.get(i).getX() == chessboardPoint.getX() && CanMoveToW.get(i).getY() == chessboardPoint.getY())
                    return true;
            }
        } else {
            for (int i = 0; i < CanMoveToB.size(); i++) {
                if (CanMoveToB.get(i).getX() == chessboardPoint.getX() && CanMoveToB.get(i).getY() == chessboardPoint.getY())
                    return true;
            }
        }
        return false;
    }

    public void castling(ChessComponent King, ChessComponent Rook) {
        if (castle1(King, Rook)) {
            ChessComponent[][] chessComponents1 = recordComponents(chessComponents);
            Step oneStep = new Step(currentColor, chessComponents1);
            oneStep.setCastling(this);
            StepSaver.stepList.add(oneStep);
            swapChessComponents(King, chessComponents[King.getChessboardPoint().getX()][2]);
            swapChessComponents(Rook, chessComponents[King.getChessboardPoint().getX()][3]);
            swapColor();
            AudioPlay.playHit();


            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Chessboard.chessComponents[i][j].setSquareColor(Chessboard.chessComponents[i][j].getBackColor(new ChessboardPoint(i, j)));
                    chessComponents[i][j].setAttacked(false);
                    Chessboard.chessComponents[i][j].repaint();
                }
            }
            setCanMoveToW();
            setCanMoveToB();
            ChessGameFrame.setStatusLabelCheck(this);
            King.setSelected(false);
            Countdown.restart();
        } else if (castle2(King, Rook)) {
            ChessComponent[][] chessComponents1 = recordComponents(chessComponents);
            Step oneStep = new Step(currentColor, chessComponents1);
            oneStep.setCastling(this);
            StepSaver.stepList.add(oneStep);
            swapChessComponents(King, chessComponents[King.getChessboardPoint().getX()][6]);
            swapChessComponents(Rook, chessComponents[King.getChessboardPoint().getX()][5]);
            swapColor();
            AudioPlay.playHit();


            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Chessboard.chessComponents[i][j].setSquareColor(Chessboard.chessComponents[i][j].getBackColor(new ChessboardPoint(i, j)));
                    chessComponents[i][j].setAttacked(false);
                    Chessboard.chessComponents[i][j].repaint();
                }
            }
            setCanMoveToW();
            setCanMoveToB();
            ChessGameFrame.setStatusLabelCheck(this);
            King.setSelected(false);
            Countdown.restart();
        }
    }

    public boolean castle1(ChessComponent King, ChessComponent Rook) {
        if (Rook.getChessboardPoint().getY() == 0 && !Chessboard.CheckKing(King) && !Chessboard.CheckKing(Rook)) {
            if (chessComponents[King.getChessboardPoint().getX()][1] instanceof EmptySlotComponent
                    && chessComponents[King.getChessboardPoint().getX()][2] instanceof EmptySlotComponent
                    && chessComponents[King.getChessboardPoint().getX()][3] instanceof EmptySlotComponent) {
                return !Threaten(chessComponents[King.getChessboardPoint().getX()][1].getChessboardPoint(), King.getChessColor())
                        && !Threaten(chessComponents[King.getChessboardPoint().getX()][2].getChessboardPoint(), King.getChessColor())
                        && !Threaten(chessComponents[King.getChessboardPoint().getX()][3].getChessboardPoint(), King.getChessColor());
            } else return false;
        } else return false;
    }

    public boolean castle2(ChessComponent King, ChessComponent Rook) {
        if (Rook.getChessboardPoint().getY() == 7 && !Chessboard.CheckKing(King) && !Chessboard.CheckKing(Rook)) {
            if (!Threaten(chessComponents[King.getChessboardPoint().getX()][5].getChessboardPoint(), King.getChessColor())
                    && !Threaten(chessComponents[King.getChessboardPoint().getX()][6].getChessboardPoint(), King.getChessColor())) {
                return chessComponents[King.getChessboardPoint().getX()][5] instanceof EmptySlotComponent
                        && chessComponents[King.getChessboardPoint().getX()][6] instanceof EmptySlotComponent;
            } else return false;
        } else return false;
    }

    public ChessboardPoint offset(int i, int j, int dx, int dy) {
        int a = i + dx;
        int b = j + dy;
        if (a < 0 || a > 7 || b < 0 || b > 7) {
            return null;
        } else return new ChessboardPoint(a, b);
    }
}
package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BishopChessComponent extends ChessComponent {
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;
    private Image bishopImage;

    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() - destination.getX() == source.getY() - destination.getY()) {
            int initRow = Math.min(source.getX(), destination.getX());
            int initCol = Math.min(source.getY(), destination.getY());
            int destRow = Math.max(source.getX(), destination.getX());
            int destCol = Math.max(source.getY(), destination.getY());
            for (int i = initRow + 1, j = initCol + 1; i < destRow; i++, j++) {
                if (!(chessComponents[i][j] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
            return true;
        } else if (source.getX() - destination.getX() == destination.getY() - source.getY()) {
            int initRow = Math.min(source.getX(), destination.getX());
            int initCol = Math.min(source.getY(), destination.getY());
            int destRow = Math.max(source.getX(), destination.getX());
            int destCol = Math.max(source.getY(), destination.getY());
            for (int i = initRow + 1, j = destCol + 1; i < destRow; i++, j--) {
                if (!(chessComponents[i][j] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bishopImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}

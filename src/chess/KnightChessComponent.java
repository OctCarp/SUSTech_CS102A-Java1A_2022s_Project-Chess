package chess;

import chessboard.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class KnightChessComponent extends ChessComponent {

    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;
    private Image knightImage;

    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = ImageIO.read(new File("./resources/images/knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("./resources/images/knight-black.png"));
        }
    }

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        setName(color);
        initiateKnightImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if ((Math.abs(source.getX() - destination.getX()) == 2 && Math.abs(source.getY() - destination.getY()) == 1)
                || (Math.abs(source.getX() - destination.getX()) == 1 && Math.abs(source.getY() - destination.getY()) == 2)) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(knightImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
    @Override
    public void setName(ChessColor color) {
        if (color == ChessColor.BLACK) {
            this.name = 'N';
        } else {
            this.name = 'n';
        }
    }
    @Override
    public  void removeSelected(){
        getClickController().removeFirst(this);
    }
}

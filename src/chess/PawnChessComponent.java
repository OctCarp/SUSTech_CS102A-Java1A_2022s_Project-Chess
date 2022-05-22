package chess;

import chessboard.Chessboard;
import controller.ClickController;
import chessboard.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PawnChessComponent extends ChessComponent {
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private Image pawnImage;

    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./resources/images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./resources/images/pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        setName(color);
        initiatePawnImage(color);
    }
    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, int size) {
        super(chessboardPoint, location, color, size);
        initiatePawnImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (chessColor.getColor() == Color.BLACK) {
            if (source.getX() == 1 && source.getY() == destination.getY()) {
                if (destination.getX() == 2 ) {
                    if (!(chessComponents[2][destination.getY()] instanceof EmptySlotComponent)) {
                        return false;
                    }
                } else if (destination.getX() == 3) {
                    if (!(chessComponents[2][destination.getY()] instanceof EmptySlotComponent) ||
                            !(chessComponents[3][destination.getY()] instanceof EmptySlotComponent)) {
                        return false;
                    }
                } else return false;
            } else if (PassingSoldier(chessComponents,destination)){
                return true;
            }
            else if (source.getX() != 1 && source.getY() == destination.getY() && destination.getX() - source.getX() == 1) {
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                    return false;
                }
            } else if (destination.getX() - source.getX() == 1 && Math.abs(destination.getY() - source.getY()) == 1) {
                if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                    return false;
                }
            } else return false;
            return true;
        } else {
            if (source.getX() == 6 && source.getY() == destination.getY()) {
                    if (destination.getX() == 5 ) {
                        if (!(chessComponents[5][destination.getY()] instanceof EmptySlotComponent)) {
                            return false;
                        }
                    } else if (destination.getX() == 4) {
                        if (!(chessComponents[5][destination.getY()] instanceof EmptySlotComponent) ||
                                !(chessComponents[4][destination.getY()] instanceof EmptySlotComponent)) {
                            return false;
                        }
                    } else return false;
            } else if (PassingSoldier(chessComponents,destination)){
                return true;
            }
            else if (source.getX() != 7 && source.getY() == destination.getY() && destination.getX() - source.getX() == -1) {
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                    return false;
                }
            } else if (destination.getX() - source.getX() == -1 && Math.abs(destination.getY() - source.getY()) == 1) {
                if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                    return false;
                }
            } else return false;
        return true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(pawnImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
    @Override
    public void setName(ChessColor color) {
        if (color == ChessColor.BLACK) {
            this.name = 'P';
        } else {
            this.name = 'p';
        }
    }
    @Override
    public  void removeSelected(){
        getClickController().removeFirst(this);
    }

    public boolean PassingSoldier(ChessComponent[][] chessComponents, ChessboardPoint destination){
        ChessboardPoint source = getChessboardPoint();
        if (getChessColor()==ChessColor.WHITE){
            if (destination.getX()<7&&chessComponents[destination.getX()+1][destination.getY()]instanceof PawnChessComponent
                    &&chessComponents[destination.getX()+1][destination.getY()].getChessColor()==ChessColor.BLACK
                    &&destination.getX()+1==source.getX()&&chessComponents[destination.getX()+1][destination.getY()].getMove()== Chessboard.turn){
                return true;
            }else return false;
        }
        else {
            if (destination.getX()>0&&chessComponents[destination.getX()-1][destination.getY()]instanceof PawnChessComponent
                    &&chessComponents[destination.getX()-1][destination.getY()].getChessColor()==ChessColor.WHITE
                    &&destination.getX()-1==source.getX()&&chessComponents[destination.getX()-1][destination.getY()].getMove()==Chessboard.turn){
                return true;
            }else return false;
        }
    }
}

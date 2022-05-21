package chess;

import chessboard.ChessboardPoint;
import controller.ClickController;

import java.awt.*;
import java.io.IOException;

/**
 * 这个类表示棋盘上的空位置
 */
public class EmptySlotComponent extends ChessComponent {

    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location, ClickController listener, int size) {
        super(chessboardPoint, location, ChessColor.NONE, listener, size);
        setName(ChessColor.NONE);
    }

    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location, int size) {
        super(chessboardPoint, location, ChessColor.NONE, size);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        return false;
    }

    @Override
    public void loadResource() throws IOException {
        //No resource!
    }

    @Override
    public void setName(ChessColor color) {
        this.name = '_';
    }

    @Override
    public void removeSelected() {
    }
}

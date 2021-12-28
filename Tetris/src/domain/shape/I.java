package domain.shape;

import domain.Cell;
import domain.Tetromino;
import test.Tetris;

/**
 * @author zytwl
 *
 * 形状：
 *      □□□□
 */
public class I extends Tetromino {
    public I() {
        cells[0] = new Cell(0,4, Tetris.I);
        cells[1] = new Cell(0,3, Tetris.I);
        cells[2] = new Cell(0,5, Tetris.I);
        cells[3] = new Cell(0,6, Tetris.I);
    }
}

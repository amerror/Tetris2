package domain.shape;

import domain.Cell;
import domain.Tetromino;
import test.Tetris;

/**
 * @author zytwl
 *
 * 形状:
 *      □□□
 *       □
 */
public class T extends Tetromino {
    public T() {
        cells[0] = new Cell(0,4, Tetris.T);
        cells[1] = new Cell(0,3, Tetris.T);
        cells[2] = new Cell(0,5, Tetris.T);
        cells[3] = new Cell(1,4, Tetris.T);
    }
}

package domain.shape;

import domain.Cell;
import domain.Tetromino;
import test.Tetris;

/**
 * @author zytwl
 * 形状：
 *          □□
 *        □□
 */
public class S extends Tetromino {
    public S() {
        cells[0] = new Cell(0,4, Tetris.S);
        cells[1] = new Cell(0,5, Tetris.S);
        cells[2] = new Cell(1,3, Tetris.S);
        cells[3] = new Cell(1,4, Tetris.S);

    }
}

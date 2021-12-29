package domain.shape;

import domain.Cell;
import domain.Tetromino;
import test.Tetris;

/**
 * @author zytwl
 * 形状：
 *      □□□
 *      □
 */
public class L extends Tetromino {
    public L() {
        cells[0] = new Cell(0,4, Tetris.L);
        cells[1] = new Cell(0,3, Tetris.L);
        cells[2] = new Cell(0,4, Tetris.L);
        cells[3] = new Cell(1,3, Tetris.L);

        states = new State[4];
        states[0] = new State(0,0,0,-1,0,1,1,-1);
        states[1] = new State(0,0,-1,0,1,0,-1,-1);
        states[2] = new State(0,0,0,1,0,-1,-1,1);
        states[3] = new State(0,0,1,0,-1,0,1,1);
    }
}

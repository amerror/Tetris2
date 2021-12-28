package domain;

import domain.shape.*;

/**
 * @author zytwl
 *
 * 四方格父类
 * 属性：Cell数组用于创建4个小方块
 * 方法：左移一格，右移一格，下移一格，变形
 */
public class Tetromino {
    protected Cell[] cells = new Cell[4];

    /**
     * 向左移动一格
     */
    public void moveLeft(){
        for(Cell cell : cells){
            cell.left();
        }
    }

    /**
     * 向右移动一格
     */
    public void moveRight(){
        for(Cell cell : cells){
            cell.right();
        }
    }

    /**
     * 向下移动一格
     */
    public void softDrop(){
        for(Cell cell : cells){
            cell.drop();
        }
    }

    /**
     * 随机生成四方格
     */

    public static Tetromino randomOne(){
        int num =(int)( Math.random()*7);
        Tetromino tetromino = null;
        switch(num){
            case 0:
                tetromino = new I();
                break;
            case 1:
                tetromino = new J();
                break;
            case 2:
                tetromino = new L();
                break;
            case 3:
                tetromino = new O();
                break;
            case 4:
                tetromino = new S();
                break;
            case 5:
                tetromino = new T();
                break;
            case 6:
                tetromino = new Z();
                break;
        }
        return tetromino;
    }
}

package test;

import domain.Cell;
import domain.Tetromino;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author zytwl
 */
public class Tetris extends JPanel {
    /**
     *  声明正在下落的四方格
     */
    private Tetromino currentOne = Tetromino.randomOne();
    /**
     * 声明将要下落的四方格
     */
    private Tetromino nextOne = Tetromino.randomOne();
    /**
     * 声明游戏主区域
     */
    private Cell[][] wall = new Cell[18][9];


    public static BufferedImage I;
    public static BufferedImage J;
    public static BufferedImage L;
    public static BufferedImage O;
    public static BufferedImage S;
    public static BufferedImage T;
    public static BufferedImage Z;

    static {
        try {
            I = ImageIO.read(new File(".\\Tetris\\src\\images\\I.png"));
            J = ImageIO.read(new File(".\\Tetris\\src\\images\\J.png"));
            L = ImageIO.read(new File(".\\Tetris\\src\\images\\L.png"));
            O = ImageIO.read(new File(".\\Tetris\\src\\images\\O.png"));
            S = ImageIO.read(new File(".\\Tetris\\src\\images\\S.png"));
            T = ImageIO.read(new File(".\\Tetris\\src\\images\\T.png"));
            Z = ImageIO.read(new File(".\\Tetris\\src\\images\\Z.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /**
         * 创建窗口对象
         * 设置可见 setVisible
         * 设置窗口尺寸   setSize
         * 设置窗口居中   setLocationRelativeTo
         * 设置窗口关闭时程序终止  setDefaultCloseOperation
         */
        JFrame frame = new JFrame("俄罗斯方块");
        Tetris panel = new Tetris();
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(810,940);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


}

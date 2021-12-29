package test;

import domain.Cell;
import domain.Tetromino;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    /**
     * 声明单元格的值位48像素
     */
    private static final int CELL_SIZE =48;

    int[] scores_pool = {0,1,2,5,10};
    private int totalScore = 0;
    private int totalLine = 0;

    private static final int PLAYING = 0;
    private static final int PAUSE = 1;
    private static final int GAMEOVER = 2;
    private int game_state = 2;

    String[] show_state = {"P[pause]","C[continue]","S[replay]"};

    public static BufferedImage I;
    public static BufferedImage J;
    public static BufferedImage L;
    public static BufferedImage O;
    public static BufferedImage S;
    public static BufferedImage T;
    public static BufferedImage Z;
    public static BufferedImage backImage;

    static {
        try {
            I = ImageIO.read(new File(".\\Tetris\\src\\images\\I.png"));
            J = ImageIO.read(new File(".\\Tetris\\src\\images\\J.png"));
            L = ImageIO.read(new File(".\\Tetris\\src\\images\\L.png"));
            O = ImageIO.read(new File(".\\Tetris\\src\\images\\O.png"));
            S = ImageIO.read(new File(".\\Tetris\\src\\images\\S.png"));
            T = ImageIO.read(new File(".\\Tetris\\src\\images\\T.png"));
            Z = ImageIO.read(new File(".\\Tetris\\src\\images\\Z.png"));
            backImage = ImageIO.read(new File(".\\Tetris\\src\\images\\background.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(backImage,0,0,null);
        //平移坐标轴
        g.translate(22,15);
        //绘制游戏主区域
        paintWall(g);
        //绘制正在下落的四方格
        paintCurrentOne(g);
        //绘制从下一个将要下落的四方格
        paintNextOne(g);
        //绘制游戏得分
        paintScore(g);
        //绘制游戏状态
        paintState(g);
    }

    public void start(){
        game_state = PLAYING;
        KeyListener l = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                switch (code){
                    case KeyEvent.VK_DOWN:
                        sortDropAction();
                        break;
                    case KeyEvent.VK_LEFT:
                        moveLeftAction();
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveRightAction();
                        break;
                    case KeyEvent.VK_UP:
                        rotateRightAction();
                        break;
                    case KeyEvent.VK_SPACE:
                        handDropAction();
                        break;
                    case KeyEvent.VK_P:
                        if(game_state == PLAYING){
                            game_state = PAUSE;
                        }
                        break;
                    case KeyEvent.VK_C:
                        if(game_state == PAUSE){
                            game_state = PLAYING;
                        }
                        break;
                    case KeyEvent.VK_S:
                        game_state = PLAYING;
                        wall = new Cell[18][9];
                        currentOne = Tetromino.randomOne();
                        nextOne =Tetromino.randomOne();
                        totalScore = 0;
                        totalLine = 0;
                        break;
                }
            }
        };
        this.addKeyListener(l);
        this.requestFocus();

        while(true){
            if(game_state == PLAYING){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (canDrop()){
                    currentOne.softDrop();
                }else{
                    landToWall();
                    destroyLine();
                    if(isGameOver()){
                        game_state = GAMEOVER;
                    }else {
                        currentOne = nextOne;
                        nextOne = Tetromino.randomOne();
                    }
                }
            }
            repaint();
        }
    }

    public void rotateRightAction(){
        currentOne.rotateRight();
        if (outOfBounds() || coincide()){
            currentOne.rotateLeft();
        }
    }

    public void handDropAction(){
        while(true){
            if(canDrop()){
                currentOne.softDrop();
            }else{
                break;
            }
        }
        landToWall();
        destroyLine();
        if (isGameOver()){
            game_state = GAMEOVER;
        }else{
            currentOne = nextOne;
            nextOne = Tetromino.randomOne();
        }
    }

    public void sortDropAction(){
        if (canDrop()){
            currentOne.softDrop();
        }else{
            landToWall();
            destroyLine();
            if (isGameOver()){
                game_state = GAMEOVER;
            }else{
                currentOne = nextOne;
                nextOne = Tetromino.randomOne();
            }
        }
    }

    private void landToWall() {
        Cell[] cells = currentOne.cells;
        for(Cell cell : cells){
            int row = cell.getRow();
            int col = cell.getCol();
            wall[row][col] = cell;
        }
    }

    public boolean canDrop(){
        Cell[] cells = currentOne.cells;
        for(Cell cell : cells){
            int row = cell.getRow();
            int col = cell.getCol();
            if(row == wall.length -1){
                return false;
            }else if(wall[row + 1][col] != null){
                return false;
            }
        }
        return true;
    }

    public void destroyLine(){
        int line = 0;
        Cell[] cells = currentOne.cells;
        for(Cell cell : cells){
            int row = cell.getRow();
            if(ifFullLine(row)){
                line++;
                for(int i = row;i>0;i--){
                    System.arraycopy(wall[i-1],0,wall[i],0,wall[0].length);
                }
                wall[0] = new Cell[9];
            }
        }
        totalScore += scores_pool[line];
        totalLine += line;
    }

    public boolean ifFullLine(int row){
        Cell[] cells = wall[row];
        for (Cell cell : cells){
            if (cell == null){
                return false;
            }
        }
        return true;
    }

    public boolean isGameOver(){
        Cell[] cells = nextOne.cells;
        for(Cell cell : cells){
            int row = cell.getRow();
            int col = cell.getCol();
            if(wall[row][col] != null){
                return true;
            }
        }
        return false;
    }

    private void paintState(Graphics g) {
        g.drawString(show_state[game_state],500,600);
        if(game_state == GAMEOVER){
            g.setColor(Color.RED);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,60));
            g.drawString("GAMEOVER",50,400);
        }
    }

    private void paintScore(Graphics g) {
        g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        g.drawString("SCROES:"+totalScore,500,250);
        g.drawString("LINES:"+totalLine,500,440);
    }

    private void paintNextOne(Graphics g) {
        Cell[] cells = nextOne.cells;
        for(Cell cell : cells){
            int x = cell.getCol() * CELL_SIZE + 390;
            int y = cell.getRow() * CELL_SIZE + 25;
            g.drawImage(cell.getImage(),x,y,null);
        }
    }

    private void paintCurrentOne(Graphics g) {
        Cell[] cells = currentOne.cells;
        for(Cell cell : cells){
            int x = cell.getCol() * CELL_SIZE;
            int y = cell.getRow() * CELL_SIZE;
            g.drawImage(cell.getImage(),x,y,null);
        }
    }

    private void paintWall(Graphics g) {
        for(int i = 0;i < wall.length;i++){
            for (int j = 0;j<wall[i].length;j++){
                int x = j * CELL_SIZE;
                int y = i * CELL_SIZE;
                Cell cell = wall[i][j];
                if(cell == null){
                    g.drawRect(x,y,CELL_SIZE,CELL_SIZE);
                }else{
                    g.drawImage(cell.getImage(),x,y,null);
                }
            }
        }

    }

    private boolean outOfBounds(){
        Cell[] cells = currentOne.cells;
        for(Cell cell : cells){
            int col = cell.getCol();
            int row = cell.getRow();
            if(row < 0 || row >wall.length-1 || col<0 ||col>wall[1].length -1){
                return true;
            }
        }
        return false;
    }

    private boolean coincide(){
        Cell[] cells = currentOne.cells;
        for(Cell cell : cells){
            int col = cell.getCol();
            int row = cell.getRow();
            if (wall[row][col] != null){
                return true;
            }
        }
        return false;
    }

    public void moveLeftAction(){
        currentOne.moveLeft();
        if (outOfBounds() || coincide()){
            currentOne.moveRight();
        }
    }

    public void moveRightAction(){
        currentOne.moveRight();
        if (outOfBounds() || coincide()){
            currentOne.moveLeft();
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
        panel.start();
    }


}

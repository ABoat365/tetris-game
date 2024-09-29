package com.aboat365.tetris.core.block;

import com.aboat365.tetris.core.shape.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Aboat365
 * 方块
 */
public abstract class Tetromino {

    //是否被Hold住
    public Boolean isHold = false;
    //方块单元
    public Block[] blocks = new Block[4];
    //下一个方块角度状态
    public TetrominoState[] nextOneStates;
    //当前方块角度状态
    protected TetrominoState[] states;
    //当前方块状态
    private int state;
    //当前方块在7bag包中的位置
    private static int cursor = 0;
    //7bag包
    private final static List<Tetromino> tetrominoList = new ArrayList<>();

    public Boolean getHold() {
        return isHold;
    }

    public void setHold(Boolean hold) {
        isHold = hold;
    }

    public TetrominoState[] getNextOneStates() {
        return nextOneStates;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static int getCursor() {
        return cursor;
    }

    public static void setCursor(int cursor) {
        Tetromino.cursor = cursor;
    }

    /**
     * 左移
     */
    public void moveLeft() {
        for (Block block : blocks) {
            block.left();
        }
    }

    /**
     * 右移
     */
    public void moveRight() {
        for (Block block : blocks) {
            block.right();
        }
    }

    /**
     * 下落
     */
    public void moveDrop() {
        for (Block block : blocks) {
            block.down();
        }
    }

    /**
     * 随机方块角度
     */
    public void randomState() {
        if (states.length == 0) {
            state = 0;
            return;
        }
        Random random = new Random();
        int r = random.nextInt(states.length);
        putState(r);
    }

    /**
     * 改变方块的角度
     *
     * @param state
     */
    public void putState(int state) {
        if (states.length == 0) {
            return;
        }
        TetrominoState s = states[state];
        Block block = blocks[0];
        int row = block.getRow();
        int col = block.getCol();
        blocks[1].setRow(row + s.row1);
        blocks[1].setCol(col + s.col1);
        blocks[2].setRow(row + s.row2);
        blocks[2].setCol(col + s.col2);
        blocks[3].setRow(row + s.row3);
        blocks[3].setCol(col + s.col3);
    }

    /**
     * 根据7bag算法随机生成一个7种形状的方块包
     */
    public static void random() {
        tetrominoList.clear();
        tetrominoList.add(new I());
        tetrominoList.add(new J());
        tetrominoList.add(new L());
        tetrominoList.add(new O());
        tetrominoList.add(new S());
        tetrominoList.add(new T());
        tetrominoList.add(new Z());
        Collections.shuffle(tetrominoList);
        cursor = 0;
    }

    /**
     * 从7bag包中获取一个方块
     *
     * @return tetromino
     */
    public static Tetromino getTetromino() {
        if (tetrominoList.isEmpty()) {
            random();
        }
        Tetromino tetromino = tetrominoList.get(cursor);
        if (cursor == 6) {
            random();
        } else {
            cursor++;
        }
        return tetromino;
    }

    /**
     * 顺时针旋转方块角度
     */
    public void rotateRight() {
        if (states.length == 0) {
            return;
        }
        int c = state + 1;
        if (c > (states.length - 1)) {
            c = 0;
        }
        state = c;
        TetrominoState s = states[c];
        Block block = blocks[0];
        int row = block.getRow();
        int col = block.getCol();
        blocks[1].setRow(row + s.row1);
        blocks[1].setCol(col + s.col1);
        blocks[2].setRow(row + s.row2);
        blocks[2].setCol(col + s.col2);
        blocks[3].setRow(row + s.row3);
        blocks[3].setCol(col + s.col3);
    }

    /**
     * 逆时针旋转方块角度
     */
    public void rotateLeft() {
        if (states.length == 0) {
            return;
        }
        int c = state - 1;
        if (c < 0) {
            c = states.length - 1;
        }
        state = c;
        TetrominoState s = states[c];
        Block block = blocks[0];
        int row = block.getRow();
        int col = block.getCol();
        blocks[1].setRow(row + s.row1);
        blocks[1].setCol(col + s.col1);
        blocks[2].setRow(row + s.row2);
        blocks[2].setCol(col + s.col2);
        blocks[3].setRow(row + s.row3);
        blocks[3].setCol(col + s.col3);
    }

    /**
     * 初始化方块
     */
    public abstract void init();
}

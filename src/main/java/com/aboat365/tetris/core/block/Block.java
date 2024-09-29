package com.aboat365.tetris.core.block;

/**
 * @author Aboat365
 * 方格
 */
public class Block {
    // 行
    private int row;
    // 列
    private int col;

    public Block(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //左移动一格
    public void left() {
        col--;
    }

    //右移动一格
    public void right() {
        col++;
    }

    //下移动一格
    public void down() {
        row++;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}

package com.aboat365.tetris.core.block;

/**
 * @author Aboat365
 * 方块角度状态
 */
public class TetrominoState {
    int row0, col0, row1, col1, row2, col2, row3, col3;

    public TetrominoState(int row0, int col0, int row1, int col1, int row2, int col2, int row3, int col3) {
        this.row0 = row0;
        this.col0 = col0;
        this.row1 = row1;
        this.col1 = col1;
        this.row2 = row2;
        this.col2 = col2;
        this.row3 = row3;
        this.col3 = col3;
    }

    public int getRow0() {
        return row0;
    }

    public int getCol0() {
        return col0;
    }

    public int getRow1() {
        return row1;
    }

    public int getCol1() {
        return col1;
    }

    public int getRow2() {
        return row2;
    }

    public int getCol2() {
        return col2;
    }

    public int getRow3() {
        return row3;
    }

    public int getCol3() {
        return col3;
    }
}

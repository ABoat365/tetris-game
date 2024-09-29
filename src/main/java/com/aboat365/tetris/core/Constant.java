package com.aboat365.tetris.core;

/**
 * @author Aboat365
 */
public interface Constant {
    /**
     * 游戏主界面行
     */
    int ROW = 20;

    /**
     * 游戏主界面列
     */
    int COL = 10;

    /**
     * 方格宽度
     */
    int SQUARE_WIDTH = 20;

    /**
     * 游戏消行得分
     * 0行 0分
     * 1行 10分
     * 2行 30分
     * 3行 50分
     * 4行 80分
     */
    int[] SCORES_POOL = {0, 10, 30, 50, 80};
}

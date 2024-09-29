package com.aboat365.tetris.core.shape;

import com.aboat365.tetris.core.block.Block;
import com.aboat365.tetris.core.block.Tetromino;
import com.aboat365.tetris.core.block.TetrominoState;

/**
 * @author Aboat365
 */
public class S extends Tetromino {
    public S() {
        init();
        //共有两种旋转状态
        states = new TetrominoState[2];
        //初始化两种状态的相对坐标
        states[0] = new TetrominoState(0, 0, 0, 1, 1, -1, 1, 0);
        states[1] = new TetrominoState(0, 0, 1, 0, -1, -1, 0, -1);

        this.randomState();

        nextOneStates = new TetrominoState[2];
        nextOneStates[0] = new TetrominoState(2, 1, 2, 2, 1, 2, 1, 3);
        nextOneStates[1] = new TetrominoState(1, 1, 2, 1, 2, 2, 3, 2);
    }

    @Override
    public void init() {
        blocks[0] = new Block(-1, 4);
        blocks[1] = new Block(-1, 5);
        blocks[2] = new Block(0, 3);
        blocks[3] = new Block(0, 4);
    }
}

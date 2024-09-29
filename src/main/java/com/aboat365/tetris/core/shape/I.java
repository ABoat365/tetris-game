package com.aboat365.tetris.core.shape;

import com.aboat365.tetris.core.block.Block;
import com.aboat365.tetris.core.block.Tetromino;
import com.aboat365.tetris.core.block.TetrominoState;

/**
 * @author Aboat365
 */
public class I extends Tetromino {

    public I() {
        init();
        //共有两种旋转状态
        states = new TetrominoState[2];
        //初始化两种状态的相对坐标
        states[0] = new TetrominoState(0, 0, 0, -1, 0, 1, 0, 2);
        states[1] = new TetrominoState(0, 0, -1, 0, 1, 0, 2, 0);
        this.randomState();

        nextOneStates = new TetrominoState[2];
        nextOneStates[0] = new TetrominoState(1, 0, 1, 1, 1, 2, 1, 3);
        nextOneStates[1] = new TetrominoState(0, 1, 1, 1, 2, 1, 3, 1);
    }

    @Override
    public void init() {
        blocks[0] = new Block(0, 4);
        blocks[1] = new Block(0, 3);
        blocks[2] = new Block(0, 5);
        blocks[3] = new Block(0, 6);
    }
}

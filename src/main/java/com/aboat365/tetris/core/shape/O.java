package com.aboat365.tetris.core.shape;

import com.aboat365.tetris.core.block.Block;
import com.aboat365.tetris.core.block.Tetromino;
import com.aboat365.tetris.core.block.TetrominoState;

/**
 * @author Aboat365
 */
public class O extends Tetromino {
    public O() {
        init();
        //无旋转状态
        states = new TetrominoState[0];

        nextOneStates = new TetrominoState[1];
        nextOneStates[0] = new TetrominoState(1, 1, 1, 2, 2, 2, 2, 1);
    }

    @Override
    public void init() {
        blocks[0] = new Block(-1, 4);
        blocks[1] = new Block(-1, 5);
        blocks[2] = new Block(0, 4);
        blocks[3] = new Block(0, 5);
    }
}

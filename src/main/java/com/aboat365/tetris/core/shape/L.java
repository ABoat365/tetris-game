package com.aboat365.tetris.core.shape;

import com.aboat365.tetris.core.block.Block;
import com.aboat365.tetris.core.block.Tetromino;
import com.aboat365.tetris.core.block.TetrominoState;

/**
 * @author Aboat365
 */
public class L extends Tetromino {
    public L() {
        init();
        states = new TetrominoState[4];
        states[0] = new TetrominoState(0, 0, 0, -1, 0, 1, 1, -1);
        states[1] = new TetrominoState(0, 0, -1, 0, 1, 0, -1, -1);
        states[2] = new TetrominoState(0, 0, 0, 1, 0, -1, -1, 1);
        states[3] = new TetrominoState(0, 0, 1, 0, -1, 0, 1, 1);
        this.randomState();

        nextOneStates = new TetrominoState[4];
        nextOneStates[0] = new TetrominoState(2, 1, 1, 1, 1, 2, 1, 3);
        nextOneStates[1] = new TetrominoState(0, 1, 0, 2, 1, 2, 2, 2);
        nextOneStates[2] = new TetrominoState(1, 3, 2, 3, 2, 2, 2, 1);
        nextOneStates[3] = new TetrominoState(0, 1, 1, 1, 2, 1, 2, 2);
    }

    @Override
    public void init() {
        blocks[0] = new Block(-1, 4);
        blocks[1] = new Block(-1, 3);
        blocks[2] = new Block(-1, 5);
        blocks[3] = new Block(0, 3);
    }
}

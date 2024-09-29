package com.aboat365.tetris.storage;

import java.awt.event.KeyEvent;

/**
 * @author Aboat365
 * 键盘映射对象
 */
public class KeyMappingState {
    private int restart = KeyEvent.VK_R;

    private int handDrop = KeyEvent.VK_SPACE;
    private int sortDrop = KeyEvent.VK_DOWN;

    private int moveRight = KeyEvent.VK_RIGHT;
    private int moveLeft = KeyEvent.VK_LEFT;

    private int rotateRight = KeyEvent.VK_UP;
    private int rotateLeft = KeyEvent.VK_Z;

    private int hold = KeyEvent.VK_C;

    public int getRestart() {
        return restart;
    }

    public void setRestart(int restart) {
        this.restart = restart;
    }

    public int getHandDrop() {
        return handDrop;
    }

    public void setHandDrop(int handDrop) {
        this.handDrop = handDrop;
    }

    public int getSortDrop() {
        return sortDrop;
    }

    public void setSortDrop(int sortDrop) {
        this.sortDrop = sortDrop;
    }

    public int getMoveRight() {
        return moveRight;
    }

    public void setMoveRight(int moveRight) {
        this.moveRight = moveRight;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(int moveLeft) {
        this.moveLeft = moveLeft;
    }

    public int getRotateRight() {
        return rotateRight;
    }

    public void setRotateRight(int rotateRight) {
        this.rotateRight = rotateRight;
    }

    public int getRotateLeft() {
        return rotateLeft;
    }

    public void setRotateLeft(int rotateLeft) {
        this.rotateLeft = rotateLeft;
    }

    public int getHold() {
        return hold;
    }

    public void setHold(int hold) {
        this.hold = hold;
    }
}

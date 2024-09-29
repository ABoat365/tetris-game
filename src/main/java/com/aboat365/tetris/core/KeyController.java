package com.aboat365.tetris.core;

import com.aboat365.tetris.storage.KeyMapping;
import com.aboat365.tetris.storage.KeyMappingState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Aboat365
 * 键盘控制
 */
public class KeyController implements KeyListener {

    private KeyMappingState keyMappingState;

    @Override
    public void keyPressed(KeyEvent e) {
        if (keyMappingState == null) {
            keyMappingState = KeyMapping.getInstance().getState();
        }
        if (keyMappingState == null) {
            return;
        }
        Engine engine = Engine.getInstance();
        int keyCode = e.getKeyCode();

        if (keyMappingState.getRestart() == keyCode) {
            engine.restart();
            return;
        }

        if (keyMappingState.getHandDrop() == keyCode) {
            engine.hardDropActive();
            return;
        }

        if (keyMappingState.getSortDrop() == keyCode) {
            engine.sortDropActive();
            return;
        }

        if (keyMappingState.getMoveRight() == keyCode) {
            engine.moveRightActive();
            return;
        }

        if (keyMappingState.getMoveLeft() == keyCode) {
            engine.moveLeftActive();
            return;
        }

        if (keyMappingState.getRotateRight() == keyCode) {
            engine.rotateRightActive();
            return;
        }

        if (keyMappingState.getRotateLeft() == keyCode) {
            engine.rotateLeftActive();
            return;
        }

        if (keyMappingState.getHold() == keyCode) {
            engine.holdActive();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

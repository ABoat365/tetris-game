package com.aboat365.tetris.ui;

import com.aboat365.tetris.storage.KeyMapping;
import com.aboat365.tetris.storage.KeyMappingState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aboat365
 */
public class KeyMappingForm extends DialogWrapper {
    private JPanel keyMappingFormPanel;
    private ShortcutTextField restart;
    private ShortcutTextField handDrop;
    private ShortcutTextField sortDrop;
    private ShortcutTextField moveRight;
    private ShortcutTextField moveLeft;
    private ShortcutTextField rotateRight;
    private ShortcutTextField rotateLeft;
    private ShortcutTextField hold;

    public KeyMappingForm(@Nullable Project project, boolean canBeParent) {
        super(project, canBeParent);
        init();
        this.setSize(345, 275);
        this.setTitle("Keyboard Mapping");
    }

    public void createUIComponents() {
        restart = new ShortcutTextField();
        handDrop = new ShortcutTextField();
        sortDrop = new ShortcutTextField();
        moveRight = new ShortcutTextField();
        moveLeft = new ShortcutTextField();
        rotateRight = new ShortcutTextField();
        rotateLeft = new ShortcutTextField();
        hold = new ShortcutTextField();

        KeyMappingState keyMappingState = KeyMapping.getInstance().getState();
        if (keyMappingState != null) {
            restart.setKeyStroke(KeyStroke.getKeyStroke(keyMappingState.getRestart(), 0));
            handDrop.setKeyStroke(KeyStroke.getKeyStroke(keyMappingState.getHandDrop(), 0));
            sortDrop.setKeyStroke(KeyStroke.getKeyStroke(keyMappingState.getSortDrop(), 0));
            moveRight.setKeyStroke(KeyStroke.getKeyStroke(keyMappingState.getMoveRight(), 0));
            moveLeft.setKeyStroke(KeyStroke.getKeyStroke(keyMappingState.getMoveLeft(), 0));
            rotateRight.setKeyStroke(KeyStroke.getKeyStroke(keyMappingState.getRotateRight(), 0));
            rotateLeft.setKeyStroke(KeyStroke.getKeyStroke(keyMappingState.getRotateLeft(), 0));
            hold.setKeyStroke(KeyStroke.getKeyStroke(keyMappingState.getHold(), 0));
        }
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return keyMappingFormPanel;
    }

    @Override
    protected @NotNull JPanel createButtonsPanel(@NotNull List<? extends JButton> buttons) {
        List<JButton> buttonList = new ArrayList<>(buttons);
        JButton resetButton = new JButton();
        resetButton.setText("Reset");
        resetButton.addActionListener(e -> reset());
        buttonList.add(resetButton);
        return super.createButtonsPanel(buttonList);
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        KeyMappingState keyMappingState = KeyMapping.getInstance().getState();
        if (keyMappingState != null) {
            keyMappingState.setRestart(restart.getKeyStroke().getKeyCode());
            keyMappingState.setHandDrop(handDrop.getKeyStroke().getKeyCode());
            keyMappingState.setSortDrop(sortDrop.getKeyStroke().getKeyCode());
            keyMappingState.setMoveRight(moveRight.getKeyStroke().getKeyCode());
            keyMappingState.setMoveLeft(moveLeft.getKeyStroke().getKeyCode());
            keyMappingState.setRotateRight(rotateRight.getKeyStroke().getKeyCode());
            keyMappingState.setRotateLeft(rotateLeft.getKeyStroke().getKeyCode());
            keyMappingState.setHold(hold.getKeyStroke().getKeyCode());
        }
    }

    private void reset() {
        restart.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
        handDrop.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
        sortDrop.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
        moveRight.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
        moveLeft.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
        rotateRight.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
        rotateLeft.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0));
        hold.setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
    }
}

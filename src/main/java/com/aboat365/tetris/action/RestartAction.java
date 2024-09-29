package com.aboat365.tetris.action;

import com.aboat365.tetris.core.Draw;
import com.aboat365.tetris.core.Engine;
import com.aboat365.tetris.core.GameState;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.DumbAware;
import org.jetbrains.annotations.NotNull;

/**
 * @author Aboat365
 * 游戏重新开始操作
 */
public class RestartAction extends AnAction implements DumbAware {
    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Draw.getInstance().getCanvas().requestFocusInWindow();
        Engine.getInstance().restart();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        GameState gameState = Engine.getInstance().getGameState();
        presentation.setEnabled(!GameState.NOT_START.equals(gameState));
    }
}

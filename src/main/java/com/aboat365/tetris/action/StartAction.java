package com.aboat365.tetris.action;

import com.aboat365.tetris.core.Draw;
import com.aboat365.tetris.core.Engine;
import com.aboat365.tetris.core.GameState;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.DumbAware;
import org.jetbrains.annotations.NotNull;

/**
 * @author Aboat365
 * 开始游戏操作
 */
public class StartAction extends AnAction implements DumbAware {
    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Draw.getInstance().getCanvas().requestFocusInWindow();
        Engine.getInstance().start();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        GameState gameState = Engine.getInstance().getGameState();
        if (GameState.NOT_START.equals(gameState) || GameState.GAME_OVER.equals(gameState)) {
            presentation.setIcon(AllIcons.Actions.Execute);
        } else if (GameState.PLAYING.equals(gameState)) {
            presentation.setIcon(AllIcons.Actions.Suspend);
        } else {
            presentation.setIcon(AllIcons.Actions.Pause);
        }
    }
}

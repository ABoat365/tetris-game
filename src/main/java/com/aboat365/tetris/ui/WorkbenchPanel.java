package com.aboat365.tetris.ui;

import com.aboat365.tetris.core.Draw;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.ui.SimpleToolWindowPanel;

/**
 * @author Aboat365
 */
public class WorkbenchPanel extends SimpleToolWindowPanel {

    private static class create {
        static WorkbenchPanel instance = new WorkbenchPanel();
    }

    public static WorkbenchPanel getInstance() {
        return WorkbenchPanel.create.instance;
    }

    public WorkbenchPanel() {
        super(true, true);
        DefaultActionGroup group = (DefaultActionGroup) ActionManager.getInstance().getAction("tetrisAction");
        ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.TOOLBAR, group, true);
        actionToolbar.setTargetComponent(this.getComponent());
        setToolbar(actionToolbar.getComponent());
        setContent(Draw.getInstance().getCanvas());
    }

}


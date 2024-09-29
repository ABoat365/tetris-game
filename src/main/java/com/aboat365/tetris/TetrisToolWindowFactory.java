package com.aboat365.tetris;

import com.aboat365.tetris.ui.WorkbenchPanel;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author Aboat365
 */
public class TetrisToolWindowFactory implements ToolWindowFactory, DumbAware {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentManager contentManager = toolWindow.getContentManager();
        WorkbenchPanel workbenchPanel = WorkbenchPanel.getInstance();
        Content content = ContentFactory.getInstance().createContent(workbenchPanel, "", false);
        contentManager.addContent(content);
    }
}

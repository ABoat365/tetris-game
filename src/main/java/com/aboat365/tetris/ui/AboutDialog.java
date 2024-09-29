package com.aboat365.tetris.ui;

import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Aboat365
 */
public class AboutDialog extends DialogWrapper implements HyperlinkListener {

    private final JPanel panel = new JPanel(new BorderLayout());

    public AboutDialog() {
        super(ProjectManager.getInstance().getDefaultProject());
        init();
        this.setSize(800, 550);
        this.setTitle("About");
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        textPane.addHyperlinkListener(this);
        String text = "<p>俄罗斯方块是一个经典休闲小游戏，让你在忙碌的工作中放松一下。安装完后在右侧工具栏会出现一个新的选项卡，点击打开就可以开始游戏了。默认使用方向键控制，空格键硬降，R键重新开始，支持自定义按键。<br>推荐两款生产力工具：</p><ul><li><a href=\"https://plugins.jetbrains.com/plugin/23146-feign-helper\">Feign-Helper</a></li><li><a href=\"https://plugins.jetbrains.com/plugin/23561-database-buddy\">Database Buddy</a></li></ul><p>Tetris is a classic casual game that allows you to relax in your busy work.After installation,a new TAB will displayed in the right toolbar,click open to start the game.Default use the arrow keys controls,the space key to hard drop,and the R key to restart,Support custom keys.<br>Two productivity tools are recommended:</p><ul><li><a href=\"https://plugins.jetbrains.com/plugin/23146-feign-helper\">Feign-Helper</a></li><li><a href=\"https://plugins.jetbrains.com/plugin/23561-database-buddy\">Database Buddy</a></li></ul>";
        textPane.setText(text);
        JScrollPane textScrollPane = new JBScrollPane(textPane);
        panel.add(textScrollPane);
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return panel;
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
                Desktop.getDesktop().browse(e.getURL().toURI());
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

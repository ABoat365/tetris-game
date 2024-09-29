package com.aboat365.tetris.ui;

import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.registry.Registry;
import com.intellij.ui.KeyStrokeAdapter;
import com.intellij.ui.components.fields.ExtendableTextField;
import com.intellij.util.ui.accessibility.ScreenReader;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Aboat365
 * 键盘快捷键设置输入框
 */
public class ShortcutTextField extends ExtendableTextField {
    private KeyStroke myKeyStroke;
    private int myLastPressedKeyCode = KeyEvent.VK_UNDEFINED;


    /**
     * 启用键盘事件，设置焦点穿越键的启用状态，初始化扩展和护理。
     * 这些初始化操作包括：
     * 1. 启用AWT事件中的键盘事件。
     * 2. 根据isFocusTraversalKeysEnabled参数设置焦点穿越键的启用状态。
     * 3. 设置一个默认的护理对象，其可见性被重写为始终不可见。
     */
    ShortcutTextField() {
        boolean isFocusTraversalKeysEnabled = true;
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        setFocusTraversalKeysEnabled(isFocusTraversalKeysEnabled);
        setCaret(new DefaultCaret() {
            @Override
            public boolean isVisible() {
                return false;
            }
        });
    }

    /**
     * 判断按键事件是否为绝对未知的键。
     * 这个方法通过检查按键代码、字符和位置是否都为未知或未定义来判断。
     *
     * @param e 按键事件
     * @return 如果按键的所有标识特征都为未知或未定义，则返回true；否则返回false。
     */
    private static boolean absolutelyUnknownKey(KeyEvent e) {
        return e.getKeyCode() == 0
                && e.getKeyChar() == KeyEvent.CHAR_UNDEFINED
                && e.getKeyLocation() == KeyEvent.KEY_LOCATION_UNKNOWN
                && e.getExtendedKeyCode() == 0;
    }

    /**
     * 处理按键事件，覆盖自父类。
     * 该方法主要负责处理各种按键事件，包括按键按下、释放和焦点的处理。
     * 它确保了特定的按键（如ESC和ENTER）的处理，同时忽略了一些修饰键的事件。
     *
     * @param e 按键事件
     */
    @Override
    protected void processKeyEvent(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (getFocusTraversalKeysEnabled() && e.getModifiersEx() == 0) {
            if (keyCode == KeyEvent.VK_ESCAPE || (keyCode == KeyEvent.VK_ENTER && myKeyStroke != null)) {
                super.processKeyEvent(e);
                return;
            }
        }

        final boolean isNotModifierKey = keyCode != KeyEvent.VK_SHIFT &&
                keyCode != KeyEvent.VK_ALT &&
                keyCode != KeyEvent.VK_CONTROL &&
                keyCode != KeyEvent.VK_ALT_GRAPH &&
                keyCode != KeyEvent.VK_META &&
                !absolutelyUnknownKey(e);

        if (isNotModifierKey) {
            // NOTE: when user presses 'Alt + Right' at Linux the IDE can receive next sequence KeyEvents: ALT_PRESSED -> RIGHT_RELEASED ->  ALT_RELEASED
            // RIGHT_PRESSED can be skipped, it depends on WM
            if (
                    e.getID() == KeyEvent.KEY_PRESSED
                            || (e.getID() == KeyEvent.KEY_RELEASED &&
                            SystemInfo.isLinux && (e.isAltDown() || e.isAltGraphDown()) && myLastPressedKeyCode != keyCode) // press-event was skipped
            ) {
                setKeyStroke(KeyStrokeAdapter.getDefaultKeyStroke(e));
            }

            if (e.getID() == KeyEvent.KEY_PRESSED)
                myLastPressedKeyCode = keyCode;
        }

        // Ensure TAB/Shift-TAB work as focus traversal keys, otherwise
        // there is no proper way to move the focus outside the text field.
        if (!getFocusTraversalKeysEnabled() && ScreenReader.isActive()) {
            setFocusTraversalKeysEnabled(true);
            try {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().processKeyEvent(this, e);
            } finally {
                setFocusTraversalKeysEnabled(false);
            }
        }
    }

    /**
     * 获取当前的按键行程。
     *
     * @return 当前的按键行程
     */
    KeyStroke getKeyStroke() {
        return myKeyStroke;
    }

    /**
     * 设置当前的按键行程。
     * 这个方法用来更新当前存储的按键行程，并触发相应的属性变更事件。
     *
     * @param keyStroke 要设置的按键行程
     */
    void setKeyStroke(KeyStroke keyStroke) {
        KeyStroke old = myKeyStroke;
        if (old != null || keyStroke != null) {
            myKeyStroke = keyStroke;
            super.setText(KeymapUtil.getKeystrokeText(keyStroke));
            setCaretPosition(0);
            firePropertyChange("keyStroke", old, keyStroke);
        }
    }

    /**
     * 启用或禁用输入方法。
     * 这个方法覆盖自父类，根据enable参数和注册表设置来决定是否启用输入方法。
     *
     * @param enable 是否启用输入方法的标志
     */
    @Override
    public void enableInputMethods(boolean enable) {
        super.enableInputMethods(enable && Registry.is("ide.settings.keymap.input.method.enabled"));
    }

    /**
     * 设置文本字段的文本。
     * 除了设置文本外，该方法还会调整 caret 的位置，并在文本为空时重置按键行程。
     *
     * @param text 要设置的文本
     */
    @Override
    public void setText(String text) {
        super.setText(text);
        setCaretPosition(0);
        if (text == null || text.isEmpty()) {
            myKeyStroke = null;
            firePropertyChange("keyStroke", null, null);
        }
    }

}

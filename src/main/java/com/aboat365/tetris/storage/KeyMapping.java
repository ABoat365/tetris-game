package com.aboat365.tetris.storage;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Aboat365
 * 键盘映射配置
 */
@State(name = "KeyMapping", storages = @Storage("tetris-game-key-mapping.xml"))
public class KeyMapping implements PersistentStateComponent<KeyMappingState> {

    private KeyMappingState keyMappingState = new KeyMappingState();

    /**
     * 持久化配置数据
     */
    @Override
    public @Nullable KeyMappingState getState() {
        return keyMappingState;
    }

    /**
     * 加载配置数据
     */
    @Override
    public void loadState(@NotNull KeyMappingState keyMappingState) {
        this.keyMappingState = keyMappingState;
    }

    public static KeyMapping getInstance() {
        return ApplicationManager.getApplication().getService(KeyMapping.class);
    }
}

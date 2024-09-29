package com.aboat365.tetris.util;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Aboat365
 */
public class FontUtil {

    /**
     * 获取自定义的字体
     * <p>
     * 此方法用于加载项目中的自定义字体文件，并根据指定的字体大小创建字体对象。
     * 它首先设置当前线程的上下文类加载器以确保字体文件能被正确加载。
     * 然后，它从资源目录中获取字体文件的输入流。
     * 如果字体文件加载成功，它将使用指定的字体大小创建一个新的字体对象并返回。
     * 如果在加载过程中发生FontFormatException或IOException，则包装在RuntimeException中抛出。
     *
     * @param fontSize 字体大小，用于指定所创建字体的大小
     * @return 返回一个Font对象，代表了自定义字体和指定大小的字体
     */
    public static Font getConfigFont(float fontSize) {
        // 设置当前线程的上下文类加载器，以便能够正确加载字体文件
        Thread.currentThread().setContextClassLoader(FontUtil.class.getClassLoader());
        // 从资源目录中获取字体文件的输入流
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("font/font.ttf");

        Font definedFont = null;
        // 尝试使用输入流创建字体对象
        try {
            if (is != null) {
                definedFont = Font.createFont(Font.TRUETYPE_FONT, is);
            }
        } catch (FontFormatException | IOException e) {
            // 如果字体创建过程中发生异常，抛出运行时异常
            throw new RuntimeException(e);
        }
        // 使用指定的字体大小创建一个新的字体对象并返回
        if (definedFont != null) {
            definedFont = definedFont.deriveFont(fontSize);
        }
        return definedFont;
    }

}

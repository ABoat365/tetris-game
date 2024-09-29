package com.aboat365.tetris.core;

import com.aboat365.tetris.core.block.Block;
import com.aboat365.tetris.core.block.Tetromino;
import com.aboat365.tetris.core.block.TetrominoState;
import com.aboat365.tetris.util.FontUtil;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Aboat365
 * 绘图
 */
public class Draw {
    private final JPanel canvas;

    public JPanel getCanvas() {
        return canvas;
    }

    private static class create {
        static Draw instance = new Draw();
    }

    public static Draw getInstance() {
        return Draw.create.instance;
    }

    public Draw() {
        canvas = new JPanel() {
            @Override
            public void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                paintBackground(graphics);

                Graphics2D g2d = (Graphics2D) graphics;
                paintSource(g2d);
                paintCell(graphics);
                paintWall(graphics);
            }
        };
        canvas.addKeyListener(new KeyController());
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                canvas.requestFocusInWindow();
            }
        });
    }


    /**
     * 绘制背景
     *
     * @param g
     */
    private void paintBackground(Graphics g) {
        g.setColor(JBColor.GRAY);
        g.drawRect(0, 0, 204, 404);
        for (int i = 0; i < Constant.COL; i++) {
            for (int j = 0; j < Constant.ROW; j++) {
                drawSquare(g, j, i, UIUtil.getBoundsColor());
            }
        }
    }

    /**
     * 绘制分数,框间隔5，文字上下间隔2，与框距离4
     * 总高404
     *
     * @param g
     */
    private void paintSource(Graphics2D g) {
        Engine engine = Engine.getInstance();
        g.setColor(JBColor.GRAY);
        g.setFont(FontUtil.getConfigFont(23));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.drawRect(207, 0, 94, 52);
        g.drawString("SCORE", 212, 24);
        g.drawString(String.valueOf(engine.getTotalScore()), 212, 48);

        g.drawRect(207, 57, 94, 52);
        g.drawString("LINES", 212, 79);
        g.drawString(String.valueOf(engine.getTotalLine()), 212, 105);

        g.drawRect(207, 114, 94, 52);
        g.drawString("LEVEL", 212, 134);
        g.drawString(String.valueOf(engine.getLevel()), 212, 162);

        paintNext(g);
        paintHold(g);
    }

    /**
     * 绘制下一个
     * 方块与字体间隔7
     *
     * @param g
     */
    private void paintNext(Graphics2D g) {
        Engine engine = Engine.getInstance();
        g.setColor(JBColor.GRAY);
        g.drawRect(207, 171, 94, 114);
        g.drawString("NEXT", 212, 195);
        g.setColor(UIUtil.getBoundsColor());
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                g.drawRect(212 + j * Constant.SQUARE_WIDTH + 2, 202 + i * Constant.SQUARE_WIDTH + 2, Constant.SQUARE_WIDTH - 4, Constant.SQUARE_WIDTH - 4);
                g.fillRect(212 + j * Constant.SQUARE_WIDTH + 5, 202 + i * Constant.SQUARE_WIDTH + 5, Constant.SQUARE_WIDTH - 9, Constant.SQUARE_WIDTH - 9);
            }
        }
        Tetromino nextOne = engine.getNextTetromino();
        TetrominoState state = nextOne.nextOneStates[nextOne.getState()];
        java.util.ArrayList<Block> blockList = new ArrayList<>();
        blockList.add(new Block(state.getRow0(), state.getCol0()));
        blockList.add(new Block(state.getRow1(), state.getCol1()));
        blockList.add(new Block(state.getRow2(), state.getCol2()));
        blockList.add(new Block(state.getRow3(), state.getCol3()));

        g.setColor(JBColor.GRAY);
        for (Block block : blockList) {
            int x = block.getCol();
            int y = block.getRow();
            g.drawRect(212 + x * Constant.SQUARE_WIDTH + 2, 202 + y * Constant.SQUARE_WIDTH + 2, Constant.SQUARE_WIDTH - 4, Constant.SQUARE_WIDTH - 4);
            g.fillRect(212 + x * Constant.SQUARE_WIDTH + 5, 202 + y * Constant.SQUARE_WIDTH + 5, Constant.SQUARE_WIDTH - 9, Constant.SQUARE_WIDTH - 9);
        }
    }

    /**
     * 绘制暂存
     * 方块与字体间隔7
     *
     * @param g
     */
    private void paintHold(Graphics2D g) {
        Engine engine = Engine.getInstance();
        g.setColor(JBColor.GRAY);
        g.drawRect(207, 290, 94, 114);

        g.drawString("HOLD", 212, 314);
        g.setColor(UIUtil.getBoundsColor());
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                g.drawRect(212 + j * Constant.SQUARE_WIDTH + 2, 321 + i * Constant.SQUARE_WIDTH + 2, Constant.SQUARE_WIDTH - 4, Constant.SQUARE_WIDTH - 4);
                g.fillRect(212 + j * Constant.SQUARE_WIDTH + 5, 321 + i * Constant.SQUARE_WIDTH + 5, Constant.SQUARE_WIDTH - 9, Constant.SQUARE_WIDTH - 9);
            }
        }
        Tetromino holdOne = engine.getHoldTetromino();
        if (holdOne != null) {
            TetrominoState state = holdOne.getNextOneStates()[holdOne.getState()];
            java.util.ArrayList<Block> blockList = new ArrayList<>();
            blockList.add(new Block(state.getRow0(), state.getCol0()));
            blockList.add(new Block(state.getRow1(), state.getCol1()));
            blockList.add(new Block(state.getRow2(), state.getCol2()));
            blockList.add(new Block(state.getRow3(), state.getCol3()));

            g.setColor(JBColor.GRAY);
            for (Block block : blockList) {
                int x = block.getCol();
                int y = block.getRow();
                g.drawRect(212 + x * Constant.SQUARE_WIDTH + 2, 321 + y * Constant.SQUARE_WIDTH + 2, Constant.SQUARE_WIDTH - 4, Constant.SQUARE_WIDTH - 4);
                g.fillRect(212 + x * Constant.SQUARE_WIDTH + 5, 321 + y * Constant.SQUARE_WIDTH + 5, Constant.SQUARE_WIDTH - 9, Constant.SQUARE_WIDTH - 9);
            }
        }
    }


    /**
     * 绘制下落方块
     *
     * @param g
     */
    private void paintCell(Graphics g) {
        Tetromino currentOne = Engine.getInstance().getCurrentTetromino();
        Block[] blocks = currentOne.blocks;
        for (Block block : blocks) {
            drawSquare(g, block.getRow(), block.getCol(), JBColor.GRAY);
        }
    }

    /**
     * 绘制方块墙壁
     *
     * @param g
     */
    private void paintWall(Graphics g) {
        Block[][] wall = Engine.getInstance().getStack();
        for (int i = 0; i < wall.length; i++) {
            for (int j = 0; j < wall[i].length; j++) {
                Block block = wall[i][j];
                //判断是否有小方块
                if (block != null) {
                    block.setRow(i);
                    block.setCol(j);
                    drawSquare(g, block.getRow(), block.getCol(), JBColor.GRAY);
                }
            }
        }
    }

    /**
     * 绘制方块
     *
     * @param graphics
     * @param row
     * @param col
     * @param color
     */
    private void drawSquare(Graphics graphics, int row, int col, Color color) {
        graphics.setColor(color);
        graphics.drawRect(col * Constant.SQUARE_WIDTH + 2 + 2, row * Constant.SQUARE_WIDTH + 2 + 2, Constant.SQUARE_WIDTH - 4, Constant.SQUARE_WIDTH - 4);
        graphics.fillRect(col * Constant.SQUARE_WIDTH + 5 + 2, row * Constant.SQUARE_WIDTH + 5 + 2, Constant.SQUARE_WIDTH - 9, Constant.SQUARE_WIDTH - 9);
    }
}

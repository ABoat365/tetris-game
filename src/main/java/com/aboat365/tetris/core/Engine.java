package com.aboat365.tetris.core;

import com.aboat365.tetris.core.block.Block;
import com.aboat365.tetris.core.block.Tetromino;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Aboat365
 * 游戏引擎
 */
public class Engine {
    //游戏状态
    private GameState gameState = GameState.NOT_START;

    //游戏运行定时器
    private Timer gameTimer;

    //正在下落的方块
    private Tetromino currentTetromino = Tetromino.getTetromino();

    //下一个方块
    private Tetromino nextTetromino = Tetromino.getTetromino();

    //暂存方块
    private Tetromino holdTetromino;

    //方块堆
    private Block[][] stack = new Block[Constant.ROW][Constant.COL];

    //合计分数
    private int totalScore = 0;

    //合计消除行
    private int totalLine = 0;

    //等级
    private int level = 1;

    public static Engine getInstance() {
        return Engine.create.instance;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public Tetromino getNextTetromino() {
        return nextTetromino;
    }

    public Block[][] getStack() {
        return stack;
    }

    public Tetromino getHoldTetromino() {
        return holdTetromino;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getTotalLine() {
        return totalLine;
    }

    public int getLevel() {
        return level;
    }

    /**
     * 开始游戏
     */
    public void start() {
        if (GameState.PLAYING.equals(gameState)) {
            gameTimer.cancel();
            gameState = GameState.PAUSE;
        } else {
            run(calculationPeriod(level));
            gameState = GameState.PLAYING;
        }
    }

    /**
     * 重新开始游戏
     */
    public void restart() {
        if (!GameState.NOT_START.equals(gameState)) {
            if (GameState.PLAYING.equals(gameState)) {
                gameTimer.cancel();
            }
            stack = new Block[Constant.ROW][Constant.COL];
            Tetromino.random();
            currentTetromino = Tetromino.getTetromino();
            nextTetromino = Tetromino.getTetromino();
            holdTetromino = null;
            totalScore = 0;
            totalLine = 0;
            gameState = GameState.PLAYING;
            level = 1;
            run(calculationPeriod(level));
        }
    }

    /**
     * 消除行动画，动画结束后执行消除行操作
     *
     * @param rows 行号
     */
    private void clearLineDynamicEffect(List<Integer> rows) {
        gameTimer.cancel();
        Timer dynamicEffectTimer = new Timer();
        TimerTask task = new TimerTask() {
            //消除行闪烁次数
            int flickerNumber = 0;

            @Override
            public void run() {
                changeLine(rows, (flickerNumber % 2) == 0);
                flickerNumber++;
                Draw.getInstance().getCanvas().repaint();

                if (flickerNumber >= 7) {
                    dynamicEffectTimer.cancel();
                    clearLine(rows);
                    Engine.this.run(calculationPeriod(level));
                }
            }
        };
        dynamicEffectTimer.schedule(task, 0, 60);
    }

    /**
     * 消除或恢复堆叠要消除的行，配合定时器周期达到消除行闪烁的动画效果
     *
     * @param rows   行号
     * @param change true: 消除，false: 恢复
     */
    private void changeLine(List<Integer> rows, boolean change) {
        rows.forEach(row -> {
            if (change) {
                stack[row] = new Block[Constant.COL];
            } else {
                Block[] blocks = new Block[Constant.COL];
                for (int i = 0; i < Constant.COL; i++) {
                    blocks[i] = new Block(row, i);
                }
                stack[row] = blocks;
            }
        });
    }

    /**
     * 游戏任务运行
     *
     * @param period 游戏速度（毫秒）
     */
    private void run(long period) {
        gameTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (checkDrop()) {
                    currentTetromino.moveDrop();
                } else {
                    upStack();
                }
                Draw.getInstance().getCanvas().repaint();
                if (period > 10) {
                    long nPeriod = calculationPeriod(level);
                    if (period != nPeriod) {
                        gameTimer.cancel();
                        Engine.this.run(nPeriod);
                    }
                }
            }
        };
        gameTimer.schedule(task, 0, period);
    }

    /**
     * 根据等级计算游戏定时器间隔时间
     *
     * @param level 等级
     * @return 游戏速度（毫秒）
     */
    private long calculationPeriod(int level) {
        return 1000 - (level - 1) * 50L;
    }

    /**
     * 将方块置入场地且增加堆叠行的高度,
     * 并检查是否有行可以消除并判断游戏是否结束。
     */
    private void upStack(){
        Block[] blocks = currentTetromino.blocks;
        for (Block block : blocks) {
            int row = block.getRow();
            int col = block.getCol();
            if (row < 0) {
                continue;
            }
            stack[row][col] = block;
        }

        List<Integer> clearRows = checkLineClear();
        if (!clearRows.isEmpty()) {
            clearLineDynamicEffect(clearRows);
        }
        if (checkGameOver()) {
            gameState = GameState.GAME_OVER;
        } else {
            currentTetromino = nextTetromino;
            nextTetromino = Tetromino.getTetromino();
        }
    }

    /**
     * 判断方块能否继续下落
     *
     * @return true: 可以下落，false: 不能下落
     */
    public boolean checkDrop() {
        Block[] blocks = currentTetromino.blocks;
        for (Block block : blocks) {
            int row = block.getRow();
            int col = block.getCol();
            if (row < -1) {
                continue;
            }
            //判断是否到达底部
            if (row == stack.length - 1) {
                return false;
            } else if (stack[row + 1][col] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查是否可以消除行
     *
     * @return 满行行号
     */
    public List<Integer> checkLineClear() {
        Block[] blocks = currentTetromino.blocks;
        return Arrays.stream(blocks).map(Block::getRow).distinct().sorted().filter(this::checkFullLine).toList();
    }

    /**
     * 消除行，并更新分数、消除行、等级数据
     *
     * @param rows 行号
     */
    public void clearLine(List<Integer> rows) {
        int line = 0;
        for (Integer row : rows) {
            line++;
            for (int i = row; i > 0; i--) {
                System.arraycopy(stack[i - 1], 0, stack[i], 0, Constant.COL);
            }
            stack[0] = new Block[Constant.COL];
        }
        if (line > 0) {
            //分数池获取分数，累加到总分
            totalScore += Constant.SCORES_POOL[line];
            //总行数
            totalLine += line;
            //等级
            level = totalLine / 10 + 1;
        }
    }

    /**
     * 判断行是否满
     *
     * @param row 行号
     * @return true: 满，false: 不满
     */
    public boolean checkFullLine(int row) {
        if (row < 0) {
            return false;
        }
        Block[] blocks = stack[row];
        for (Block block : blocks) {
            if (block == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断游戏是否结束
     *
     * @return true: 结束，false: 未结束
     */
    public boolean checkGameOver() {
        Block[] blocks = nextTetromino.blocks;
        for (Block block : blocks) {
            int row = block.getRow();
            int col = block.getCol();
            if (row < 0) {
                continue;
            }
            if (stack[row][col] != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断方块是否重合
     *
     * @return true: 重合，false: 不重合
     */
    public boolean checkTetrominoCoincide() {
        Block[] blocks = currentTetromino.blocks;
        for (Block block : blocks) {
            int row = block.getRow();
            int col = block.getCol();
            if (row < 0) {
                continue;
            }
            if (stack[row][col] != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断方块是否越界
     *
     * @return true: 越界，false: 未越界
     */
    public boolean checkBoundary() {
        Block[] blocks = currentTetromino.blocks;
        for (Block block : blocks) {
            int col = block.getCol();
            int row = block.getRow();
            if (row > stack.length - 1 || col < 0 || col > stack[0].length - 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 软降
     * 加速降落
     */
    public void sortDropActive() {
        if (!GameState.PLAYING.equals(gameState)) {
            return;
        }
        if (checkDrop()) {
            currentTetromino.moveDrop();
        } else {
            upStack();
        }
        Draw.getInstance().getCanvas().repaint();
    }

    /**
     * 硬降
     * 立即着陆
     */
    public void hardDropActive() {
        if (!GameState.PLAYING.equals(gameState)) {
            return;
        }
        while (true) {
            if (checkDrop()) {
                currentTetromino.moveDrop();
            } else {
                break;
            }
        }
        upStack();
        Draw.getInstance().getCanvas().repaint();
    }

    /**
     * 控制左移
     */
    public void moveLeftActive() {
        if (!GameState.PLAYING.equals(gameState)) {
            return;
        }
        currentTetromino.moveLeft();
        //判断是否越界或重合
        if (checkBoundary() || checkTetrominoCoincide()) {
            currentTetromino.moveRight();
        }
        Draw.getInstance().getCanvas().repaint();
    }

    /**
     * 控制右移
     */
    public void moveRightActive() {
        if (!GameState.PLAYING.equals(gameState)) {
            return;
        }
        currentTetromino.moveRight();
        if (checkBoundary() || checkTetrominoCoincide()) {
            currentTetromino.moveLeft();
        }
        Draw.getInstance().getCanvas().repaint();
    }

    /**
     * 控制顺时针旋转
     */
    public void rotateRightActive() {
        if (!GameState.PLAYING.equals(gameState)) {
            return;
        }
        currentTetromino.rotateRight();
        if (checkBoundary() || checkTetrominoCoincide()) {
            currentTetromino.rotateLeft();
        }
        Draw.getInstance().getCanvas().repaint();
    }

    /**
     * 控制逆时针旋转
     */
    public void rotateLeftActive() {
        if (!GameState.PLAYING.equals(gameState)) {
            return;
        }
        currentTetromino.rotateLeft();
        if (checkBoundary() || checkTetrominoCoincide()) {
            currentTetromino.rotateRight();
        }
        Draw.getInstance().getCanvas().repaint();
    }

    /**
     * 暂存
     */
    public void holdActive() {
        if (!currentTetromino.getHold()) {
            if (holdTetromino == null) {
                holdTetromino = currentTetromino;
                currentTetromino = nextTetromino;
                nextTetromino = Tetromino.getTetromino();
            } else {
                Tetromino j = currentTetromino;
                currentTetromino = holdTetromino;
                holdTetromino = j;
                currentTetromino.init();
                currentTetromino.putState(currentTetromino.getState());
            }
            holdTetromino.setHold(true);
        }
    }

    /**
     * 创建引擎实例
     */
    private static class create {
        static Engine instance = new Engine();
    }
}

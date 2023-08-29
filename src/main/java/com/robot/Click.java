package com.robot;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.robot.util.CommonUtil.CommaClick;
import static com.robot.util.CommonUtil.CommaRightClick;

/**
 * main
 *
 * @author wql
 * @desc click
 * @date 2023/3/13
 * @lastUpdateUser wql
 * @lastUpdateDesc
 * @lastUpdateTime 2023/3/13
 */
public class Click {

    static int SCREEN_W = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    static int SCREEN_H = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    static Screen screen = new Screen();
    static LocalDateTime startTime = LocalDateTime.now();
    static Boolean flag = false;
    static String globalPath = System.getProperty("user.dir");
    static boolean jumpFlag = false;
    static boolean toolFlag = false;

    public static void main(String[] args) throws AWTException {
        Click click = new Click();
        // test
        SwingUtilities.invokeLater(click::RobotBody);
        Robot robot = new Robot();
        click.controlFactory(robot);
    }

    //<editor-fold desc="界面设置">
    private void RobotBody() {
        JFrame frame = new JFrame("Robot");
        frame.setSize(400, 100);
        frame.setLocation(SCREEN_W / 2 - 200, SCREEN_H / 2 - 50);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Container container = frame.getContentPane();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(null, "点退出点退出!", "不听话", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel();
        placeComponents(panel);
        container.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void placeComponents(JPanel panel) {
        JButton start = new JButton("开始");
        start.addActionListener(e -> {
            flag = true;
            start.setEnabled(false);
        });
        panel.add(start);
        JButton exit = new JButton("退出");
        exit.addActionListener(e -> System.exit(0));
        panel.add(exit);
    }
    //</editor-fold>

    /**
     * 识别操作步骤
     *
     * @param robot robot
     */
    public void controlFactory(Robot robot) {
        jumpFlag = false;
        toolFlag = false;
        int globalTimeout = 1;
        if (Objects.equals(flag, true)) {
            Duration dur = Duration.between(startTime, LocalDateTime.now());
            if (dur.toMinutes() > 420) {
                System.exit(0);
            }
            try {
                // 开始游戏
                Match start = screen.exists(globalPath + "\\img\\start.png", globalTimeout);
                if (Objects.nonNull(start)) {
                    screen.hover(globalPath + "\\img\\start.png");
                    screen.click();
                }
                // 开始匹配
                Match startMatch = screen.exists(globalPath + "\\img\\startMatch.png", globalTimeout);
                if (Objects.nonNull(startMatch)) {
                    Thread.sleep(1000 * 65);
                }
                // 马上开始
                Match nowStart = screen.exists(globalPath + "\\img\\nowStart.png", globalTimeout);
                if (Objects.nonNull(nowStart)) {
                    Thread.sleep(1000 * 60);
                }
                // 跳伞
                Match jump = screen.exists(globalPath + "\\img\\jump.png", globalTimeout);
                if (Objects.nonNull(jump)) {
                    jumpFlag = true;
                    screen.type("f");
                    // 加速下落
                    screen.keyDown(Key.SHIFT);
                    screen.keyDown("w");
                    Thread.sleep(1000 * 48);
                    screen.keyUp(Key.SHIFT);
                    screen.keyUp("w");
                }
                // 判断是否打开工具箱
                if (Objects.equals(jumpFlag, true) && Objects.equals(toolFlag, false)) {
                    screen.type(Keys.TAB);
                    Thread.sleep(100);
                    // 打开工具箱
                    CommaRightClick(robot, 475, 149);
                    // 移动到飞机上 点左键
                    CommaClick(robot, 957, 358);
                    // 左键点OK
                    CommaClick(robot, 949, 866);
                    Thread.sleep(1000 * 5);
                    screen.type("z");
                    toolFlag = true;
                }
                // 重置按钮
                if (Objects.equals(jumpFlag, true) && Objects.equals(toolFlag, true)) {
                    screen.type(Keys.TAB);
                    screen.type("x");
                }
                // 观战
                Match look = screen.exists(globalPath + "\\img\\look2.png", globalTimeout);
                if (Objects.nonNull(look)) {
                    screen.hover(globalPath + "\\img\\look.png");
                    screen.click();
                    Thread.sleep(1000 * 60);
                }
                // 队伍排名
                Match teamRank = screen.exists(globalPath + "\\img\\teamRank.png", globalTimeout);
                Match toHome = screen.exists(globalPath + "\\img\\toHome.png", globalTimeout);
                if (Objects.nonNull(teamRank) || Objects.nonNull(toHome)) {
                    screen.hover(globalPath + "\\img\\toHome.png");
                    screen.click();
                    Thread.sleep(1000 * 5);
                }
                // 确认离开
                Match returnGame = screen.exists(globalPath + "\\img\\returnGame.png", globalTimeout);
                if (Objects.nonNull(returnGame)) {
                    screen.hover(globalPath + "\\img\\config.png");
                    screen.click();
                    Thread.sleep(1000 * 3);
                }
                // 重新开始
                Match reStart = screen.exists(globalPath + "\\img\\reStart.png", globalTimeout);
                if (Objects.nonNull(reStart)) {
                    screen.type(Keys.ESC);
                    screen.type(Keys.ESC);
                    Thread.sleep(1000 * 2);
                }
                Match reStart2 = screen.exists(globalPath + "\\img\\reStart2.png", globalTimeout);
                if (Objects.nonNull(reStart2)) {
                    screen.type(Keys.ESC);
                    screen.type(Keys.ESC);
                    Thread.sleep(1000 * 2);
                }
                Match reStart3 = screen.exists(globalPath + "\\img\\reStart3.png", globalTimeout);
                if (Objects.nonNull(reStart3)) {
                    screen.type(Keys.ESC);
                    screen.type(Keys.ESC);
                    Thread.sleep(1000 * 2);
                }
                // 吃鸡
                Match first = screen.exists(globalPath + "\\img\\first.png", globalTimeout);
                Match first2 = screen.exists(globalPath + "\\img\\first2.png", globalTimeout);
                if (Objects.nonNull(first) || Objects.nonNull(first2)) {
                    screen.type(Keys.ESC);
                    screen.type(Keys.ESC);
                    Thread.sleep(1000 * 2);
                }
                // 重新连接
                Match reload = screen.exists(globalPath + "\\img\\reload.png", 2);
                if (Objects.nonNull(reload)) {
                    screen.hover(globalPath + "\\img\\cancel.png");
                    screen.click();
                    Thread.sleep(1000 * 2);
                }
                // 连接超时/与主机失去连接
                Match error = screen.exists(globalPath + "\\img\\error.png", 2);
                if (Objects.nonNull(error)) {
                    screen.hover(globalPath + "\\img\\ok.png");
                    screen.click();
                    Thread.sleep(1000 * 2);
                }
                // 服务器繁忙
                Match busy = screen.exists(globalPath + "\\img\\busy.png", 2);
                if (Objects.nonNull(busy)) {
                    screen.hover(globalPath + "\\img\\reloadBtn.png");
                    screen.click();
                    Thread.sleep(1000 * 2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 防止没有观战
//            screen.type(Keys.PAGE_DOWN);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controlFactory(robot);
    }

    public Click() {
        try {
            PrintStream print = new PrintStream("log.txt");
            System.setOut(print);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

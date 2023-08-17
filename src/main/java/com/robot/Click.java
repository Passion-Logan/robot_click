package com.robot;

import org.sikuli.hotkey.Keys;
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

    public static void main(String[] args) {
        Click click = new Click();
        SwingUtilities.invokeLater(click::RobotBody);
        click.controlFactory();
    }

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

    /**
     * 识别操作步骤
     */
    public void controlFactory() {
        int globalTimeout = 2;
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
                    Thread.sleep(1000 * 30);
                }
                // 跳伞
                Match jump = screen.exists(globalPath + "\\img\\jump.png", globalTimeout);
                if (Objects.nonNull(jump)) {
                    screen.type("f");
                    Thread.sleep(1000 * 30);
                }
                // 打开工具箱
                screen.type(Keys.TAB);
                boolean tabFlag = false;
                Match tool = screen.exists(globalPath + "\\img\\tool.png", globalTimeout);
                if (Objects.nonNull(tool)) {
                    screen.hover(globalPath + "\\img\\tool.png");
                    screen.rightClick();
                    Thread.sleep(1000);
                }
                // 使用工具箱
                Match toolInfo = screen.exists(globalPath + "\\img\\tool_info.png", globalTimeout);
                if (Objects.nonNull(toolInfo)) {
                    screen.hover(globalPath + "\\img\\tool_info.png");
                    screen.click();
                    Match toolOk = screen.exists(globalPath + "\\img\\tool_ok.png", globalTimeout);
                    if (Objects.nonNull(toolOk)) {
                        screen.hover(globalPath + "\\img\\tool_ok.png");
                        screen.click();
                        Thread.sleep(1000 * 5);
                        // 去除tab、趴下
                        screen.type("z");
                    }
                    screen.type(Keys.ESC);
                    tabFlag = true;
                }
                if (Objects.equals(tabFlag, false)) {
                    screen.type(Keys.ESC);
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
                if (Objects.nonNull(teamRank)) {
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
                // 吃鸡
                Match first = screen.exists(globalPath + "\\img\\first2.png", globalTimeout);
                if (Objects.nonNull(first)) {
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
            screen.type(Keys.PAGE_DOWN);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controlFactory();
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

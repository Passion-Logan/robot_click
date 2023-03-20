package com.robot;

import cn.hutool.core.io.resource.ClassPathResource;
import org.sikuli.hotkey.Keys;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import javax.swing.*;
import java.awt.*;
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

    public static void main(String[] args) {
        Click click = new Click();
        SwingUtilities.invokeLater(click::RobotBody);
        click.controlFactory();
    }

    private void RobotBody() {
        JFrame frame = new JFrame("Robot");
        frame.setSize(200, 100);
        frame.setLocation(SCREEN_W / 2 - 100, SCREEN_H / 2 - 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = frame.getContentPane();

        JPanel panel = new JPanel();
        placeComponents(panel);
        container.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    public void placeComponents(JPanel panel) {
        JButton start = new JButton("退出");
        start.addActionListener(e -> System.exit(0));
        panel.add(start);
    }

    /**
     * 识别操作步骤
     */
    public void controlFactory() {
        Duration dur = Duration.between(startTime, LocalDateTime.now());
        if (dur.toMinutes() > 420) {
            System.exit(0);
        }

        try {
            // 开始游戏
            Match start = screen.exists(new ClassPathResource("img/start.png").getAbsolutePath(), 5);
            if (!Objects.isNull(start)) {
                screen.hover(new ClassPathResource("img/start.png").getAbsolutePath());
                screen.click();
            }
            // 开始匹配
            Match startMatch = screen.exists(new ClassPathResource("img/startMatch.png").getAbsolutePath(), 5);
            if (!Objects.isNull(startMatch)) {
                Thread.sleep(1000 * 65);
            }
            // 马上开始
            Match nowStart = screen.exists(new ClassPathResource("img/nowStart.png").getAbsolutePath(), 5);
            if (!Objects.isNull(nowStart)) {
                Thread.sleep(1000 * 30);
            }
            // 跳伞
            Match jump = screen.exists(new ClassPathResource("img/jump.png").getAbsolutePath(), 5);
            if (!Objects.isNull(jump)) {
                screen.type("f");
                Thread.sleep(1000 * 60);
            }
            // 观战
            Match look = screen.exists(new ClassPathResource("img/look2.png").getAbsolutePath(), 5);
            if (!Objects.isNull(look)) {
                screen.hover(new ClassPathResource("img/look.png").getAbsolutePath());
                screen.click();
                Thread.sleep(1000 * 60);
            }
            // 队伍排名
            Match teamRank = screen.exists(new ClassPathResource("img/teamRank.png").getAbsolutePath(), 5);
            if (!Objects.isNull(teamRank)) {
                screen.hover(new ClassPathResource("img/toHome.png").getAbsolutePath());
                screen.click();
                Thread.sleep(1000 * 5);
            }
            // 确认离开
            Match returnGame = screen.exists(new ClassPathResource("img/returnGame.png").getAbsolutePath(), 5);
            if (!Objects.isNull(returnGame)) {
                screen.hover(new ClassPathResource("img/config.png").getAbsolutePath());
                screen.click();
                Thread.sleep(1000 * 3);
            }
            // 重新开始
            Match reStart = screen.exists(new ClassPathResource("img/reStart.png").getAbsolutePath(), 5);
            if (!Objects.isNull(reStart)) {
                screen.type(Keys.ESC);
                screen.type(Keys.ESC);
                Thread.sleep(1000 * 2);
            }
            // 重新连接
            Match reload = screen.exists(new ClassPathResource("img/reload.png").getAbsolutePath(), 2);
            if (!Objects.isNull(reload)) {
                screen.hover(new ClassPathResource("img/cancel.png").getAbsolutePath());
                screen.click();
                Thread.sleep(1000 * 2);
            }
            // todo 连接超时
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 防止没有观战
        screen.type(Keys.PAGE_DOWN);
        controlFactory();
    }

}

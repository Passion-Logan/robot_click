package com.robot;

import cn.hutool.core.io.resource.ClassPathResource;
import org.sikuli.script.Screen;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;

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
            screen.wait(new ClassPathResource("img/start.png").getAbsolutePath(), 100);
            screen.hover(new ClassPathResource("img/start.png").getAbsolutePath());
            Thread.sleep(2000);
            controlFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Result result = JSONUtil.toBean(text, Result.class);
//        System.out.println(result);
//        if (text.contains("开始游戏")) {
//            // 开始游戏
//            StartGame(robot, result);
////            RobotTimer(robot, 1000 * 60);
//        } else if (text.contains("开始匹配")) {
//            RobotTimer(robot, 1000 * 90);
//        } else if (text.contains("距离比赛开始还有")) {
//            RobotTimer(robot, 1000 * 90);
//        } else if (text.contains("跳伞") && text.contains("生存")) {
//            robot.keyPress(VK_F);
//            robot.keyRelease(VK_F);
//            robot.delay(800);
//            RobotTimer(robot, 1000 * 90);
//        } else if (text.contains("生存")) {
//            RobotTimer(robot, 1000 * 120);
//        } else if (text.contains("您的队伍仍存活")) {
//            // 观战
//            LookGame(robot, result);
//            RobotTimer(robot, 1000 * 90);
//        } else if (text.contains("祝下次好运")) {
//            // 结算重开
//            robot.keyPress(VK_ESCAPE);
//            robot.keyRelease(VK_ESCAPE);
//            robot.delay(1000);
//            robot.keyPress(VK_ESCAPE);
//            robot.keyRelease(VK_ESCAPE);
//            robot.delay(1000);
//            RobotTimer(robot, 1000 * 3);
//        } else if (text.contains("服务器目前非常繁忙")) {
//            // 重连重开
//            ReloadGame(robot, result);
//            RobotTimer(robot, 1000 * 90);
//        } else if (text.contains("比赛仍在继续中") && text.contains("重新连接")) {
//            ReStartGame(robot, result);
//            RobotTimer(robot, 1000 * 5);
//        } else {
//            // 防止没有自动观战
//            robot.keyPress(VK_PAGE_DOWN);
//            robot.keyRelease(VK_PAGE_DOWN);
//            robot.delay(800);
//        }
//        if (text.contains("连接超时") && text.contains("错误")) {
//            // 连接超时
//            TimeOutGame(robot, result);
//            RobotTimer(robot, 1000 * 10);
//        }
    }

}

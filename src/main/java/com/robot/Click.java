package com.robot;

import cn.hutool.json.JSONUtil;
import com.robot.entity.Result;
import com.robot.util.OcrUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.robot.process.GameControl.*;

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Click::RobotBody);
    }

    public static void RobotBody() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Robot");
        frame.setSize(200, 100);
        frame.setLocation(SCREEN_W / 2 - 100, SCREEN_H / 2 - 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = frame.getContentPane();

        JPanel panel = new JPanel();
        placeComponents(panel);
        container.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        JButton start = new JButton("开始");
        start.addActionListener(e -> RobotStartControl());
        panel.add(start);
    }

    /**
     * 开始控制
     */
    private static void RobotStartControl() {
        try {
            Robot robot = new Robot();
            Thread.sleep(5000);
            RobotMain(robot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 入口
     *
     * @param robot robot
     */
    public static void RobotMain(Robot robot) {
        controlFactory(robot, OcrUtil.ImageOcr());
    }

    /**
     * 识别操作步骤
     *
     * @param robot robot
     * @param text  内容
     */
    private static void controlFactory(Robot robot, String text) {
        Result result = JSONUtil.toBean(text, Result.class);
        System.out.println(result);
        if (text.contains("开始游戏")) {
            // 开始游戏
            StartGame(robot, result);
            RobotTimer(robot, 1000 * 60);
        }
        if (text.contains("您的队伍仍存活")) {
            // 观战
            LookGame(robot, result);
            RobotTimer(robot, 1000 * 90);
        }
        if (text.contains("祝下次好运")) {
            // 结算重开
            StartGame(robot, result);
            RobotTimer(robot, 1000 * 90);
        }
        if (text.contains("服务器目前非常繁忙")) {
            // 重连重开
            ReloadGame(robot, result);
            RobotTimer(robot, 1000 * 90);
        }
        if (text.contains("连接超时")) {
            // 连接超时
            TimeOutGame(robot, result);
            RobotTimer(robot, 1000 * 90);
        }
    }

    private static void RobotTimer(Robot robot, long sleepInt) {
        try {
            Thread.sleep(sleepInt);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        RobotMain(robot);
    }

}

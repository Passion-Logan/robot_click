package com.robot;

import cn.hutool.json.JSONUtil;
import com.robot.entity.Result;
import com.robot.util.OcrUtil;

import javax.swing.*;
import java.awt.*;
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
    static HashMap<Integer, Map<String, Integer>> OCR_MAP = new HashMap<>();

    static {
        Map<String, Integer> temp = new HashMap<>();
        temp.put("X", 1070);
        temp.put("Y", 40);
        OCR_MAP.put(1920, temp);

        Map<String, Integer> temp2 = new HashMap<>();
        temp2.put("X", 1517);
        temp2.put("Y", 57);
        OCR_MAP.put(2560, temp2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Click::RobotBody);
    }

    public static void RobotBody() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Robot");
        frame.setSize(400, 200);
        frame.setLocation(SCREEN_W / 2 - 200, SCREEN_H / 2 - 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = frame.getContentPane();

        JPanel panel = new JPanel();
        placeComponents(panel);
        container.add(panel, BorderLayout.CENTER);

        frame.pack();
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

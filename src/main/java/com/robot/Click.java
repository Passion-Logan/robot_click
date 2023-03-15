package com.robot;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.robot.process.GameControl.*;
import static com.robot.util.CommonUtil.*;
import static java.awt.event.KeyEvent.*;

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
        panel.setLayout(new GridLayout(3, 3, 10, 10));

        panel.add(new JLabel("Other坐标:"));
//        JTextField gameX = new JTextField("466", 5);
        JTextField gameX = new JTextField("1636", 5);
        panel.add(gameX);
//        JTextField gameY = new JTextField("1058", 5);
        JTextField gameY = new JTextField("1416", 5);
        panel.add(gameY);
        panel.add(new JLabel("OCR坐标:"));
//        JTextField ocrX = new JTextField("417", 5);
        JTextField ocrX = new JTextField("1590", 5);
        panel.add(ocrX);
//        JTextField ocrY = new JTextField("1058", 5);
        JTextField ocrY = new JTextField("1414", 5);
        panel.add(ocrY);
        JButton start = new JButton("开始");
        start.addActionListener(e -> RobotStartControl(Integer.parseInt(gameX.getText()), Integer.parseInt(gameY.getText()),
                Integer.parseInt(ocrX.getText()), Integer.parseInt(ocrY.getText())));
        panel.add(start);
    }

    /**
     * 开始控制
     *
     * @param gameX game任务栏X
     * @param gameY game任务栏X
     * @param ocrX  ocr任务栏X
     * @param ocrY  ocr任务栏X
     */
    private static void RobotStartControl(int gameX, int gameY, int ocrX, int ocrY) {
        try {
            Robot robot = new Robot();
            // 打开程序
            CommaClick(robot, gameX, gameY);
            // 5秒识别一次
            RobotMain(robot, gameX, gameY, ocrX, ocrY, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 入口
     *
     * @param robot robot
     * @param gameX gameX
     * @param gameY gameY
     * @param ocrX  ocrX
     * @param ocrY  ocrY
     * @param b     是否使用tab切换
     */
    public static void RobotMain(Robot robot, int gameX, int gameY, int ocrX, int ocrY, boolean b) {
        if (Objects.equals(b, false)) {
            // 打开ocr
            CommaClick(robot, ocrX, ocrY);
        } else {
            TabClick(robot);
        }
        // 开始识别
        CommaClick(robot, OCR_MAP.get(SCREEN_W).get("X"), OCR_MAP.get(SCREEN_W).get("Y"));
        // 判断运行状态
        CommaClick(robot, OCR_MAP.get(SCREEN_W).get("X"), OCR_MAP.get(SCREEN_W).get("Y") + 100);
        robot.keyPress(VK_CONTROL);
        robot.keyPress(VK_A);
        robot.keyRelease(VK_A);
        robot.keyPress(VK_C);
        robot.keyRelease(VK_C);
        robot.keyRelease(VK_CONTROL);
        robot.delay(1000);
        controlFactory(robot, gameX, gameY, ocrX, ocrY, getSysClipboardText());
    }

    /**
     * 识别操作步骤
     *
     * @param robot robot
     * @param gameX gameX
     * @param gameY gameY
     * @param ocrX  ocrX
     * @param ocrY  ocrY
     * @param text  内容
     */
    private static void controlFactory(Robot robot, int gameX, int gameY, int ocrX, int ocrY, String text) {
        TabClick(robot);
        if (text.contains("开始游戏")) {
            // 开始游戏
            StartGame(robot, gameX, gameY);
            RobotTimer(robot, gameX, gameY, ocrX, ocrY);
        }
        if (text.contains("您的队伍仍存活")) {
            // 观战
            LookGame(robot, gameX, gameY);
            RobotTimer(robot, gameX, gameY, ocrX, ocrY);
        }
        if (text.contains("祝下次好运")) {
            // 结算重开
            StartGame(robot, gameX, gameY);
            RobotTimer(robot, gameX, gameY, ocrX, ocrY);
        }
        if (text.contains("服务器目前非常繁忙")) {
            // 重连重开
            ReloadGame(robot, gameX, gameY);
            RobotTimer(robot, gameX, gameY, ocrX, ocrY);
        }
        if (text.contains("连接超时")) {
            // 连接超时
            TimeOutGame(robot, gameX, gameY);
            RobotTimer(robot, gameX, gameY, ocrX, ocrY);
        }
    }

    private static void RobotTimer(Robot robot, int gameX, int gameY, int ocrX, int ocrY) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        RobotMain(robot, gameX, gameY, ocrX, ocrY, true);
    }

    private static void TabClick(Robot robot) {
        robot.keyPress(VK_ALT);
        robot.keyPress(VK_TAB);
        robot.keyRelease(VK_TAB);
        robot.keyRelease(VK_ALT);
        robot.delay(800);
    }

}

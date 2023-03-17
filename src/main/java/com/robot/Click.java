package com.robot;

import cn.hutool.json.JSONUtil;
import com.robot.entity.Result;
import com.robot.util.CommonUtil;
import com.robot.util.OcrUtil;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import javax.swing.*;
import java.awt.*;

import static com.robot.process.GameControl.*;
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
    static String IMG_PATH;

    public static void main(String[] args) {
//        SwingUtilities.invokeLater(Click::RobotBody);

        try {
            baiduSearch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void baiduSearch() throws InterruptedException, FindFailed {
        Screen screen = new Screen();
        screen.wait("C:\\Users\\Administrator\\Desktop\\test\\11.png", 100);
//        screen.hover("C:\\Users\\Administrator\\Desktop\\test\\test.jpg");
        //获取某个图片在屏幕上的位置
        Match match = screen.find("C:\\Users\\Administrator\\Desktop\\test\\11.png");
        System.out.println(match.getX() + " " +  match.getY() + "    " + match.getH() + " " + match.getW());
//        screen.mouseMove(match.getX(), match.getY());
        screen.hover("C:\\Users\\Administrator\\Desktop\\test\\11.png");

//        screen.mouseDown(Button.RIGHT);
//        screen.mouseUp();
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

        frame.pack();
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(new GridLayout(1, 1, 10, 10));
        JTextField imgPath = new JTextField("C:\\Users\\Cody\\Desktop\\robot",5);
        panel.add(imgPath);
        JButton start = new JButton("开始");
        IMG_PATH = imgPath.getText();
        start.addActionListener(e -> RobotStartControl());
        panel.add(start);
    }

    /**
     * 开始控制
     */
    private static void RobotStartControl() {
        try {
            Robot robot = new Robot();
            Thread.sleep(3000);
//            CommonUtil.TabClick(robot);
            StartGame(robot, null);
//            RobotMain(robot);
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
        controlFactory(robot, OcrUtil.ImageOcr(IMG_PATH));
//        OcrUtil.ImageOcr(IMG_PATH);
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
//            RobotTimer(robot, 1000 * 60);
        } else if (text.contains("开始匹配")) {
            RobotTimer(robot, 1000 * 90);
        } else if (text.contains("距离比赛开始还有")) {
            RobotTimer(robot, 1000 * 90);
        } else if (text.contains("跳伞") && text.contains("生存")) {
            robot.keyPress(VK_F);
            robot.keyRelease(VK_F);
            robot.delay(800);
            RobotTimer(robot, 1000 * 90);
        } else if (text.contains("生存")) {
            RobotTimer(robot, 1000 * 120);
        } else if (text.contains("您的队伍仍存活")) {
            // 观战
            LookGame(robot, result);
            RobotTimer(robot, 1000 * 90);
        } else if (text.contains("祝下次好运")) {
            // 结算重开
            robot.keyPress(VK_ESCAPE);
            robot.keyRelease(VK_ESCAPE);
            robot.delay(1000);
            robot.keyPress(VK_ESCAPE);
            robot.keyRelease(VK_ESCAPE);
            robot.delay(1000);
            RobotTimer(robot, 1000 * 3);
        } else if (text.contains("服务器目前非常繁忙")) {
            // 重连重开
            ReloadGame(robot, result);
            RobotTimer(robot, 1000 * 90);
        } else if (text.contains("比赛仍在继续中") && text.contains("重新连接")) {
            ReStartGame(robot, result);
            RobotTimer(robot, 1000 * 5);
        } else {
            // 防止没有自动观战
            robot.keyPress(VK_PAGE_DOWN);
            robot.keyRelease(VK_PAGE_DOWN);
            robot.delay(800);
        }
        if (text.contains("连接超时") && text.contains("错误")) {
            // 连接超时
            TimeOutGame(robot, result);
            RobotTimer(robot, 1000 * 10);
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

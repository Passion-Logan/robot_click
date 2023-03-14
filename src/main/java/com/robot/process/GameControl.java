package com.robot.process;

import com.robot.util.CommonUtil;
import com.sun.jna.IntegerType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static java.awt.event.KeyEvent.*;

/**
 * @author wql
 * @desc GameControl
 * @date 2023/3/14
 * @lastUpdateUser wql
 * @lastUpdateDesc
 * @lastUpdateTime 2023/3/14
 */
public class GameControl {

    static int SCREEN_W = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    static Map<Integer, Map<String, Map<String, Integer>>> BUTTON_MAP;

    static {
        Map<String, Map<String, Integer>> buttons1 = new HashMap<>();
        // 准备按钮
        HashMap<String, Integer> ready = new HashMap<>();
        ready.put("X", 123);
        ready.put("Y", 123);
        buttons1.put("ready", ready);
        // 观战按钮
        HashMap<String, Integer> look = new HashMap<>();
        look.put("X", 123);
        look.put("Y", 123);
        buttons1.put("look", look);

        // todo 添加 2k分辨率的按钮坐标
        BUTTON_MAP.put(1920, buttons1);
//        BUTTON_MAP.put(2560, buttons2);
    }

    /**
     * 开始游戏控制
     *
     * @param robot robot
     * @param gameX gameX
     * @param gameY gameY
     */
    public static void StartGame(Robot robot, int gameX, int gameY) {
        Map<String, Integer> ready = BUTTON_MAP.get(SCREEN_W).get("ready");
        // 点击准备
        CommonUtil.CommaClick(robot, ready.get("X"), ready.get("Y"));
        // 2.5秒按一次F
        robot.keyPress(VK_F);
        robot.keyRelease(VK_F);
        robot.delay(2500);
    }

    /**
     * 观战
     *
     * @param robot robot
     * @param gameX gameX
     * @param gameY gameY
     */
    public static void LookGame(Robot robot, int gameX, int gameY) {
        Map<String, Integer> look = BUTTON_MAP.get(SCREEN_W).get("look");
        // 点击观战
        CommonUtil.CommaClick(robot, look.get("X"), look.get("Y"));
        // 2.5秒按一次pagedown
        robot.keyPress(VK_PAGE_DOWN);
        robot.keyRelease(VK_PAGE_DOWN);
        robot.delay(2500);
    }

    /**
     * 连接超时
     * @param robot robot
     * @param gameX gameX
     * @param gameY gameY
     */
    public static void TimeOutGame(Robot robot, int gameX, int gameY) {
        // 点击确定
    }
}

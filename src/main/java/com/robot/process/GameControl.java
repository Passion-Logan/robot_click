package com.robot.process;

import com.robot.entity.Result;
import com.robot.util.CommonUtil;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static java.awt.event.KeyEvent.VK_F;
import static java.awt.event.KeyEvent.VK_PAGE_DOWN;

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
    static Map<Integer, Map<String, Map<String, Integer>>> BUTTON_MAP = new HashMap<>();

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
        //中间的按钮-重新连接、
        HashMap<String, Integer> reload = new HashMap<>();
        look.put("X", 123);
        look.put("Y", 123);
        buttons1.put("look", look);


        BUTTON_MAP.put(1920, buttons1);
        // 2k分辨率的按钮坐标
        Map<String, Map<String, Integer>> buttons2 = new HashMap<>();
        // 准备按钮
        HashMap<String, Integer> ready2 = new HashMap<>();
        ready2.put("X", 425);
        ready2.put("Y", 939);
        buttons2.put("ready", ready2);

        BUTTON_MAP.put(2560, buttons2);
    }

    /**
     * 开始游戏控制
     *
     * @param robot robot
     */
    public static void StartGame(Robot robot, Result result) {
        Map<String, Integer> ready = BUTTON_MAP.get(SCREEN_W).get("ready");
        // 点击准备
        CommonUtil.CommaClick(robot, ready.get("X"), ready.get("Y"));
        // 2.5秒按一次F
        robot.keyPress(VK_F);
        robot.keyRelease(VK_F);
        robot.delay(2500);
    }

    /**
     * 游戏重开控制
     *
     * @param robot robot
     * @param gameX gameX
     * @param gameY gameY
     */
    public static void ReStartGame(Robot robot, int gameX, int gameY) {
        // 点击ESC 点击确定
        // 调用开始游戏方法
    }

    /**
     * 观战
     *
     * @param robot  robot
     * @param result result
     */
    public static void LookGame(Robot robot, Result result) {
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
     *
     * @param robot  robot
     * @param result result
     */
    public static void TimeOutGame(Robot robot, Result result) {
        // 点击确定
    }

    /**
     * 重新连接
     *
     * @param robot  robot
     * @param result result
     */
    public static void ReloadGame(Robot robot, Result result) {
        // 点击确定
    }

}

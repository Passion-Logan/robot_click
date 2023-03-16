package com.robot.process;

import cn.hutool.core.util.StrUtil;
import com.robot.entity.LineData;
import com.robot.entity.Result;
import com.robot.util.CommonUtil;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    /**
     * 开始游戏控制
     *
     * @param robot robot
     */
    public static void StartGame(Robot robot, Result result) {
        Map<String, Integer> bounding = findBounding(result, "开始");
        // 点击准备
        CommonUtil.CommaClick(robot, bounding.get("x"), bounding.get("y"));
    }

    /**
     * 游戏重开控制
     *
     * @param robot  robot
     * @param result result
     */
    public static void ReStartGame(Robot robot, Result result) {
        Map<String, Integer> bounding = findBounding(result, "取消");
        // 点击取消
        CommonUtil.CommaClick(robot, bounding.get("x"), bounding.get("y"));
    }

    /**
     * 观战
     *
     * @param robot  robot
     * @param result result
     */
    public static void LookGame(Robot robot, Result result) {
        Map<String, Integer> bounding = findBounding(result, "观战");
        // 点击观战
        CommonUtil.CommaClick(robot, bounding.get("x"), bounding.get("y"));
    }

    /**
     * 连接超时
     *
     * @param robot  robot
     * @param result result
     */
    public static void TimeOutGame(Robot robot, Result result) {
        // 点击确定
        Map<String, Integer> bounding = findBounding(result, "确定");
        // 点击观战
        CommonUtil.CommaClick(robot, bounding.get("x"), bounding.get("y"));
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

    /**
     * 查找坐标
     *
     * @param result ocr结果
     * @param word   关键字
     */
    private static Map<String, Integer> findBounding(Result result, String word) {
        Map<String, Integer> bound = new HashMap<>();
        if (Objects.equals(result.getErrorCode(), "0")) {
            for (LineData line : result.getLines()) {
                if (Objects.equals(line.getWords(), word)) {
                    String[] split = line.getBoundingBox().split(StrUtil.COMMA);
                    bound.put("x", (Integer.parseInt(split[0]) + Integer.parseInt(split[2])) / 2);
                    bound.put("y", (Integer.parseInt(split[5]) + Integer.parseInt(split[3])) / 2);
                }
            }
        }
        return bound;
    }

}

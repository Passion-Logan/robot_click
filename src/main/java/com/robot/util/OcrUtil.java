package com.robot.util;

import cn.hutool.http.HttpUtil;

import java.awt.*;
import java.util.HashMap;

import static java.awt.event.KeyEvent.VK_PRINTSCREEN;

/**
 * @author wql
 * @desc CvUtil
 * @date 2023/3/15
 * @lastUpdateUser wql
 * @lastUpdateDesc
 * @lastUpdateTime 2023/3/15
 */
public class OcrUtil {

    public static String ImageOcr(String imgPath) {
        String result = "";
        try {
            Robot robot = new Robot();
            robot.keyPress(VK_PRINTSCREEN);
            robot.keyRelease(VK_PRINTSCREEN);
            robot.delay(800);
            Thread.sleep(1000);
            // 获取截图
            String base64 = CommonUtil.saveImageFromClipboard(imgPath, "temp.jpg");
            // 图片识别
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("imgBase", "data:image/jpg;base64," + base64);
            paramMap.put("lang", "");
            result = HttpUtil.post("https://aidemo.youdao.com/ocrapi1", paramMap);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

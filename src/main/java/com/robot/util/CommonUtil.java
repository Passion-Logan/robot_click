package com.robot.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import static java.awt.event.KeyEvent.VK_ALT;
import static java.awt.event.KeyEvent.VK_TAB;

/**
 * @author wql
 * @desc CommonUtil
 * @date 2023/3/14
 * @lastUpdateUser wql
 * @lastUpdateDesc
 * @lastUpdateTime 2023/3/14
 */
public class CommonUtil {

    /**
     * 读取剪切板在内容
     *
     * @return 内容
     */
    public static String getSysClipboardText() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 获取剪切板中的内容
        Transferable clipTf = sysClip.getContents(null);

        if (clipTf != null) {
            // 检查内容是否是文本类型
            if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    ret = (String) clipTf
                            .getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }


    /**
     * 从剪切板获得图片。
     */
    public static Image getImageFromClipboard() throws Exception {
        Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable cc = sysc.getContents(null);
        if (cc == null)
            return null;
        else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor))
            return (Image) cc.getTransferData(DataFlavor.imageFlavor);
        return null;
    }

    public static void saveImageFromClipboard() throws Exception {
        //获取粘贴板图片
        Image image = getImageFromClipboard();
        File file = new File("D:\\Myfile\\a__temp\\aaaa.png");
        if (file.exists()) {
            file.delete();
        }
        //转成png
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, null, null);
        //ImageIO.write((RenderedImage)bufferedImage, "jpg", file);
        ImageIO.write(bufferedImage, "png", file);
    }

    /**
     * 点击操作
     *
     * @param robot robot
     * @param x     x
     * @param y     y
     */
    public static void CommaClick(Robot robot, int x, int y) {
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(800);
    }

    /**
     * Tab操作
     *
     * @param robot robot
     */
    private static void TabClick(Robot robot) {
        robot.keyPress(VK_ALT);
        robot.keyPress(VK_TAB);
        robot.keyRelease(VK_TAB);
        robot.keyRelease(VK_ALT);
        robot.delay(800);
    }

}

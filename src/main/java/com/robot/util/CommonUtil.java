package com.robot.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.img.ImgUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.*;

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

    public static String saveImageFromClipboard(String imgPath, String imgName) throws Exception {
        //获取粘贴板图片
        Image image = getImageFromClipboard();
        File file = new File(imgPath + "\\\\" + imgName);
        File file2 = new File(imgPath + "\\\\" + "ocr.jpg");
        if (file.exists()) {
            file.delete();
        }
        if (file2.exists()) {
            file2.delete();
        }
        //转成jpg
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        //转成png
//        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, null, null);
        ImageIO.write(bufferedImage, "jpg", file);
//        ImageIO.write(bufferedImage, "png", file);
        ImgUtil.compress(file, file2, 0.5f);
        return file2Base64(file2);
//        System.out.println((s.length() * 0.75 / 1024 / 1024));
    }

    public static String file2Base64(File file) {
        if (file == null) {
            return null;
        }
        String base64 = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte[] buff = new byte[fin.available()];
            fin.read(buff);
            base64 = Base64.encode(buff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    public static File base64ToFile(String base64) {
        if (base64 == null || "".equals(base64)) {
            return null;
        }
        byte[] buff = Base64.decode(base64);
        File file = null;
        FileOutputStream fout = null;
        try {
            file = File.createTempFile("tmp", null);
            fout = new FileOutputStream(file);
            fout.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 鼠标左键点击操作
     *
     * @param robot robot
     * @param x     x
     * @param y     y
     */
    public static void CommaClick(Robot robot, int x, int y) {
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(1000);
    }

    /**
     * 鼠标右键点击操作
     *
     * @param robot robot
     * @param x     x
     * @param y     y
     */
    public static void CommaRightClick(Robot robot, int x, int y) {
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        robot.delay(1000);
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

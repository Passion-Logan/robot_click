package com.robot.entity;

/**
 * @author wql
 * @desc LineData
 * @date 2023/3/15
 * @lastUpdateUser wql
 * @lastUpdateDesc
 * @lastUpdateTime 2023/3/15
 */
public class LineData {

    public LineData(String boundingBox, String words) {
        this.boundingBox = boundingBox;
        this.words = words;
    }

    /**
     * 左上角，右上角，右下角，左下角
     */
    String boundingBox;

    String words;

    public String getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(String boundingBox) {
        this.boundingBox = boundingBox;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}

package com.robot.entity;

import java.util.List;

/**
 * @author wql
 * @desc Result
 * @date 2023/3/15
 * @lastUpdateUser wql
 * @lastUpdateDesc
 * @lastUpdateTime 2023/3/15
 */
public class Result {

    String orientation;

    String errorCode;

    List<LineData> lines;

    public Result(String orientation, String errorCode, List<LineData> lines) {
        this.orientation = orientation;
        this.errorCode = errorCode;
        this.lines = lines;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public List<LineData> getLines() {
        return lines;
    }

    public void setLines(List<LineData> lines) {
        this.lines = lines;
    }
}

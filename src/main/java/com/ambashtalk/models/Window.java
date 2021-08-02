package com.ambashtalk.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Window {

    private Integer pid;
    private String windowTitle, className, filePath;
    private LocSize locSize;
    private boolean active;
    private double duration;
    private long startTime, stopTime;

    @Override
    public String toString() {
        String res = this.isActive() ? "***" : "";
        res += "\t[ " + this.windowTitle + " | " + this.pid + " | " + this.className + " ] " + " [ " + this.filePath + " ] " + this.locSize;
        return res;
    }

    public void setLocSize(Rectangle locSize) {
        this.locSize = new LocSize(locSize.x, locSize.y, locSize.width, locSize.height);
    }
}


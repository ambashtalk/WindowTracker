package com.ambashtalk.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Monitor {
    private String name, screen, workArea, device;
    private boolean primary;

    @Override
    public String toString() {
        String res = "Name:\t\t" + this.name + "\n" +
                "Screen:\t\t" + this.screen + "\n" +
                "Work Area:\t" + this.workArea + "\n" +
                "Primary:\t" + this.primary + "\n" +
                "Device:\t\t" + this.device;
        return res;
    }
}


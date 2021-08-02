package com.ambashtalk.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocSize {
    private int x;
    private int y;
    private int width;
    private int height;
}

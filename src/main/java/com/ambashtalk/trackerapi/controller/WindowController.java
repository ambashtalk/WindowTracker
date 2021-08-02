package com.ambashtalk.trackerapi.controller;

import com.ambashtalk.trackerapi.response.WindowResponse;
import com.ambashtalk.wintrack.WindowInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WindowController {
    @GetMapping("/windows")
    public WindowResponse getWindowList() {
        WindowResponse windowResponse = WindowInfo.getWindowsList();
        return windowResponse;
    }
}

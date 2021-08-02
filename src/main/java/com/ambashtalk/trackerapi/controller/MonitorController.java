package com.ambashtalk.trackerapi.controller;

import com.ambashtalk.trackerapi.response.MonitorResponse;
import com.ambashtalk.wintrack.MonitorInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitorController {
    @GetMapping("/monitors")
    public MonitorResponse getMonitorInfo() {
        MonitorResponse monitorList = MonitorInfo.getMonitorList();
        return monitorList;
    }
}

package com.ambashtalk.trackerapi.response;

import com.ambashtalk.models.Monitor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MonitorResponse {
    private List<Monitor> monitorList;
}

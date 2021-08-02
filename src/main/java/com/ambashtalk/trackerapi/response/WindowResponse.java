package com.ambashtalk.trackerapi.response;

import com.ambashtalk.models.Window;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WindowResponse {
    private List<Window> windowList;
}

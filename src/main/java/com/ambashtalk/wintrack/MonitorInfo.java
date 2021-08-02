package com.ambashtalk.wintrack;


import com.ambashtalk.trackerapi.response.MonitorResponse;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HDC;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HMONITOR;
import com.sun.jna.platform.win32.WinUser.MONITORENUMPROC;
import com.sun.jna.platform.win32.WinUser.MONITORINFOEX;
import com.ambashtalk.models.Monitor;

import java.util.ArrayList;
import java.util.List;

/**
 * A small demo that tests the Win32 monitor API.
 *
 * @author Sudhaker Raj
 */
public class MonitorInfo {

    public static MonitorResponse getMonitorList() {
        List<Monitor> monitorList = new ArrayList<>();

        System.out.println("Installed Physical Monitors: " + User32.INSTANCE.GetSystemMetrics(WinUser.SM_CMONITORS));

        User32.INSTANCE.EnumDisplayMonitors(null, null, new MONITORENUMPROC() {

            @Override
            public int apply(HMONITOR hMonitor, HDC hdc, RECT rect, LPARAM lparam) {
                Monitor monitor = new Monitor();
                MONITORINFOEX info = new MONITORINFOEX();
                User32.INSTANCE.GetMonitorInfo(hMonitor, info);

                monitor.setName(hMonitor.getPointer().toString());
                monitor.setScreen(info.rcMonitor.toString());
                monitor.setWorkArea(info.rcWork.toString());
                monitor.setPrimary((info.dwFlags & WinUser.MONITORINFOF_PRIMARY) != 0) ;
                monitor.setDevice(info.szDevice.toString());
                monitorList.add(monitor);
                return 1;
            }

        }, new LPARAM(0));

        return new MonitorResponse(monitorList);
    }

}

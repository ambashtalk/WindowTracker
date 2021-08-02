package com.ambashtalk.wintrack;

import java.util.ArrayList;
import java.util.List;

import com.ambashtalk.trackerapi.response.WindowResponse;
import com.sun.jna.Native;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.ambashtalk.models.Window;

/**
 * A small demo that tests the Win32 Windows API
 *
 * @author Sudhaker Raj
 */
public class WindowInfo {

    public static WindowResponse getWindowsList() {

        final char[] classText = new char[512];

        HWND hwndActive = User32.INSTANCE.GetForegroundWindow();
        List<Window> windows = new ArrayList<>();

        List<DesktopWindow> windowsList = WindowUtils.getAllWindows(true);
        for (DesktopWindow win : windowsList) {
            Window window = new Window();
            User32.INSTANCE.GetClassName(win.getHWND(), classText, 512);
            window.setWindowTitle(win.getTitle());
            window.setPid(User32.INSTANCE.GetWindowThreadProcessId(win.getHWND(), null));
            window.setClassName(Native.toString(classText));
            window.setFilePath(win.getFilePath());
            window.setLocSize(win.getLocAndSize());
            window.setActive(win.getHWND().equals(hwndActive));

            windows.add(window);
        }
        return new WindowResponse(windows);
    }

}
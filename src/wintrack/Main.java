package wintrack;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.HDC;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser.HMONITOR;
import com.sun.jna.platform.win32.WinUser.MONITORENUMPROC;
import com.sun.jna.platform.win32.WinUser.MONITORINFOEX;

// User Packages
import db.Monitor;
import db.Window;

public class Main {

	static Monitor getMonitorDetails() {
		System.out.println("Installed Physical Monitors: " + User32.INSTANCE.GetSystemMetrics(WinUser.SM_CMONITORS));
		Monitor monitor = new Monitor();
		User32.INSTANCE.EnumDisplayMonitors(null, null, new MONITORENUMPROC() {

			@Override
			public int apply(HMONITOR hMonitor, HDC hdc, RECT rect, LPARAM lparam) {

//				System.out.println("Found HMONITOR: " + hMonitor.getPointer().toString());
				monitor.setName(hMonitor.getPointer().toString());
				MONITORINFOEX info = new MONITORINFOEX();
				User32.INSTANCE.GetMonitorInfo(hMonitor, info);
//				System.out.println("Screen " + info.rcMonitor);
				monitor.setscreen(info.rcMonitor.toString());
//				System.out.println("Work area " + info.rcWork);
				monitor.setWorkArea(info.rcWork.toString());
//				boolean isPrimary = (info.dwFlags & WinUser.MONITORINFOF_PRIMARY) != 0;
				monitor.setPrimary((info.dwFlags & WinUser.MONITORINFOF_PRIMARY) != 0);
//				System.out.println("Primary? " + (isPrimary ? "yes" : "no"));
//				System.out.println("Device " + new String(info.szDevice));
				monitor.setDevice(new String(info.szDevice));
				return 1;
			}

		}, new LPARAM(0));
		
		return monitor;
	}
	
	static List<Window> getWindows() {
		final char[] classText = new char[512];
		HWND hwndActive = User32.INSTANCE.GetForegroundWindow();
		List<DesktopWindow> windowsList = WindowUtils.getAllWindows(true);
		List<Window> result = new ArrayList<Window>();
		for (DesktopWindow win : windowsList) {
			Window ob = new Window();
			
			User32.INSTANCE.GetClassName(win.getHWND(), classText, 512);
//			String wClass = Native.toString(classText);
			ob.setclass(Native.toString(classText));
			
//			int pid = User32.INSTANCE.GetWindowThreadProcessId(win.getHWND(), null);
			ob.setPID(User32.INSTANCE.GetWindowThreadProcessId(win.getHWND(), null));
			
			ob.setActive(win.getHWND().equals(hwndActive));
//			String fg = win.getHWND().equals(hwndActive) ? " *** " : "";
//			String fg = ob.isActive() ? Window.MARKER : "";
//			System.out.println("[ " + fg + win.getTitle() + " | " + pid + " | " + wClass + " ] [ " + win.getFilePath() + " ] " + win.getLocAndSize());
//			String res = "[ " + fg + ob.getWinTitle() + " | " + ob.getPID() + " | " + ob.getclass() + " ] [ " + ob.getFilePath() + " ] " + ob.getLocSize() + "\n";
			ob.setFilePath(win.getFilePath());
			ob.setWinTitle(win.getTitle());
			result.add(ob);
		}
		return result;
	}
	
	public static void main(String[] args) {
		Monitor ob = getMonitorDetails();
		System.out.println(ob + "\n");
		
		List<Window> allWindows = getWindows();
		for(Window w: allWindows) {
			System.out.println(w);
		}
	}
}

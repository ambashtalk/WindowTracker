package wintrack;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.HDC;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser.HMONITOR;
import com.sun.jna.platform.win32.WinUser.MONITORENUMPROC;
import com.sun.jna.platform.win32.WinUser.MONITORINFOEX;

// User Packages
import db.Monitor;

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
	
	public static void main(String[] args) {
		Monitor ob = getMonitorDetails();
		System.out.println(ob);
	}
}

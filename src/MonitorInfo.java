import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HDC;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HMONITOR;
import com.sun.jna.platform.win32.WinUser.MONITORENUMPROC;
import com.sun.jna.platform.win32.WinUser.MONITORINFOEX;
/**
 * A small demo that tests the Win32 monitor API.
 * 
 * @author Sudhaker Raj
 */
public class MonitorInfo {

	/**
	 * @param args (ignored)
	 */
	public static void main(String[] args) {

		System.out.println("Installed Physical Monitors: " + User32.INSTANCE.GetSystemMetrics(WinUser.SM_CMONITORS));

		User32.INSTANCE.EnumDisplayMonitors(null, null, new MONITORENUMPROC() {

			@Override
			public int apply(HMONITOR hMonitor, HDC hdc, RECT rect, LPARAM lparam) {

				System.out.println("Found HMONITOR: " + hMonitor.getPointer().toString());

				MONITORINFOEX info = new MONITORINFOEX();
				User32.INSTANCE.GetMonitorInfo(hMonitor, info);
				System.out.println("Screen " + info.rcMonitor);
				System.out.println("Work area " + info.rcWork);
				boolean isPrimary = (info.dwFlags & WinUser.MONITORINFOF_PRIMARY) != 0;
				System.out.println("Primary? " + (isPrimary ? "yes" : "no"));
				System.out.println("Device " + new String(info.szDevice));

				return 1;
			}

		}, new LPARAM(0));

	}

}
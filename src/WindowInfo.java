import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

/**
 * A small demo that tests the Win32 Windows API
 * 
 * @author Sudhaker Raj
 */
public class WindowInfo {

	/**
	 * @param args (ignored)
	 */
	public static void main(String[] args) {

		final char[] classText = new char[512];

		HWND hwndActive = User32.INSTANCE.GetForegroundWindow();

		List<DesktopWindow> windowsList = WindowUtils.getAllWindows(true);
		for (DesktopWindow win : windowsList) {
			User32.INSTANCE.GetClassName(win.getHWND(), classText, 512);
			String wClass = Native.toString(classText);
			int pid = User32.INSTANCE.GetWindowThreadProcessId(win.getHWND(), null);
			String fg = win.getHWND().equals(hwndActive) ? " *** " : "";
			System.out.println("[ " + fg + win.getTitle() + " | " + pid + " | " + wClass + " ] [ " + win.getFilePath()
					+ " ] " + win.getLocAndSize());
		}

		System.out.println("==============================");
	}

}
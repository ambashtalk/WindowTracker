package wintrack;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.json.simple.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

public class Main extends TimerTask {
	
	// limit var: time after which it shoud send a POST request
	public final long t = 15 * 1000; // time in milliseconds
	public final long limit = (long) t / TimeIt.period; //limit value of "calls" variable
	
	// keeping track of time:
	// when calls reaches 60, means 3 mins have elapsed and time for POST request
	static int calls = 0;
	
	// JSON Object to hold data of every 3 min
	public static JSONObject json = new JSONObject();
	
	// Store previous active window
	public static Window prevActiveWin = null;
	
	static String pretty(JSONObject json) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}
	
	static Monitor getMonitorDetails() {
		Monitor monitor = new Monitor();
//		System.out.println("Installed Physical Monitors: " + User32.INSTANCE.GetSystemMetrics(WinUser.SM_CMONITORS));
		Monitor.Qty = User32.INSTANCE.GetSystemMetrics(WinUser.SM_CMONITORS);
		
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
				monitor.setDevice(new String(info.szDevice).trim());
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
			Window currWin = new Window();
			
			User32.INSTANCE.GetClassName(win.getHWND(), classText, 512);
//			String wClass = Native.toString(classText);
			currWin.setclass(Native.toString(classText));
			
//			int pid = User32.INSTANCE.GetWindowThreadProcessId(win.getHWND(), null);
			currWin.setPID(User32.INSTANCE.GetWindowThreadProcessId(win.getHWND(), null));
			
			currWin.setActive(win.getHWND().equals(hwndActive));
			
			//set running duration
			if (currWin.isActive()) {
				if (prevActiveWin == null) {
					prevActiveWin = currWin;
					currWin.recordStartTime();
				}
				else {
					if (prevActiveWin.getPID() != currWin.getPID()) { // it is a new active window
						prevActiveWin.recordStopTime(); // record active duration of prev window
						currWin.recordStartTime();
						prevActiveWin = currWin;
					}
				}
			}
			
//			String fg = win.getHWND().equals(hwndActive) ? " *** " : "";
//			String fg = ob.isActive() ? Window.MARKER : "";
//			System.out.println("[ " + fg + win.getTitle() + " | " + pid + " | " + wClass + " ] [ " + win.getFilePath() + " ] " + win.getLocAndSize());
//			String res = "[ " + fg + ob.getWinTitle() + " | " + ob.getPID() + " | " + ob.getclass() + " ] [ " + ob.getFilePath() + " ] " + ob.getLocSize() + "\n";
			currWin.setFilePath(win.getFilePath().toString());
			currWin.setWinTitle(win.getTitle().toString());
			currWin.setLocSize(win.getLocAndSize());
			
//			System.out.println(ob.getLocSize().toString());
			result.add(currWin);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	static JSONObject getMonitorJson(Monitor mtr) {
		JSONObject detail = new JSONObject();
		JSONObject detail_entry = new JSONObject();
		// add number of monitors
		detail.put("quantity", Monitor.Qty);
		
		// add monitor details
		detail_entry.put("name", mtr.getName());
		detail_entry.put("screen", mtr.getScreen());
		detail_entry.put("work_area", mtr.getWorkArea());
		detail_entry.put("device", mtr.getDevice());
		detail_entry.put("primary", mtr.isPrimary());
		
		// Check primary monitor
		detail.put("details", detail_entry);
		if (mtr.isPrimary()) {
			detail.put("primary", detail_entry);
		}
		
		return detail;
	}
	
	@SuppressWarnings("unchecked")
	static JSONObject getWindowJson(List<Window> win) {
		JSONObject details = new JSONObject();
		JSONObject detail_entry = new JSONObject();
		JSONObject active_win = new JSONObject();
		JSONObject loc = new JSONObject();
		for (Window w: win) {
			String s = w.getclass();
			System.out.println(s);
			detail_entry.clear();
			loc.clear();
//			detail_entry.compute("test", (k,v) -> (v==null) ? w.getclass() : "none");
			detail_entry.put("title", w.getWinTitle());
			detail_entry.put("class", w.getclass());
			detail_entry.put("file_path", w.getFilePath());
			detail_entry.put("duration", w.getDuration());
			
			loc.put("x", w.getLocSize().x);
			loc.put("y", w.getLocSize().y);
			loc.put("height", w.getLocSize().height);
			loc.put("width", w.getLocSize().width);
			
			detail_entry.put("loc_size", loc);
			
			if (w.isActive()) {
				active_win = detail_entry;
			}
			
			details.put(w.getPID().toString(), detail_entry);
		}
		
		JSONObject output = new JSONObject();
		output.put("all", details);
		output.put("active", active_win);
		
		return output;
	}
	
	@SuppressWarnings("unchecked")
	static JSONObject getJSON(Monitor mtr, List<Window> win) {
		JSONObject mtrDetailsJson = getMonitorJson(mtr);
		JSONObject winDetailsJson = getWindowJson(win);
		
		JSONObject output = new JSONObject();
		output.put("Monitor", mtrDetailsJson);
		output.put("Windows", winDetailsJson);
		
		return output;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		
		// Keeping track of calls
		calls++;
		System.out.println("This is call number:" + calls);
		
		Monitor monitor = getMonitorDetails();
//		System.out.println(monitor + "\n");
		
		List<Window> allWindows = getWindows();
//		for(Window w: allWindows) {
//			System.out.println(w);
//		}
		JSONObject details = new JSONObject();
		details = getJSON(monitor, allWindows);
//		System.out.print(pretty(details));
		
		// maintaining a static json object
		String key = LocalDateTime.now().toString();
		json.put(key, details);
		
		if (calls == limit) {
			// Writing to File
			try (FileWriter fw = new FileWriter("info.json")) {
				fw.write(pretty(json));
				fw.flush();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
			// Reset JSONObjects and timer for next period
			json.clear();
			calls = 0;
			System.out.println("Call Reset\n\n");
		}
	}
}

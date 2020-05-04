package db;

public class Monitor {

	private String name, screen, work_area, device;
	private boolean primary;
	Monitor(boolean primary, String name, String screen, String work_area, String device) {
		this.name = name == null? "default": name;
		this.screen = screen== null? "default": screen;
		this.work_area = work_area== null? "default": work_area;
		this.device = device== null? "default": device;
		this.primary = primary;
	}
	
	//Setter and Getters
	// Name
	void setName(String name) {
		this.name = name;
	}
	
	String getName() {
		return this.name;
	}
	
	// Screen
	void setscreen(String screen) {
		this.screen = screen;
	}
	
	String getScreen() {
		return this.screen;
	}
	
	// Work Area
	void setWorkArea(String work_area) {
		this.work_area = work_area;
	}
	
	String getWorkArea() {
		return this.work_area;
	}
	
	// Device
	void setDevice(String device) {
		this.device = device;
	}
	
	String getDevice() {
		return this.device;
	}
	
	// Is Primary Display 
	void setPrimary(boolean primary) {
		this.primary = primary;
	}
	
	boolean isPrimary() {
		return this.primary;
	}
}

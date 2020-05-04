package db;

public class Monitor {
	
	private String name, screen, work_area, device;
	private boolean primary;
	
	public Monitor(){
		this.name = "nil";
		this.screen = "nil";
		this.work_area = "nil";
		this.device = "nil";
		this.primary = false;
	}
	
	public Monitor(boolean primary, String name, String screen, String work_area, String device) {
		this.name = name == null? "default": name;
		this.screen = screen== null? "default": screen;
		this.work_area = work_area== null? "default": work_area;
		this.device = device== null? "default": device;
		this.primary = primary;
	}
	
	@Override
	public String toString() {
		String res = "Name:\t\t" + this.name + "\n" +
					 "Screen:\t\t" + this.screen + "\n" +
					 "Work Area:\t" + this.work_area + "\n" +
					 "Primary:\t" + this.primary + "\n" +
					 "Device:\t\t" + this.device;
		return res;
	}
	
	//Setter and Getters
	// Name
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	// Screen
	public void setscreen(String screen) {
		this.screen = screen;
	}
	
	public String getScreen() {
		return this.screen;
	}
	
	// Work Area
	public void setWorkArea(String work_area) {
		this.work_area = work_area;
	}
	
	public String getWorkArea() {
		return this.work_area;
	}
	
	// Device
	public void setDevice(String device) {
		this.device = device;
	}
	
	public String getDevice() {
		return this.device;
	}
	
	// Is Primary Display 
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	
	public boolean isPrimary() {
		return this.primary;
	}
}

package db;

import java.awt.Rectangle;
import java.awt.Dimension;

public class Window {
	private final static Dimension NULL_DIM = new Dimension(30, 30);
	public final static String MARKER = "***";
	
	private Integer pid;
	private String win_title, _class, file_path;
	private Rectangle loc_size;
	private boolean active;
	
	public Window() {
		this.pid =0;
		this.active = false;
		this.win_title = "";
		this._class = "";
		this.file_path = "";
		this.loc_size = new Rectangle(NULL_DIM);
	}
	
	public Window(Integer pid, String win_title, String _class, String file_path, Rectangle loc_size, boolean active) {
		this.pid = pid==null? 0 :pid;
		this.win_title = pid==null? "default" :win_title;
		this._class = pid==null? "default" :_class;
		this.file_path = pid==null? "default" :file_path;
		this.loc_size = pid==null? new Rectangle(NULL_DIM) :loc_size;
		this.active = active;
	}
	
	@Override
	public String toString() {
		String res = this.isActive() ? Window.MARKER : "";
		res += "\t[ " + this.win_title + " | " + this.pid + " | " + this._class + " ] " + " [ " + this.file_path + " ] " + this.loc_size;
		return res;
	}
	
	//Getter and Setter Methods
	//PID
	public void setPID(Integer pid) {
		this.pid = pid;
	}
	
	public Integer getPID() {
		return this.pid;
	}
	
	//Window Title
	public void setWinTitle(String win_title) {
		this.win_title = win_title;
	}
	
	public String getWinTitle() {
		return this.win_title;
	}
	
	//Class
	public void setclass(String _class) {
		this._class = _class;
	}
	
	public String getclass() {
		return this._class;
	}
	//File Path
	public void setFilePath(String path) {
		this.file_path = path;
	}
	
	public String getFilePath() {
		return this.file_path;
	}
	//Location and Size
	public void setLocSize(Rectangle loc_size) {
		this.loc_size = loc_size;
	}
	
	public Rectangle getLocSize() {
		return this.loc_size;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return this.active;
	}
}

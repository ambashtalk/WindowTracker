package db;

import java.awt.Rectangle;
import java.awt.Dimension;

public class Window {
	private Dimension null_dim = new Dimension(30, 30);
	private Integer pid;
	private String win_title, _class, file_path;
	private Rectangle loc_size;
	
	Window(Integer pid, String win_title, String _class, String file_path, Rectangle loc_size) {
		this.pid = pid==null? 0 :pid;
		this.win_title = pid==null? "default" :win_title;
		this._class = pid==null? "default" :_class;
		this.file_path = pid==null? "default" :file_path;
		this.loc_size = pid==null? new Rectangle(null_dim) :loc_size;
	}
	
	@Override
	public String toString() {
		String res = "[ " + this.win_title + " | " + this.pid + " | " + this._class + " ] " + " [ " + this.file_path + " ] " + this.loc_size + "\n" ;
		return res;
	}
	
	//Getter and Setter Methods
	//PID
	void setPID(Integer pid) {
		this.pid = pid;
	}
	
	Integer getPID() {
		return this.pid;
	}
	
	//Window Title
	void setWinTitle(String win_title) {
		this.win_title = win_title;
	}
	
	String getWinTitle() {
		return this.win_title;
	}
	
	//Class
	void setclass(String _class) {
		this._class = _class;
	}
	
	String getclass() {
		return this._class;
	}
	//File Path
	void setFilePath(String path) {
		this.file_path = path;
	}
	
	String getFilePath() {
		return this.file_path;
	}
	//Location and Size
	void setLocSize(Rectangle loc_size) {
		this.loc_size = loc_size;
	}
	
	Rectangle getLocSize() {
		return this.loc_size;
	}
}

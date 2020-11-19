package lib.clearclass.web;

public class CLParams {
	String item;
	
	String unit;
	String ctrl;
	Boolean u_sel;
	
	String cc_manuf;
	String c_mode;
	
	Integer c_lines;
	Integer d_lines;
	Integer r_lines;
	Integer u_lines;
		
	public CLParams(String item, String unit, String ctrl, 
			Boolean u_sel, String cc_manuf, String c_mode, Integer c_lines, 
			Integer d_lines, Integer r_lines, Integer u_lines) {
		
		this.item = item;
		this.unit = unit;
		this.ctrl = ctrl;
		this.u_sel = u_sel;
		this.cc_manuf = cc_manuf;
		this.c_mode = c_mode;
		this.c_lines = c_lines;
		this.d_lines = d_lines;
		this.r_lines = r_lines;
		this.u_lines = u_lines;
	}
	
	public String getItem() {
		return item;
	}
	public String getUnit() {
		return u_sel? unit : ctrl;
	}
	public String getCC_manuf() {
		return cc_manuf;
	}
	public String c_mode() {
		return c_mode;
	}
	public Integer c_lines() {
		return c_lines;
	}
	public Integer d_lines() {
		return d_lines;
	}
	public Integer r_lines() {
		return r_lines;
	}
	public Integer u_lines() {
		return u_lines;
	}
}
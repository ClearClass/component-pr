package lib.clearclass.model;

// представляет одну строку в спецификации
public class SPRow {
	private String pos;
	private String name;
	private String amount;
	private String note;
	
	public SPRow(String pos, String name, String amount, String note){
		this.pos = pos;
		this.name = name;
		this.amount = amount;
		this.note = note;
	}
	public SPRow(String name, String note){
		this("", name, "", note);
	}
	public SPRow(String name){
		this(name, "");
	}
	public SPRow(){
		this("");
	}
	
	public String getPos() {
		return pos;
	}
	public String getName() {
		return name;
	}
	public String getAmount() {
		return amount;
	}
	public String getNote() {
		return note;
	}
}
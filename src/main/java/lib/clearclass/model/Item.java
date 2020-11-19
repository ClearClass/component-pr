package lib.clearclass.model;

import java.util.List;

public class Item {
	private String name;
	private List<String> units;
	private List<String> contrs;
	
	public Item(String name, List<String> units, List<String> contrs) {
		this.name = name;
		this.units = units;
		this.contrs = contrs;
	}

	public String getName() {
		return name;
	}

	public List<String> getUnits() {
		return units;
	}

	public List<String> getContrs() {
		return contrs;
	}
}
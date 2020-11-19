package lib.clearclass.model;

public class CSRow {
	private String rnum;
	private String name;
	private Boolean isNewCategory = false; 
	private String spec;
	private String unit;
	private String amount1;
	private String amount2;
	private Boolean bold2 = false;
	private Boolean isDark = false;

	public CSRow(Integer num, String name, String spec, 
			String unit, Integer amount1, Integer amount2, Boolean isDark) {
		this.rnum = num.toString();
		this.name = name;
		this.spec = spec;
		this.unit = unit;
		this.amount1 = (amount1!=null)? amount1.toString() : "";
		this.amount2 = (amount2!=null)? amount2.toString() : "";
		this.isDark = isDark;
	}
	
	public CSRow(Integer num, String name, String unit, 
			Integer amount1, Integer amount2, Boolean isDark) {
		this(num, name, "", unit, amount1, amount2, isDark);
	}
	
	public CSRow(Integer num, String name, Boolean isDark) {
		this(num, name, "", "", null, null, isDark);
	}
	
	public CSRow(Integer num, String unit, Integer amount1, Integer amount2, Boolean isDark) {
		this(num, "", "", unit, amount1, amount2, isDark);
	}

	public CSRow(Integer num, Integer amount2, Boolean isDark) {
		this(num, "", "", "", null, amount2, isDark);
		this.bold2 = true;
	}
	
	public CSRow(Integer num, Boolean isDark) {
		this(num, "", "", "", null, null, isDark);
	}

	public String getRnum() {
		return rnum;
	}

	public String getName() {
		return name;
	}

	public String getSpec() {
		return spec;
	}

	public String getUnit() {
		return unit;
	}

	public String getAmount1() {
		return amount1;
	}

	public String getAmount2() {
		return amount2;
	}
	
	public boolean getBold2() {
		return bold2;
	}

	public Boolean getIsNewCategory() {
		return isNewCategory;
	}

	public CSRow setNewCategory() {
		this.isNewCategory = true;
		return this;
	}

	public Boolean getIsDark() {
		return isDark;
	}
}
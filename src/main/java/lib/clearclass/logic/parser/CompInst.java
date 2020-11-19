package lib.clearclass.logic.parser;

public class CompInst implements Comparable<CompInst>{
	private String refdes;
	private String originalName;
	private String compValue;
	private String spec;
	private String manufact;
	private String mark;
	
	public CompInst(String refdes, String originalName, 
			String compValue, String spec, String manufact, String mark) {
		this.refdes = refdes;
		this.originalName = originalName;
		this.compValue = compValue;
		this.spec = spec;
		this.manufact = manufact;
		this.mark = mark;
	}
	
	public static CompInst getInstWithSpec(String refdes, 
			String originalName, String compValue, String spec){
		return new CompInst(refdes, originalName, compValue, spec, "", "");
	}
	
	public static CompInst getInst(String refdes, 
			String originalName, String compValue){
		return new CompInst(refdes, originalName, compValue, "", "", "");
	}

	public String getRefdes() {
		return refdes;
	}

	public String getOriginalName() {
		return originalName;
	}

	public String getCompValue() {
		return compValue;
	}

	public String getSpec() {
		return spec;
	}

	public String getManufacturer() {
		return manufact;
	}
	
	public String getMark() {
		return mark;
	}
	
	@Override
	public int compareTo(CompInst ob) {
		Character ch0 = refdes.charAt(0);
		Character ch0_ = ob.refdes.charAt(0);
		
		if(ch0.compareTo(ch0_)!=0)
			return ch0.compareTo(ch0_);
		
		Character ch1 = refdes.charAt(1);
		Character ch1_ = ob.refdes.charAt(1);
		
		if(Character.isDigit(ch1) && Character.isDigit(ch1_)){
			Integer num = Integer.parseInt(refdes.substring(1));
			Integer num_ = Integer.parseInt(ob.refdes.substring(1));
			return num.compareTo(num_);
		}
		
		if(ch1.compareTo(ch1_)!=0)
			return ch1.compareTo(ch1_);
		
		Integer num = Integer.parseInt(refdes.substring(2));
		Integer num_ = Integer.parseInt(ob.refdes.substring(2));
		
		return num.compareTo(num_);
	}

	@Override
	public String toString() {
		return String.format("%-8s %-20s %-20s %-20s %-25s %-25s", 
				refdes, originalName, compValue, spec, manufact, mark);
	}
}
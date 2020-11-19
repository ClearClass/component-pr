package lib.clearclass.entity;

import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import lib.clearclass.logic.OrderCodes;

@Entity
@Table(name = "c_types")
@NamedNativeQuery(name = "CType", query = "SELECT * FROM c_types WHERE id IN " +
"(SELECT DISTINCT type_id FROM c WHERE item = :item AND unit IN :units)", resultClass = CType.class)
public class CType extends AbstractType {
	public enum Manufact {Kemet, Murata, AVX}
	
	private static Manufact manufact = Manufact.Kemet;
	
	public static Manufact getManufact() {
		return manufact;
	}
	
	public static void setManufact(String manufact) {
		if(manufact.equalsIgnoreCase("kemet"))
			CType.manufact = Manufact.Kemet;
		else if(manufact.equalsIgnoreCase("murata"))
			CType.manufact = Manufact.Murata;
		else if(manufact.equalsIgnoreCase("avx"))
			CType.manufact = Manufact.AVX;
		else throw new IllegalArgumentException(manufact);
	}
	
	@ManyToOne
	@JoinColumn(name = "type")
	private CSpec   cSpec;
	private String  diel;
	private Integer volt;
	private String  size;
	private Double  value;
	private String  dim;
	private Integer toler;
	private Boolean metal;
	
	public CType(CSpec cSpec, String diel, Integer volt, String size, 
			Double value, String dim, Integer toler, Boolean metal){
		this.cSpec = cSpec;
		this.diel  = diel;
		this.volt  = volt;
		this.size  = size;
		this.value = value;
		this.dim   = dim;
		this.toler = toler;
		this.metal = metal;
	}
	
	public CType(){
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cSpec == null) ? 0 : cSpec.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((diel == null) ? 0 : diel.hashCode());
		result = prime * result + ((dim == null) ? 0 : dim.hashCode());
		result = prime * result + ((metal == null) ? 0 : metal.hashCode());
		result = prime * result + ((toler == null) ? 0 : toler.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((volt == null) ? 0 : volt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object ob) {
		if (this == ob)
			return true;
		if (ob == null)
			return false;
		if (getClass() != ob.getClass())
			return false;
		CType other = (CType) ob;
		if (cSpec == null) {
			if (other.cSpec != null)
				return false;
		} else if (!cSpec.equals(other.cSpec))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (diel == null) {
			if (other.diel != null)
				return false;
		} else if (!diel.equals(other.diel))
			return false;
		if (dim == null) {
			if (other.dim != null)
				return false;
		} else if (!dim.equals(other.dim))
			return false;
		if (metal == null) {
			if (other.metal != null)
				return false;
		} else if (!metal.equals(other.metal))
			return false;
		if (toler == null) {
			if (other.toler != null)
				return false;
		} else if (!toler.equals(other.toler))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (volt == null) {
			if (other.volt != null)
				return false;
		} else if (!volt.equals(other.volt))
			return false;
		return true;
	}

	@Override
	public String getType() {
		return cSpec.getType();
	}
	
	@Override
	public String getTypeName() {
		return "Конденсатор" + (metal? " луженый" : "");
	}
	
	@Override
	public String getTypeNames() {
		return "Конденсаторы" + (metal? " луженые" : "");
	}
	
	@Override
	public String getGenTypeName() {
		return "Конденсатор";
	}

	@Override
	public String getGenTypeNames() {
		return "Конденсаторы";
	}

	@Override
	public String getSpec() {
		if(getType().equals("CC"))
			return manufact.name();
		if(getType().equals("CC_TAJ"))
			return "Kemet";
		return cSpec.getSpec();
	}

	public Boolean getMetal() {
		return metal;
	}

	@Override
	public String toString() {
		if(getType().equals("CC") && manufact==Manufact.Kemet) // Kemet ceramic
			return OrderCodes.forKemet(value, dim, toler, diel, size, volt);
		
		if(getType().equals("CC") && manufact==Manufact.Murata) // Murata ceramic (GRM series)
			return OrderCodes.forMurata(value, dim, toler, diel, size, volt);
						
		if(getType().equals("CC") && manufact==Manufact.AVX)
			return OrderCodes.forAVX(value, dim, toler, diel, size, volt);
		
		if(getType().equals("CC_TAJ")) // Kemet tanatal T491 series
			return OrderCodes.forTantal(value, dim, toler, diel, size, volt);

		if(getType().equals("B41851"))
			return OrderCodes.forEpcos(value, dim, toler, diel, size, volt);
		
		
		// для отечественных элементов
		String val = value.toString();
		val = val.endsWith(".0")? val.substring(0, val.length()-2) : val.replace('.', ',');
		String tol = (toler==null)? "" : ((toler.intValue()==8020)? "+80-20 %" : ("± " + toler + " %"));
		if(diel.equals("ЭЛТ"))
			return getType() + " \"" + size + "\"-" + volt + " В-" + val + " " + dim + "Ф " + tol;
		if(getType().equals("К10-47Ма"))
			return "К10-47Ма-" + volt + " В-" + val + " " + dim + "Ф" + "-" + diel;
		return getType() + "-" + diel + "-" + volt + " В-" + size + "-" + val + " " + dim + "Ф " + tol;
	}
	
	@Override
	public String toNote() {
		HashMap<String, String> dims = new HashMap<>();
		dims.put("п",  "p");
		dims.put("н",  "n");
		dims.put("мк", "u");
		dims.put("м",  "m");
		dims.put("",   "");
		dims.put("к",  "k");
		dims.put("М",  "M");
		
		String valSt = value.toString();
		if((toler>=5 || dim.equals("п")) && valSt.endsWith(".0"))
			valSt = valSt.substring(0, valSt.length()-2);
		
		String vdt = valSt + dims.get(dim) + "F±" + toler + "% ";
		
		if(getType().equals("CC"))
			return size + " " + vdt + diel + " " + volt + "V";
		if(getType().equals("CC_TAJ"))
			return vdt + volt + "V " + "(case_" + size.toLowerCase() + ")";
		if(getType().equals("B41851"))
			return vdt + volt + "V " + "(" + size.toLowerCase().replace("x", "×") + ")";
		return "";
	}
}
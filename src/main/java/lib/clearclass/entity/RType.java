package lib.clearclass.entity;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import lib.clearclass.logic.OrderCodes;

@Entity
@Table(name = "r_types")
@NamedNativeQuery(name = "RType", query = "SELECT * FROM r_types WHERE id IN " +
"(SELECT DISTINCT type_id FROM r WHERE item = :item AND unit IN :units)", resultClass = RType.class)
public class RType extends AbstractType {
	@ManyToOne
	@JoinColumn(name = "type")
	private RSpec rSpec;
	private Double power;
	private String size; // опциональный параметр
	private Double value;
	private String dim;
	private Integer toler;
	
	public RType(RSpec rSpec, Double power, String size, 
			Double value, String dim, Integer toler) {
		this.rSpec = rSpec;
		this.power = power;
		this.size = size;
		this.value = value;
		this.dim = dim;
		this.toler = toler;
	}
	
	public RType(RSpec rSpec, String size, Double value, 
			String dim, Integer toler) {
		this(rSpec, null, size, value, dim, toler);
	}
	
	public RType() {
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dim == null) ? 0 : dim.hashCode());
		result = prime * result + ((power == null) ? 0 : power.hashCode());
		result = prime * result + ((rSpec == null) ? 0 : rSpec.hashCode());
		result = prime * result + ((toler == null) ? 0 : toler.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		RType other = (RType) ob;
		if (dim == null) {
			if (other.dim != null)
				return false;
		} else if (!dim.equals(other.dim))
			return false;
		if (power == null) {
			if (other.power != null)
				return false;
		} else if (!power.equals(other.power))
			return false;
		if (rSpec == null) {
			if (other.rSpec != null)
				return false;
		} else if (!rSpec.equals(other.rSpec))
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
		return true;
	}
	
	public String getCase() {
		return size;
	}
	
	@Override
	public String getType() {
		return rSpec.getType();
	}
	
	@Override
	public String getTypeName() {
		return "Резистор";
	}
	
	@Override
	public String getTypeNames() {
		return "Резисторы";
	}

	@Override
	public String getSpec() {
		return rSpec.getSpec();
	}
	
	@Override
	public String toString() {
		if(getType().equals("CR")) // ..для импортных резисторов ("Bourns")
			return OrderCodes.forBourns(size, value, dim, toler);
		
		String extr = getType().equals("Р1-12")? "-М-\"А\"" : "";
		String val = value.toString();
		val = val.endsWith(".0")? val.substring(0, val.length()-2) : val.replace('.', ',');
		String pwr = power.toString().replace('.', ',');
		return getType() + "-" + pwr + "-" + val + " " + dim + "Ом" + " ± " + toler + " %" + extr;
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
		if(valSt.endsWith(".0")) //toler>=5 &&
			valSt = valSt.substring(0, valSt.length()-2);
		
		if(getType().equals("CR"))
			return size + " " + valSt + " " + dims.get(dim) + "Ω±" + toler + "%";
		return "";
	}
}
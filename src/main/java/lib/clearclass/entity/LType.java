package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "l_types")
@NamedNativeQuery(name = "LType", query = "SELECT * FROM l_types WHERE id IN " +
"(SELECT DISTINCT type_id FROM l WHERE item = :item AND unit IN :units)", resultClass = LType.class)
public class LType extends AbstractType {
	private String type;
	private Double value;
	private String dim;
	private Integer toler;
	private String spec;
	
	public LType(String type, Double value, String dim, Integer toler, String spec) {
		this.type = type;
		this.value = value;
		this.dim = dim;
		this.toler = toler;
		this.spec = spec;
	}

	public LType(String type, String spec) {
		this.type = type;
		this.spec = spec;
	}
	
	public LType() {
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dim == null) ? 0 : dim.hashCode());
		result = prime * result + ((toler == null) ? 0 : toler.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		LType other = (LType) ob;
		if (dim == null) {
			if (other.dim != null)
				return false;
		} else if (!dim.equals(other.dim))
			return false;
		if (toler == null) {
			if (other.toler != null)
				return false;
		} else if (!toler.equals(other.toler))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public String getTypeName() {
		if(type.startsWith("ДФ"))
			return "Дроссель фильтрации выходной";
		if(type.startsWith("ДМ"))
			return "Дроссель высокочастотный";
		return "Дроссель";
	}
	
	@Override
	public String getTypeNames() {
		if(type.startsWith("ДФ"))
			return "Дроссели фильтрации выходные";
		if(type.startsWith("ДМ"))
			return "Дроссели высокочастотные";
		return "Дроссели";
	}

	@Override
	public String getGenTypeName() {
		return "Дроссель";
	}

	@Override
	public String getGenTypeNames() {
		return "Дроссели";
	}

	@Override
	public String getSpec() {
		return spec;
	}
	
	@Override
	public String toString() {
		String type = getType().replace('.', ',');
		String val = (value==null)? "" : "-" + value.toString();
		val = val.endsWith(".0")? val.substring(0, val.length()-2) : val.replace('.', ',');
		String tol = (toler==null)? "" : " ± " + toler.toString() + "%";
		String extr = type.startsWith("ДМ")? " В" : "";
		return type + val + tol + extr;
	}
}
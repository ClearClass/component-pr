package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "h_types")
@NamedNativeQuery(name = "HType", query = "SELECT * FROM h_types WHERE id IN " +
"(SELECT DISTINCT type_id FROM h WHERE item = :item AND unit IN :units)", resultClass = HType.class)
public class HType extends AbstractType {
	private String type;
	private String spec;

	public HType(String type, String spec) {
		this.type = type;
		this.spec = spec;
	}
	
	public HType(){
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		HType other = (HType) ob;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public String getTypeName() {
		if(type.equals("L-603G"))
			return "Индикатор единичный";
		return "---------------";
	}
	
	@Override
	public String getTypeNames() {
		return "Индикаторы";
	}
	
	@Override
	public String getSpec() {
		return spec!=null? spec : "";
	}
}
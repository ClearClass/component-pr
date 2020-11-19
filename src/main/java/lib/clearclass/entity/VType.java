package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "v_types")
@NamedNativeQuery(name = "VType", query = "SELECT * FROM v_types WHERE id IN " +
"(SELECT DISTINCT type_id FROM v WHERE item = :item AND unit IN :units)", resultClass = VType.class)
public class VType extends AbstractType {
	private String type;
	private String spec;

	public VType(String type, String spec) {
		this.type = type;
		this.spec = spec;
	}
	
	public VType(){
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
		VType other = (VType) ob;
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
		if(type.equals("1SMA5914BT3"))
			return "Стабилитрон";
		return "[---------]";
	}
	
	@Override
	public String getTypeNames() {
		return "Приборы полупроводниковые";
	}

	@Override
	public String getSpec() {
		return spec!=null? spec : "";
	}
}
package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "u_types")
@NamedNativeQuery(name = "UType", query = "SELECT * FROM u_types WHERE id IN " +
"(SELECT DISTINCT type_id FROM u WHERE item = :item AND unit IN :units)", resultClass = UType.class)
public class UType extends AbstractType {
	private String type;
	private String spec;

	public UType(String type, String spec) {
		this.type = type;
		this.spec = spec;
	}
	
	public UType(){
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
		UType other = (UType) ob;
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
		return "Модуль питания";
	}
	
	@Override
	public String getTypeNames() {
		return "Модули питания";
	}

	@Override
	public String getSpec() {
		return spec;
	}
}
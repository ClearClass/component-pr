package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "a_types")
@NamedNativeQuery(name = "AType", query = "SELECT * FROM a_types WHERE id IN " +
"(SELECT DISTINCT type_id FROM a WHERE item = :item AND unit IN :units)", resultClass = AType.class)
public class AType extends AbstractType {
	private String type;
	private String value;
	private String spec;

	public AType(String type, String value, String spec) {
		this.type = type;
		this.value = value;
		this.spec = spec;
	}
	
	public AType(){
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null)? 0 : type.hashCode());
		result = prime * result + ((value == null)? 0 : value.hashCode());
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
		AType other = (AType) ob;
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
		if(type.equals("POWER_CABLE"))
			return "Кабель сетевой";
		if(type.equals("POWER_CONNECTOR"))
			return "Разъем питания";
		if(type.equals("POWER_SUPPLY"))
			return "Блок питания";
		return null;      
	}
	
	@Override
	public String getTypeNames() {
		return "";
	}

	@Override
	public String getSpec() {
		return spec!=null? spec : "";
	}

	@Override
	public String toString() {
		return value;
	}
}
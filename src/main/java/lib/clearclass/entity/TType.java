package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "t_types")
@NamedNativeQuery(name = "TType", query = "SELECT * FROM t_types WHERE id IN " +
"(SELECT DISTINCT type_id FROM t WHERE item = :item AND unit IN :units)", resultClass = TType.class)
public class TType extends AbstractType {
	private String type;
	private String spec;

	public TType(String type, String spec) {
		this.type = type;
		this.spec = spec;
	}
	
	public TType(){
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
		TType other = (TType) ob;
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
		return "Трансформатор" + (type.equals("ТИМ-238")? " импульсный" : "");
	}
	
	@Override
	public String getTypeNames() {
		return "Трансформаторы";
	}
	
	@Override
	public String getSpec() {
		return spec;
	}
}
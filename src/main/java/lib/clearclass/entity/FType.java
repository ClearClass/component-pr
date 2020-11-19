package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "f_types")
@NamedNativeQuery(name = "FType", query = "SELECT * FROM f_types WHERE id IN " +
"(SELECT DISTINCT type_id FROM f WHERE item = :item AND unit IN :units)", resultClass = FType.class)
public class FType extends AbstractType {
	private String type;
	private String spec;

	public FType(String type, String spec) {
		this.type = type;
		this.spec = spec;
	}
	
	public FType(){
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
		FType other = (FType) ob;
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
		return "Вставка плавкая";
	}
	
	@Override
	public String getTypeNames() {
		return "Вставки плавкие";
	}
	
	@Override
	public String getSpec() {
		return spec;
	}
}
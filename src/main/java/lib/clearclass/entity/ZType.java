package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "z_types")
public class ZType extends AbstractType {
	private String type;
	private String spec;

	public ZType(String type, String spec) {
		this.type = type;
		this.spec = spec;
	}
	
	public ZType(){
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
		ZType other = (ZType) ob;
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
		return "Фильтр";
	}
	
	@Override
	public String getTypeNames() {
		return "Фильтры";
	}
	
	@Override
	public String getSpec() {
		return spec!=null? spec : "";
	}
}
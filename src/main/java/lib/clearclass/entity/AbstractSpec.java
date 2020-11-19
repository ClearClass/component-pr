package lib.clearclass.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractSpec {
	@Id
	private String type;
	private String spec;
	
	public AbstractSpec(String type, String spec) {
		this.type = type;
		this.spec = spec;
	}

	public AbstractSpec(){
	}
	
	public String getType() {
		return type;
	}
	
	public String getSpec() {
		return spec;
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
		AbstractSpec other = (AbstractSpec) ob;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return type + " " + (spec==null? "" : spec);
	}
}
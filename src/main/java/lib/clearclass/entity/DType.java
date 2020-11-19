package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import lib.clearclass.logic.Mop;

@Entity
@Table(name = "d_types")
@NamedNativeQuery(name = "DType", query = "SELECT * FROM d_types WHERE id IN " +
"(SELECT DISTINCT type_id FROM d WHERE item = :item AND unit IN :units)", resultClass = DType.class)
public class DType extends AbstractType {
	private String type;
	private String spec;

	public DType(String type, String spec) {
		this.type = type;
		this.spec = spec;
	}
	
	public DType(){
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
		DType other = (DType) ob;
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
		return "Микросхема";
	}
	
	@Override
	public String getTypeNames() {
		return "Микросхемы";
	}
	
	@Override
	public String getSpec() {	
		if(spec==null){
			spec = Mop.getSpec(type);
			if(spec!=null)
				setExtSpec();
		}
		return spec;
	}
}











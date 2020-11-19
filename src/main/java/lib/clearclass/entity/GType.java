package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "g_types")
@NamedNativeQuery(name = "GType", query = "SELECT * FROM g_types WHERE id IN " +
"(SELECT DISTINCT type_id FROM g WHERE item = :item AND unit IN :units)", resultClass = GType.class)
public class GType extends AbstractType {
	private String type;
	private String freqs;
	private String spec;
	
	public GType() {
	}

	public GType(String type, String freqs, String spec) {
		this.type = type;
		this.freqs = freqs;
		this.spec = spec;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((freqs == null) ? 0 : freqs.hashCode());
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
		GType other = (GType) ob;
		if (freqs == null) {
			if (other.freqs != null)
				return false;
		} else if (!freqs.equals(other.freqs))
			return false;
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
		return "Генератор";
	}
	
	@Override
	public String getTypeNames() {
		return "Генераторы";
	}
	
	@Override
	public String getSpec() {
		return spec;
	}
	
	@Override
	public String toString() {// ГК108-П-15ГР-30М  
		 					  // KXO-V97 40 MHz (3.3V) Geyer
		String type = getType();
		
		if(type.equals("ГК108-П"))
			return type + "-15ГР-" + freqs;
		if(type.equals("KXO-V97"))
			return type + " " + freqs.replace("M", " M") + "Hz (3.3V)";
		return type + "-" + freqs;
	}
}
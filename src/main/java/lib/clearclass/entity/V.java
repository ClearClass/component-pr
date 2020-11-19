package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "V.forCL", query = "SELECT * FROM v " +
			"WHERE item = :item AND unit = :unit ORDER BY vtype, refdes;", resultClass = V.class),
	@NamedNativeQuery(name = "V.forSp", query = "SELECT item, unit, vtype, refdes, type_id " +
			"FROM v JOIN v_types ON type_id = id " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type;", resultClass = V.class),
	@NamedNativeQuery(name = "V.forCS", query = "SELECT item, unit, vtype, refdes, type_id " +
			"FROM v JOIN v_types ON type_id = id " +
			"WHERE item = :item " +
			"ORDER BY type, unit;", resultClass = V.class)})
public class V extends AbstractComponent<VType> implements CompositeRefDes {
	public enum Type {D, T}
	@Id
	@Enumerated(EnumType.STRING)
	private Type vtype;

	public V(String item, String unit, Type vtype, Integer refdes, VType type) {
		super(item, unit, refdes, type);
		this.vtype = vtype;
	}
	
	public V() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((vtype == null) ? 0 : vtype.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object ob) {
		if (this == ob)
			return true;
		if (!super.equals(ob))
			return false;
		if (getClass() != ob.getClass())
			return false;
		V other = (V) ob;
		if (vtype != other.vtype)
			return false;
		return true;
	}
	
//	@Override
//	public String toString() {
//		return getItem() + "." + getUnit() + ".V" + type + getRefdes()+ ": " + getType();
//	}

	@Override
	public String getRefDesName() {
		return "V" + vtype;
	}
	
	@Override
	public String toString() {
		return String.format("%-15s %-25s", 
				getItem() + "." + getUnit() + "." + getRefDesName() + getRefdes() + ": ", getType());		
	}
}
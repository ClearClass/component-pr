package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "H.forCL", query = "SELECT * FROM h " +
			"WHERE item = :item AND unit = :unit ORDER BY htype, refdes;", resultClass = H.class),
	@NamedNativeQuery(name = "H.forSp", query = "SELECT item, unit, htype, refdes, type_id " +
			"FROM h JOIN h_types ON type_id = id " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type;", resultClass = H.class),
	@NamedNativeQuery(name = "H.forCS", query = "SELECT item, unit, htype, refdes, type_id " +
			"FROM h JOIN h_types ON type_id = id " +
			"WHERE item = :item " +
			"ORDER BY type, unit;", resultClass = H.class)})
public class H extends AbstractComponent<HType> implements CompositeRefDes {
	public enum Type {L}
	@Id
	@Enumerated(EnumType.STRING)
	private Type htype;

	public H(String item, String unit, Type htype, Integer refdes, HType type) {
		super(item, unit, refdes, type);
		this.htype = htype;
	}
	
	public H() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((htype == null) ? 0 : htype.hashCode());
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
		H other = (H) ob;
		if (htype != other.htype)
			return false;
		return true;
	}
	
//	@Override
//	public String toString() {
//		return getItem() + "." + getUnit() + ".D" + type + getRefdes()+ ": " + getType();
//	}

	@Override
	public String getRefDesName() {
		return "H" + htype;
	}
	
	
	@Override
	public String toString() {
		return String.format("%-15s %-25s", 
				getItem() + "." + getUnit() + "." + getRefDesName() + getRefdes() + ": ", getType());		
	}
}
package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "D.forCL", query = "SELECT * FROM d " +
			"WHERE item = :item AND unit = :unit ORDER BY dtype, refdes;", resultClass = D.class),
	@NamedNativeQuery(name = "D.forSp", query = "SELECT item, unit, dtype, refdes, type_id " +
			"FROM d JOIN d_types ON type_id = id " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type;", resultClass = D.class),
	@NamedNativeQuery(name = "D.forCS", query = "SELECT item, unit, dtype, refdes, type_id " +
			"FROM d JOIN d_types ON type_id = id " +
			"WHERE item = :item " +
			"ORDER BY type, unit;", resultClass = D.class)})
public class D extends AbstractComponent<DType> implements CompositeRefDes {
	public enum Type {A, D}
	@Id
	@Enumerated(EnumType.STRING)
	private Type dtype;

	public D(String item, String unit, Type dtype, Integer refdes, DType type) {
		super(item, unit, refdes, type);
		this.dtype = dtype;
	}
	
	public D() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dtype == null) ? 0 : dtype.hashCode());
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
		D other = (D) ob;
		if (dtype != other.dtype)
			return false;
		return true;
	}
	
//	@Override
//	public String toString() {
//		return getItem() + "." + getUnit() + ".D" + type + getRefdes()+ ": " + getType();
//	}

	@Override
	public String getRefDesName() {
		return "D" + dtype;
	}
	
	
	@Override
	public String toString() {
		return String.format("%-15s %-25s", 
				getItem() + "." + getUnit() + "." + getRefDesName() + getRefdes() + ": ", getType());		
	}
}
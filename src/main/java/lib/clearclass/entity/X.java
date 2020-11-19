package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "X.forCL", query = "SELECT * FROM x " +
			"WHERE item = :item AND unit = :unit ORDER BY xtype, refdes;", resultClass = X.class),
	@NamedNativeQuery(name = "X.forSp", query = "SELECT item, unit, xtype, refdes, type_id " +
			"FROM x JOIN x_types ON type_id = id " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type;", resultClass = X.class),
	@NamedNativeQuery(name = "X.forCS", query = "SELECT item, unit, xtype, refdes, type_id " +
			"FROM x JOIN x_types ON type_id = id " +
			"WHERE item = :item " +
			"ORDER BY type, unit;", resultClass = X.class)})
public class X extends AbstractComponent<XType> implements CompositeRefDes {
	public enum Type {P, S}
	@Id
	@Enumerated(EnumType.STRING)
	private Type xtype;

	public X(String item, String unit, Type xtype, Integer refdes, XType type) {
		super(item, unit, refdes, type);
		this.xtype = xtype;
	}
	
	public X() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((xtype == null) ? 0 : xtype.hashCode());
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
		X other = (X) ob;
		if (xtype != other.xtype)
			return false;
		return true;
	}
	
//	@Override
//	public String toString() {
//		return getItem() + "." + getUnit() + ".X" + type + getRefdes()+ ": " + getType();
//	}

	@Override
	public String getRefDesName() {
		return "X" + xtype;
	}
	
	@Override
	public String toString() {
		return String.format("%-15s %-25s", 
				getItem() + "." + getUnit() + "." + getRefDesName() + getRefdes() + ": ", getType());		
	}
}
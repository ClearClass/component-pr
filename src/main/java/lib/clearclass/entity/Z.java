package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "Z.forCL", query = "SELECT * FROM z " +
			"WHERE item = :item AND unit = :unit ORDER BY refdes;", resultClass = Z.class),
	@NamedNativeQuery(name = "Z.forSp", query = "SELECT item, unit, refdes, type_id " +
			"FROM z JOIN z_types ON type_id = id " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type;", resultClass = Z.class),
	@NamedNativeQuery(name = "Z.forCS", query = "SELECT item, unit, refdes, type_id " +
			"FROM z JOIN z_types ON type_id = id " +
			"WHERE item = :item " +
			"ORDER BY type, unit;", resultClass = Z.class)})
public class Z extends AbstractComponent<ZType> {
	public Z(String item, String unit, Integer refdes, ZType type) {
		super(item, unit, refdes, type);
	}
	
	public Z() {
	}
}
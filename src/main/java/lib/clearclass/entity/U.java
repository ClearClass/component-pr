package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "U.forCL", query = "SELECT * FROM u " +
			"WHERE item = :item AND unit = :unit ORDER BY refdes;", resultClass = U.class),
	@NamedNativeQuery(name = "U.forSp", query = "SELECT item, unit, refdes, type_id " +
			"FROM u JOIN u_types ON type_id = id " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type;", resultClass = U.class),
	@NamedNativeQuery(name = "U.forCS", query = "SELECT item, unit, refdes, type_id " +
			"FROM u JOIN u_types ON type_id = id " +
			"WHERE item = :item " +
			"ORDER BY type, unit;", resultClass = U.class)})
public class U extends AbstractComponent<UType> {
	public U(String item, String unit, Integer refdes, UType type) {
		super(item, unit, refdes, type);
	}
	
	public U() {
	}
}
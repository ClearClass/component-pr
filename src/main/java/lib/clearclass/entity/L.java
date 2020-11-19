package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "L.forCL", query = "SELECT * FROM l " +
			"WHERE item = :item AND unit = :unit ORDER BY refdes;", resultClass = L.class),
	@NamedNativeQuery(name = "L.forSp", query = "SELECT item, unit, refdes, type_id " +
			"FROM l JOIN l_types ON type_id = id NATURAL JOIN dim_values " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type, value*e, toler;", resultClass = L.class),
	@NamedNativeQuery(name = "L.forCS", query = "SELECT item, unit, refdes, type_id " +
			"FROM l JOIN l_types ON type_id = id NATURAL JOIN dim_values " +
			"WHERE item = :item " +
			"ORDER BY type, value*e, toler, unit;", resultClass = L.class)})
public class L extends AbstractComponent<LType> {
	public L(String item, String unit, Integer refdes, LType type) {
		super(item, unit, refdes, type);
	}
	
	public L() {
	}
}
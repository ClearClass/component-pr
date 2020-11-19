package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "R.forCL", query = "SELECT * FROM r " +
			"WHERE item = :item AND unit = :unit ORDER BY refdes;", resultClass = R.class),
	@NamedNativeQuery(name = "R.forSp", query = "SELECT item, unit, refdes, type_id " +
			"FROM r JOIN r_types ON type_id = id NATURAL JOIN dim_values " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type, power, value*e, toler;", resultClass = R.class),
	@NamedNativeQuery(name = "R.forCS", query = "SELECT item, unit, refdes, type_id " +
			"FROM r JOIN r_types ON type_id = id NATURAL JOIN dim_values " +
			"WHERE item = :item " +
			"ORDER BY type, power, value*e, toler, unit;", resultClass = R.class)})
public class R extends AbstractComponent<RType> {
	public R(String item, String unit, Integer refdes, RType type) {
		super(item, unit, refdes, type);
	}
	
	public R() {
	}
}
package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "C.forCL", query = "SELECT * FROM c " +
			"WHERE item = :item AND unit = :unit ORDER BY refdes;", resultClass = C.class),
	@NamedNativeQuery(name = "C.forSp", query = "SELECT item, unit, refdes, type_id " +
			"FROM c JOIN c_types ON type_id = id NATURAL JOIN dim_values " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type, diel, volt, size, value*e, toler;", resultClass = C.class),
	@NamedNativeQuery(name = "C.forCS", query = "SELECT item, unit, refdes, type_id " +
			"FROM c JOIN c_types ON type_id = id NATURAL JOIN dim_values " +
			"WHERE item = :item " +
			"ORDER BY type, diel, volt, size, value*e, toler, unit;", resultClass = C.class)})
public class C extends AbstractComponent<CType> {
	public C(String item, String unit, Integer refdes, CType type) {
		super(item, unit, refdes, type);
	}
	
	public C() {
	}
}
package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "T.forCL", query = "SELECT * FROM t " +
			"WHERE item = :item AND unit = :unit ORDER BY refdes;", resultClass = T.class),
	@NamedNativeQuery(name = "T.forSp", query = "SELECT item, unit, refdes, type_id " +
			"FROM t JOIN t_types ON type_id = id " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type;", resultClass = T.class),
	@NamedNativeQuery(name = "T.forCS", query = "SELECT item, unit, refdes, type_id " +
			"FROM t JOIN t_types ON type_id = id " +
			"WHERE item = :item " +
			"ORDER BY type, unit;", resultClass = T.class)})
public class T extends AbstractComponent<TType> {
	public T(String item, String unit, Integer refdes, TType type) {
		super(item, unit, refdes, type);
	}
	
	public T() {
	}
}
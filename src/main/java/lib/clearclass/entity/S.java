package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "S.forCL", query = "SELECT * FROM s " +
			"WHERE item = :item AND unit = :unit ORDER BY refdes;", resultClass = S.class),
	@NamedNativeQuery(name = "S.forSp", query = "SELECT item, unit, refdes, type_id " +
			"FROM s JOIN s_types ON type_id = id " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type;", resultClass = S.class),
	@NamedNativeQuery(name = "S.forCS", query = "SELECT item, unit, refdes, type_id " +
			"FROM s JOIN s_types ON type_id = id " +
			"WHERE item = :item " +
			"ORDER BY type, unit;", resultClass = S.class)})
public class S extends AbstractComponent<SType> {
	public S(String item, String unit, Integer refdes, SType type) {
		super(item, unit, refdes, type);
	}
	
	public S() {
	}
}
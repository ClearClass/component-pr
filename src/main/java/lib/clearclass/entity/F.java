package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "F.forCL", query = "SELECT * FROM f " +
			"WHERE item = :item AND unit = :unit ORDER BY refdes;", resultClass = F.class),
	@NamedNativeQuery(name = "F.forSp", query = "SELECT item, unit, refdes, type_id " +
			"FROM f JOIN f_types ON type_id = id " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type, curr;", resultClass = F.class),
	@NamedNativeQuery(name = "F.forCS", query = "SELECT item, unit, refdes, type_id " +
			"FROM f JOIN f_types ON type_id = id " +
			"WHERE item = :item " +
			"ORDER BY type, curr, unit;", resultClass = F.class)})
public class F extends AbstractComponent<FType> {
	public F(String item, String unit, Integer refdes, FType type) {
		super(item, unit, refdes, type);
	}
	
	public F() {
	}
}
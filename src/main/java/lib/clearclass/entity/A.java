package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "A.forCL", query = "SELECT * FROM a " +
			"WHERE item = :item AND unit = :unit ORDER BY refdes;", resultClass = A.class),
	@NamedNativeQuery(name = "A.forSp", query = "SELECT item, unit, refdes, type_id " +
			"FROM a JOIN a_types ON type_id = id " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type;", resultClass = A.class),
	@NamedNativeQuery(name = "A.forCS", query = "SELECT item, unit, refdes, type_id " +
			"FROM a JOIN a_types ON type_id = id " +
			"WHERE item = :item " +
			"ORDER BY type, unit;", resultClass = A.class)})
public class A extends AbstractComponent<AType> {
	public A(String item, String unit, Integer refdes, AType type) {
		super(item, unit, refdes, type);
	}
	
	public A() {
	}
}
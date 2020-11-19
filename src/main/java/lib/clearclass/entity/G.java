package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name = "G.forCL", query = "SELECT * FROM g " +
			"WHERE item = :item AND unit = :unit ORDER BY refdes;", resultClass = G.class),
	@NamedNativeQuery(name = "G.forSp", query = "SELECT item, unit, refdes, type_id " +
			"FROM g JOIN g_types ON type_id = id " +
			"WHERE item = :item AND unit = :unit " +
			"ORDER BY type, freqs;", resultClass = G.class),
	@NamedNativeQuery(name = "G.forCS", query = "SELECT item, unit, refdes, type_id " +
			"FROM g JOIN g_types ON type_id = id " +
			"WHERE item = :item " +
			"ORDER BY type, freqs, unit;", resultClass = G.class)})
public class G extends AbstractComponent<GType> {
	public G(String item, String unit, Integer refdes, GType type) {
		super(item, unit, refdes, type);
	}
	
	public G() {
	}
}
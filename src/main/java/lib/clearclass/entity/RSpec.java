package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "r_spec")
@NamedNativeQuery(name = "RSpec", query = "SELECT DISTINCT r_types.type, spec "
		+ "FROM r JOIN r_types ON type_id = id NATURAL JOIN r_spec "
		+ "WHERE item = :item AND unit = :unit ORDER BY type;", resultClass = RSpec.class)
public class RSpec extends AbstractSpec {
	public RSpec(String type, String spec) {
		super(type, spec);
	}
	public RSpec(){
	}
	
	@Override
	public String toString() {
		if(getType().equals("CR"))
			return "Резисторы " + getSpec();
		return "Резисторы " + super.toString();
	}
}
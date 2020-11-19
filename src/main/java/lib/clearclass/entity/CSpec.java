package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "c_spec")
@NamedNativeQuery(name = "CSpec", query = "SELECT DISTINCT c_types.type, spec "
		+ "FROM c JOIN c_types ON type_id = id NATURAL JOIN c_spec "
		+ "WHERE item = :item AND unit = :unit ORDER BY type;", resultClass = CSpec.class)
public class CSpec extends AbstractSpec {
	public CSpec(String type, String spec) {
		super(type, spec);
	}

	public CSpec() {
	}

	@Override
	public String toString() {// тип + спецификация
		if(getType().equals("CC"))
			return "Конденсаторы керамические " + CType.getManufact();
		if(getType().equals("CC_TAJ"))
			return "Конденсаторы танталовые T491 Kemet";
		if(getType().equals("B41851"))
			return "Конденсаторы электролитические " + super.toString();
		return "Конденсаторы " + super.toString();
	}
}
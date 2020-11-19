package lib.clearclass.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Transient
	private boolean extName = false;
	@Transient
	private boolean extSpec = false;

	/** тип типономинала, например Р1-12 */
	public abstract String getType();
	
	/** Конденсатор луженый */
	public abstract String getTypeName();
	
	/** Конденсаторы луженые */
	public abstract String getTypeNames();
	
	/** Конденсатор */
	public String getGenTypeName(){
		return getTypeName();
	}
	
	/** Конденсаторы */
	public String getGenTypeNames(){
		return getTypeNames();
	}
	
	public abstract String getSpec();
	
	@Override
	public String toString() {
		return getType();
	}
	
	// расшифровка типономинала (для импортных элементов)
	public String toNote() {
		return "";
	}
	
	public boolean isExtName() {
		return extName;
	}

	public boolean isExtSpec() {
		return extSpec;
	}
	
	public void setExtSpec() {
		extSpec = true;
	}
}
package lib.clearclass.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractComponent<E extends AbstractType> implements Serializable {
	@Id
	private String item;
	@Id
	private String unit;
	@Id
	private Integer refdes;
	
	@ManyToOne
	@JoinColumn(name = "type_id")
	private E type;
	
	public AbstractComponent(String item, String unit, Integer refdes, E type) {
		this.item = item;
		this.unit = unit;
		this.refdes = refdes;
		this.type = type;
	}

	public AbstractComponent() {
	}
	
	public String getItem() {
		return item;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public Integer getRefdes() {
		return refdes;
	}

	public E getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((refdes == null) ? 0 : refdes.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object ob) {
		if (this == ob)
			return true;
		if (ob == null)
			return false;
		if (getClass() != ob.getClass())
			return false;
		AbstractComponent<?> other = (AbstractComponent<?>) ob;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (refdes == null) {
			if (other.refdes != null)
				return false;
		} else if (!refdes.equals(other.refdes))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%-15s %-25s", 
				item + "." + unit + "." + getClass().getSimpleName() + refdes + ": ", 
				type.toString());		
	}
}
package lib.clearclass.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "x_types")
public class XType extends AbstractType {
	public enum Type {P, S, K, G}
	
	private String type;
	@Enumerated(EnumType.STRING)
	private Type xtype;
	private String spec;

	public XType(String type, Type xtype, String spec) {
		this.type = type;
		this.xtype = xtype;
		this.spec = spec;
	}
	
	public XType(){
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((xtype == null) ? 0 : xtype.hashCode());
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
		XType other = (XType) ob;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (xtype != other.xtype)
			return false;
		return true;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getTypeName() {
		if(xtype==Type.K)
			return "Клемма приборная";
		if(xtype==Type.G)
			return "Гнездо приборное";
		if(type.startsWith("БЯ"))
			return "Колодка";
		if(xtype==Type.P)
			return "Вилка";
		else
			return "Розетка";
	}
	
	@Override
	public String getTypeNames() {
		return "Соединители";
	}

	@Override
	public String getSpec() {
		if(type.equals("BH-10"))
			return "";
		if(type.equals("ГИ4.0"))
			return "";
		if(type.equals("КП-1Б"))
			return "";
		if(type.startsWith("DRB-"))
			return "";
		 
		return spec;
	}
}
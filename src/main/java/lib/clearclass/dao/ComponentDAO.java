package lib.clearclass.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib.clearclass.entity.AbstractComponent;
import lib.clearclass.entity.AbstractType;
import lib.clearclass.model.Item;

public interface ComponentDAO {
	void save(Set<?> set);
	void save(String item, String unit, String title, boolean iscontr);
	void save(String item, Date date);
	
	
	
	<E extends AbstractComponent<?>> List<E> getComponentsForCL(String item, String unit, Class<E> type);
	<E extends AbstractComponent<?>> List<E> getComponentsForSp(String item, String unit, Class<E> type);
	<E extends AbstractComponent<?>> List<E> getComponentsForCS(String item, Class<E> type);
	<E extends AbstractType> List<E> getTypes(String item, String unit, Class<E> type);
	<E extends AbstractType> Map<E, Integer> getTypes(String item, List<String> units, Class<E> type);
	
	
	
	void delete();
	
	
	



	
	
	List<Item> getItems();
	
	
	
	
	String getTitle(String item, String unit);
	
	
	
	
	
	
	
}
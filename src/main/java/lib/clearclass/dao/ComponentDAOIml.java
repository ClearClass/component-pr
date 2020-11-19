package lib.clearclass.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lib.clearclass.entity.AbstractComponent;
import lib.clearclass.entity.AbstractType;
import lib.clearclass.model.Item;

@Repository
public class ComponentDAOIml implements ComponentDAO {
	@Autowired
	EntityManager em;

	@Override
	public void save(Set<?> set) {
		for(Object ob : set)
			try {
				em.persist(ob);
			} catch (Exception e) {
				throw new RuntimeException(ob + " " + e.getCause().getCause());
			}
	}

	@Override
	@Transactional
	public void save(String item, String unit, String title, boolean iscontr) {
		em.createNativeQuery("INSERT INTO units VALUES (:item, :unit, :title, :iscontr)")
		.setParameter("item", item)
		.setParameter("unit", unit)
		.setParameter("title", title)
		.setParameter("iscontr", iscontr)
		.executeUpdate();
	}
	
	
	@Override
	public void save(String item, Date date) {
		em.createNativeQuery("INSERT INTO items VALUES (:item, :date)")
		.setParameter("item", item)
		.setParameter("date", date)
		.executeUpdate();
	}
	
	
	
	
	
	
	

	@Override
	public <E extends AbstractComponent<?>> List<E> getComponentsForCL(String item, String unit, Class<E> type) {
		TypedQuery<E> query = em.createNamedQuery(type.getSimpleName() + ".forCL", type);
		query.setParameter("item", item);
		query.setParameter("unit", unit);
		return query.getResultList();
	}
	
	@Override
	public <E extends AbstractComponent<?>> List<E> getComponentsForSp(String item, String unit, Class<E> type) {
		TypedQuery<E> query = em.createNamedQuery(type.getSimpleName() + ".forSp", type);
		query.setParameter("item", item);
		query.setParameter("unit", unit);
		return query.getResultList();
	}

	@Override
	public <E extends AbstractComponent<?>> List<E> getComponentsForCS(String item, Class<E> type) {
		TypedQuery<E> query = em.createNamedQuery(type.getSimpleName() + ".forCS", type);
		query.setParameter("item", item);
		return query.getResultList();
	}

	@Override
	@Transactional(readOnly=true)
	@SuppressWarnings("unchecked")
	public List<Item> getItems() {
		List<Item> items = new ArrayList<>();
		List<String> itemNames = em.createNativeQuery("SELECT item FROM items ORDER BY date").getResultList();
		Query query = em.createNativeQuery("SELECT unit FROM units WHERE item=:item AND iscontr=:iscontr");
		for (String itemName : itemNames){
			query.setParameter("item", itemName);
			List<String> unitNames = query.setParameter("iscontr", false).getResultList();
			List<String> ctrlNames = query.setParameter("iscontr", true).getResultList();;
			items.add(new Item(itemName, unitNames, ctrlNames));
		}
		return items;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public String getTitle(String item, String unit) {
		return (String) em.createNativeQuery("SELECT title FROM units WHERE item = :item AND unit = :unit")
				.setParameter("item", item).setParameter("unit", unit).getSingleResult();
	}

	@Override
	public <E extends AbstractType> List<E> getTypes(String item, String unit, Class<E> type) {
		List<String> units = new ArrayList<>();
		units.add(unit);
//		return getTypes(item, units, type);
		return null;
	}

	@Override
	public <E extends AbstractType> Map<E, Integer> getTypes(String item, List<String> units, Class<E> type) {
		Map<E, Integer> map = new LinkedHashMap<>();
		
		TypedQuery<E> query = em.createNamedQuery(type.getSimpleName(), type);
		query.setParameter("item", item);
		query.setParameter("units", units);
		
		em.createNativeQuery("").getResultList();
		
		
//		return query.getResultList();
		return map;
	}

	@Override
	public void delete() {
		em.createNativeQuery("DELETE FROM items").executeUpdate();
	}




	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
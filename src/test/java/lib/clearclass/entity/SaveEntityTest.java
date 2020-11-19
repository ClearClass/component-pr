package lib.clearclass.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lib.clearclass.dao.ComponentDAO;
import lib.clearclass.logic.Items;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaveEntityTest {
	@Autowired
	Items items;
	@Autowired
	EntityManager em;

	DType dtype = new DType("1432УД18qР", "АЕЯР.431100.280-07 ТУ");
	@Test
	@Transactional
	public void test(){

//		items.persist();
		
//		List<String> list = em.createNativeQuery("SELECT dim FROM dim_values").getResultList();
//		
//		for(String st : list)
//		System.out.println(st);
//		
//		
//		em.createNativeQuery("INSERT INTO units VALUES (:item, :unit, :decimal)")
//		.setParameter("item", "364")
//		.setParameter("unit", "81")
//		.setParameter("decimal", "--")
//		.executeUpdate();
//		
//		
//		em.persist(dtype);
//		
//		D d = new D("364", "8", D.Type.A, 1, dtype);
//		
//		em.persist(d);
//		


		CSpec cSpec = new CSpec("К10-69в", "АЖЯР.673511.002 ТУ");
		CType ctype1 = new CType(cSpec, "Н90", 25, "2012М", 0.1, "мк", 8020, true);
		CType ctype2 = new CType(cSpec, "Н90", 25, "2012М", 0.25, "мк", 8020, true);
//		em.persist(cSpec);
//		em.persist(ctype1);
//		em.persist(ctype2);







	}







}
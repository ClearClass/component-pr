package lib.clearclass.dao;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lib.clearclass.dao.ComponentDAO;
import lib.clearclass.entity.AbstractComponent;
import lib.clearclass.entity.AbstractSpec;
import lib.clearclass.entity.AbstractType;
import lib.clearclass.entity.CSpec;
import lib.clearclass.entity.CType;
import lib.clearclass.entity.D;
import lib.clearclass.entity.DType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {
	@Autowired
	ComponentDAO dao;
	
	
	@Test
	@Transactional
	public void deleteTest(){
		dao.save("PR", new Date(2012, 2, 21));
		dao.delete();
		dao.save("PR", new Date(2012, 2, 21));
		
	}
	
	
	
//	@Test
	@Transactional
	public void test(){
		Set<AbstractSpec> specs = new LinkedHashSet<>();
		specs.add(new CSpec("к10-69", "ту"));
		dao.save(specs);
		
		
		Set<AbstractType> types = new LinkedHashSet<>();
		DType dType = new DType("1303ЕН5П2", null);
		types.add(dType);
		
		Set<AbstractComponent<? extends AbstractType>> cmpset = new LinkedHashSet<>();
		cmpset.add(new D("19", "37", D.Type.A, 12, dType));
		
		// persist ...
		
//		dao.save("19", "37", "xxx", false);
//		dao.save(types);
//		dao.save(cmpset);
		
	
		
		
		System.out.println(dao.getTypes("364", "Прибор 7", CType.class));
		
		
		
 
	}
}
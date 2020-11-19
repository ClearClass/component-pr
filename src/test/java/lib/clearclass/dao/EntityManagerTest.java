package lib.clearclass.dao;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityManagerTest {
	@Autowired
	EntityManager em;
	
	@Test
	@Transactional
	public void test(){
		
		
		



	}

}

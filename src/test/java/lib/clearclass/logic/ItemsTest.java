package lib.clearclass.logic;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lib.clearclass.logic.parser.Parser;

class A {
	private int a;

	A(int a) {
		this.a = a;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + a;
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
		A other = (A) ob;
		if (a != other.a)
			return false;
		return true;
	}
}



@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemsTest {
	@Autowired
	Items items;
	
	
	@Test
	public void test(){

		items.delete();
		items.load(CadFileType.SCH);
		items.persist();
		
		items.delete();
		items.load(CadFileType.SCH);
		items.persist();
//		File unitsDir = new File("/Items/" + "364" + "/Units");
//		System.out.println(unitsDir.getPath());
		

		
		
		

	}
	
	@Test
	public void addGetTest() {
		A a1 = new A(1000);
		A a2 = new A(1000);
		assertTrue(a1.equals(a2));
		assertTrue(a1 != a2);
		Set<A> set = new HashSet<>();
		assertTrue(a1 == Parser.addGet(a1, set));
		assertTrue(a1 == Parser.addGet(a2, set));
		assertTrue(set.size() == 1);
	}
}

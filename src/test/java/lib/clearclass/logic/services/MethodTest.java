package lib.clearclass.logic.services;

import java.util.List;

import org.junit.Test;



public class MethodTest {
	@Test
	public void splitTest() {
		String st1 = "К53-68 \"B\"-10 В-4,7 мкФ ± 20 %";
		String st2 = "К53-68 \"D\"-10 В-100 мкФ ± 20 %";
		String st3 = "К53-68 \"B\"-20 В-2,2 мкФ ± 20 %";
		String st4 = "К53-68 \"B\"-20 В-4,7 мкФ ± 20 %";
		String st5 = "К53-68 \"E\"-50 В-6,8 мкФ ± 20 %";
		int lenght = 30;
		List<String> list = Split.doo(st1, lenght);
		System.out.println(list);
	}
}
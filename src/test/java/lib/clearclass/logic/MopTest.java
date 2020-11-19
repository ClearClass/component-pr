package lib.clearclass.logic;

import org.junit.Test;

import lib.clearclass.logic.Mop;

public class MopTest {
	@Test
	public void test() {
		System.out.println(Mop.getSpec("К10-47Ма"));
		System.out.println(Mop.getSpec("К53-68"));
		System.out.println(Mop.getSpec("Р1-12"));
		System.out.println(Mop.getSpec("5559ИН28У"));
		 
	}
}
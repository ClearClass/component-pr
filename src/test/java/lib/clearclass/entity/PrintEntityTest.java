package lib.clearclass.entity;

import org.junit.Test;

public class PrintEntityTest {
	CSpec cSpec = new CSpec("К10-69в", "АЖЯР.673511.002 ТУ");
	CType ctype = new CType(cSpec, "Н90", 25, "2012М", 0.1, "мк", 8020, true);
	DType dtype = new DType("1432УД18Р", "АЕЯР.431100.280-07 ТУ");
//	@Test
	public void printTypeTest(){
		System.out.println("=======================================C-types");
		System.out.println(cSpec);
		System.out.println(ctype);
		CSpec cSpec = new CSpec("К53-68", null);
		System.out.println(new CType(cSpec, "ЭЛТ", 10, "В", 10d, "мк", 10, false));
		System.out.println(new CType(cSpec, "ЭЛТ", 10, "В", 10.1, "мк", 10, false));
		cSpec = new CSpec("К10-47Ма", null);
		System.out.println(new CType(cSpec, "Н90", 50, null, 4.7, "мк", null, false));
		
		System.out.println("\n=======================================D-types");
		System.out.println(new DType("1432УД18Р", "АЕЯР.431100.280-07 ТУ"));
		
		System.out.println("\n=======================================G-types");
		System.out.println(new GType("ГК108-П", "24М", "АФТП 433520.007 ТУ"));
		
		System.out.println("\n=======================================L-types");
		System.out.println(new LType("ДФ15-2P/1,5", "БКЮС.670109.002 ТУ"));
		System.out.println(new LType("ДМ-2,4", 4d, "мк", 10, "ЦКСН.671342.001 ТУ"));
		
		System.out.println("\n=======================================R-types");
		RSpec rSpec = new RSpec("Р1-12", "ШКАБ.434110.002 ТУ");
		System.out.println(rSpec);
		System.out.println(new RType(rSpec, 0.125, "0805", 3.65, "к", 1));
		System.out.println(new RType(rSpec, 0.125, "0805", 3d, "к", 5));
		rSpec = new RSpec("СП5-2ВБ", "ШКАБ.434110.002 ТУ");
		System.out.println(new RType(rSpec, 0.5, null, 10d, "к", 10));
		
		System.out.println("\n=======================================T-types");
		System.out.println(new TType("ТИЛ6В", "АГ0.472.105 ТУ"));
		System.out.println(new TType("ТИМ-238", "ОЮ0.472.045 ТУ"));
		
		System.out.println("\n=======================================V-types");
		System.out.println(new VType("2Д707АС9", "аА0.339.583 ТУ"));
		
		System.out.println("\n=======================================X-types");
		System.out.println(new VType("РСГ4ТВ", "АВ0.364.047 ТУ"));
		System.out.println(new VType("СНП347-10ВП22-В", "РЮМК.430420.012 ТУ"));
		System.out.println(new VType("БЯ6.629.051-02", null));
	}
	
	@Test
	public void printComponentTest(){
//		System.out.println("=======================================C-components");
//		System.out.println(new C("1М111", "16", 1, ctype));
//		System.out.println(new D("1М111", "16", D.Type.D, 1, dtype));
//		System.out.println(new D("1М111", "16", D.Type.A, 1, dtype));
	}
	
	@Test
	public void printCmponentTest(){
		System.out.println("=======================================C-components");
				
		RSpec rSpec = new RSpec("CR", "Bourns");
		System.out.println(new RType(rSpec, "0805", 1d, "к", 2).toNote());
		
		
		CSpec cSpec = new CSpec("CC", null);
		CType.setManufact("Kemet");
		System.out.println(cSpec);
		
		
		System.out.println(new CType(cSpec, "X7R", 25, "0603", 0.1, "мк", 10, false).toNote());
		System.out.println(new CType(cSpec, "X7R", 25, "0805", 2700d, "п", 2, false).toNote());
		
		System.out.println(new CType(cSpec, "X7R", 25, "0603", 0.1, "мк", 10, false).toString());
		System.out.println(new CType(cSpec, "X7R", 25, "0805", 2700d, "п", 2, false).toString());
		
		 		
		cSpec = new CSpec("CC_TAJ", "Kemet");
		System.out.println(new CType(cSpec, "ЭЛТ", 35, "D", 22d, "мк", 20, false).toNote());
		System.out.println(new CType(cSpec, "ЭЛТ", 20, "B", 4.7, "мк", 20, false).toNote());
		
		
//		cSpec = new CSpec("B41851", "Epcos");
//		System.out.println(new CType(cSpec, "ЭЛТ", 10, "10x20", 1500d, "мк", 20, false));
//		System.out.println(new CType(cSpec, "ЭЛТ", 25, "10x20", 1000d, "мк", 20, false));
//		System.out.println(new CType(cSpec, "ЭЛТ", 16, "10x20", 1500d, "мк", 20, false));
//		System.out.println(new CType(cSpec, "ЭЛТ", 100, "10x20", 100d, "мк", 20, false));
	}
}





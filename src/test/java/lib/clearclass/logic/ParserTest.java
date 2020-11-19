package lib.clearclass.logic;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import lib.clearclass.entity.*;
import lib.clearclass.logic.parser.AccelComponentReader;
import lib.clearclass.logic.parser.CompInst;
import lib.clearclass.logic.parser.ComponentReader;
import lib.clearclass.logic.parser.Parser;


public class ParserTest {
	Set<AbstractSpec> specs = new HashSet<>();
	Set<AbstractType> types = new HashSet<>();
	Parser parser = new Parser(specs, types);
	
	CompInst compInst;
	CompInst compInst1;
	CompInst compInst2;
	AbstractComponent<? extends AbstractType> cmp;
	AbstractComponent<? extends AbstractType> cmp1;
	AbstractComponent<? extends AbstractType> cmp2;
	
	@Test
	public void svk(){
//		compInst1 = CompInst.getInst("C1", "CC0603", "0.1u_25V_10_X7R");
//		cmp1 = parser.get(compInst1);
//		System.out.println(cmp1);
//		
//		compInst2 = CompInst.getInst("C46", "CC0603", "0.1u_25V_10_X7R");
//		cmp2 = parser.get(compInst2);
//		System.out.println(cmp2);
//		
//		System.out.println(cmp1.getType()==cmp2.getType());
		
		
		ComponentReader reader = new AccelComponentReader("Items/364/Units/PR7_2018_11T_N.asc");
		parser.setItem("364");
		parser.setUnit("7");
		
//		while(reader.hasNext())
//			System.out.println(reader.next());
		
		System.out.println(reader.getDecml() + " " + reader.getTitle());
		System.out.println();
		
		while(reader.hasNext())
			System.out.println(parser.get(reader.next()));
		
		
	}
	
	
	
	
//	@Test
	public void test7(){ // прибор 7
		// ==================================================== КОНДЕНСАТОРЫ
		compInst = CompInst.getInstWithSpec(
				"C20", "K10-47MA-50B-4.7", "4.7u_50v_N90", "ОЖО.460.174-МТУ");
		cmp = parser.get(compInst);
		
		assertTrue(cmp instanceof C);
		assertEquals(20, (int)cmp.getRefdes());
		assertEquals(new CType(new CSpec("К10-47Ма", null), "Н90", 50, null, 4.7, "мк", null, false), cmp.getType());
		assertEquals("ОЖО.460.174-МТУ", cmp.getType().getSpec());
		
		
		
		
		
		// ==================================================== МИКРОСХЕМЫ
		
		
	
		compInst = CompInst.getInstWithSpec(
				"DA1", "1303ЕН5_0П", "1303EN5P", "АЕ_Р.431420.638 ТУ");
		
		
		cmp = parser.get(compInst);
		assertTrue(((CompositeRefDes)cmp).getRefDesName().equals("DA"));
		assertEquals(1, (int)cmp.getRefdes());
		
		assertEquals(new DType("1303ЕН5П", null), cmp.getType());
		assertEquals("АЕЯР.431420.638 ТУ", cmp.getType().getSpec());
		
		compInst = CompInst.getInstWithSpec(
				"DA4", "142ЕР2У_(DA)", "142ER2U", "");
		
		
		
		cmp = parser.get(compInst);
		
		
		
		
		
		
		
		
		assertEquals(4, (int)cmp.getRefdes());
		
		assertEquals(new DType("142ЕР2У", null), cmp.getType());
		assertNull(cmp.getType().getSpec());
//		// ==================================================== ГЕНЕРАТОРЫ
//		// ==================================================== ИНДУКТИВНОСТИ
//		st  = "L1                   ДФ15-2P/1_5          DF15-2R/1_5     H=9                  БКЮС.670109.002 ТУ                       ";
//		cmp = parser.get(compInst);
//		assertEquals(1, (int)cmp.getRefdes());
//		assertEquals(new LType("ДФ15-2P/1,5", null), type);
//		assertEquals("БКЮС.670109.002 ТУ", cmp.getType().getSpecification());
//		// ==================================================== РЕЗИСТОРЫ
//		st  = "R48                  R1-12-1              200_5           H=0.8                                                         ";
//		cmp = parser.get(compInst);
//		assertEquals(48, (int)cmp.getRefdes());
//		assertNull(cmp.getType().getSpecification());
//		assertEquals(new RType(new RSpec("Р1-12", null), 1d, null, 200d, "", 5), type);
//		// ==================================================== ТРАНСФОРМАТОРЫ
//		st  = "T1                   TIM238               TIM238          H=10.4                                                        ";
//		cmp = parser.get(compInst);
//		assertEquals(1, (int)cmp.getRefdes());
//		assertEquals(new TType("ТИМ-238", null), type);
//		assertNull(cmp.getType().getSpecification());
//		// ==================================================== МОДУЛИ ПИТАНИЯ
//		st  = "U1                   МДМ7.5-1В12МП        MDM7.5-1V12MP   H=10mm                                                        ";
//		cmp = parser.get(compInst);
//		assertEquals(1, (int)cmp.getRefdes());
//		assertEquals(new UType("МДМ7,5-1В12МП", null), type);
//		assertNull(cmp.getType().getSpecification());
//		st  = "U3                   МДМ15-1В12МП         MDM15-1V12MP    H=10mm                                                        ";
//		cmp = parser.get(compInst);
//		assertEquals(3, (int)cmp.getRefdes());
//		assertEquals(new UType("МДМ15-1В12МП", null), type);
//		assertNull(cmp.getType().getSpecification());
//		// ==================================================== СОЕДИНИТЕЛИ
//		st  = "XS1                  KOLODKA_5            KOLODKA_5       H=11.5mm                                                      ";
//		cmp = parser.get(compInst);
//		assertEquals(1, (int)cmp.getRefdes());

//		assertEquals(new XType("БЯ6.629.059-01", null), type);
//		assertNull(cmp.getType().getSpecification());
	}
//	@Test
	public void test8(){ // прибор 8
		
		
		
		// ==================================================== КОНДЕНСАТОРЫ
	
		
		
		compInst = CompInst.getInstWithSpec(
				"C5", "K53-68_(B)", "10u_10v_20", "АЖ_Р.673546.007 ТУ");
		
		cmp = parser.get(compInst);
		
		

		assertEquals(5, (int)cmp.getRefdes());
		assertEquals("АЖЯР.673546.007 ТУ", cmp.getType().getSpec());
		assertEquals(new CType(new CSpec("К53-68", null), "ЭЛТ", 10, "B", 10d, "мк", 20, false), cmp.getType());
		// ==================================================== МИКРОСХЕМЫ
		// ==================================================== ГЕНЕРАТОРЫ
		// ==================================================== ИНДУКТИВНОСТИ
		
		
		compInst = CompInst.getInstWithSpec(
				"L1", "ДМ-0,6-12", "12u_5", "ЦКСН.671342.001 ТУ");
		
		cmp = parser.get(compInst);
		
		

		assertEquals(1, (int)cmp.getRefdes());
		assertEquals(new LType("ДМ-0,6", 12d, "мк", 5, null), cmp.getType());
		assertEquals("ЦКСН.671342.001 ТУ", cmp.getType().getSpec());
		// ==================================================== РЕЗИСТОРЫ
		
		
		
		compInst = CompInst.getInstWithSpec(
				"R114", "R1-12-0_125_(0805)", "1K_5", "");
		
		cmp = parser.get(compInst);
		
		
		

		
		assertEquals(114, (int)cmp.getRefdes());
	
		assertNull(cmp.getType().getSpec());
		assertEquals(new RType(new RSpec("Р1-12", null), 0.125, "0805", 1d, "к", 5), cmp.getType());
		
		
		
		compInst = CompInst.getInstWithSpec(
				"R118", "СП5-2ВБ", "10k_10", "ОЖО.468.539ТУ");
		cmp = parser.get(compInst);
		
		
		

		assertEquals(118, (int)cmp.getRefdes());
		assertEquals("ОЖО.468.539ТУ", cmp.getType().getSpec());
		assertEquals(new RType(new RSpec("СП5-2ВБ", null), 0.5, null, 10d, "к", 10), cmp.getType());
		
		
		compInst = CompInst.getInstWithSpec(
				"R119", "R1-12-0_125_(0805)", "20k_5", "");
		cmp = parser.get(compInst);
		
		
		

		assertEquals(119, (int)cmp.getRefdes());
		assertNull(cmp.getType().getSpec());
		assertEquals(new RType(new RSpec("Р1-12", null), 0.125, "0805", 20d, "к", 5), cmp.getType());
		
		
		compInst = CompInst.getInstWithSpec(
				"R216", "R1-12-0_125_(0805)", "51_5", "");
		cmp = parser.get(compInst);
		
		
		

		assertEquals(216, (int)cmp.getRefdes());
		assertNull(cmp.getType().getSpec());
		assertEquals(new RType(new RSpec("Р1-12", null), 0.125, "0805", 51d, "", 5), cmp.getType());
		
		compInst = CompInst.getInstWithSpec(
				"R218", "R1-12-0_125_(0805)", "1.6k_5", "");
		cmp = parser.get(compInst);
		
		
		
		

		assertEquals(218, (int)cmp.getRefdes());
		assertNull(cmp.getType().getSpec());
		assertEquals(new RType(new RSpec("Р1-12", null), 0.125, "0805", 1.6, "к", 5), cmp.getType());
		
		compInst = CompInst.getInstWithSpec(
				"R219", "R1-12-0_125_(0805)", "4,7k_5", "");
		cmp = parser.get(compInst);
		
		
		
		

		assertEquals(219, (int)cmp.getRefdes());
		assertNull(cmp.getType().getSpec());
		assertEquals(new RType(new RSpec("Р1-12", null), 0.125, "0805", 4.7, "к", 5), cmp.getType());
		// ==================================================== ТРАНСФОРМАТОРЫ
		
		
		compInst = CompInst.getInstWithSpec(
				"T1", "TIL6V", "TIL6V", "АГО.472.105ТУ");
		cmp = parser.get(compInst);
		
		
		

		assertEquals(1, (int)cmp.getRefdes());
		assertEquals(new TType("ТИЛ6В", null), cmp.getType());
		assertEquals("АГО.472.105ТУ", cmp.getType().getSpec());
		// ==================================================== ДИОДЫ И ТРАНЗИСТОРЫ
		
		
		compInst = CompInst.getInstWithSpec(
				"VD18", "2Д707AC9", "2D707AS9", "аА0.339.583ТУ");
		cmp = parser.get(compInst);
		
		
		
		

		assertEquals(18, (int)cmp.getRefdes());
		
		assertEquals(new VType("2Д707АС9", null), cmp.getType());
		assertEquals("аА0.339.583ТУ", cmp.getType().getSpec());
		
		
		
		compInst = CompInst.getInstWithSpec(
				"VT5", "2T368A9", "2T368A9", "");
		cmp = parser.get(compInst);
		
		
		

		assertEquals(5, (int)cmp.getRefdes());
		
		assertEquals(new VType("2Т368А9", null), cmp.getType());
		assertNull(cmp.getType().getSpec());
		// ==================================================== СОЕДИНИТЕЛИ
		
		
		compInst = CompInst.getInstWithSpec(
				"XS1", "KOLODKA_21", "KOLODKA_21", "");
		cmp = parser.get(compInst);
		
		
		
		

		assertEquals(1, (int)cmp.getRefdes());
		
		assertEquals(new XType("БЯ6.629.051-0_", XType.Type.S, null), cmp.getType());
		assertNull(cmp.getType().getSpec());
		
		
		
		compInst = CompInst.getInstWithSpec(
				"XS2", "KOLODKA_9", "KOLODKA_9", "");
		cmp = parser.get(compInst);
		
		
		
		

		assertEquals(2, (int)cmp.getRefdes());
		
		assertEquals(new XType("БЯ6.629.051", XType.Type.S, null), cmp.getType());
		assertNull(cmp.getType().getSpec());
	}
////	@Test
//	public void test16(){ // прибор 16



//		// ==================================================== КОНДЕНСАТОРЫ
//		st  = "C1                   K10-69_(C0805)       0.1u_25V_+80-20 H=1.0                АЖЯР.673511.002 ТУ                       ";
//		AbstractType cmp = parser.get(compInst);
//		assertEquals(1, (int)cmp.getRefdes());
//		assertEquals("АЖЯР.673511.002 ТУ", cmp.getType().getSpecification());
//		assertEquals(new CType(new CSpec("К10-69в", null), "Н90", 25, "2012М", 0.1, "мк", 8020, true), type);
//		st  = "C33                  K53-68_(B)           4.7u_10V_20     H=2.1                АЖ_Р.673546.007 ТУ                       ";
//		cmp = parser.get(compInst);
//		assertEquals(33, (int)cmp.getRefdes());
//		assertEquals("АЖЯР.673546.007 ТУ", cmp.getType().getSpecification());
//		assertEquals(new CType(new CSpec("К53-68", null), "ЭЛТ", 10, "B", 4.7, "мк", 20, false), type);
//		// ==================================================== МИКРОСХЕМЫ
//		st  = "DA1                  1303ЕН1_8П           1303EN1_8P      H=4.8                АЕ_Р.431420.638 ТУ                       ";
//		cmp = parser.get(compInst);
//		assertEquals(1, (int)cmp.getRefdes());

//		assertEquals(new DType("1303ЕН1,8П", null), type);
//		assertEquals("АЕЯР.431420.638 ТУ", cmp.getType().getSpecification());
//		st  = "DA2                  5101NA015            5101NA015       H=2.8                                                         ";
//		cmp = parser.get(compInst);
//		assertEquals(2, (int)cmp.getRefdes());

//		assertEquals(new DType("5101НА015", null), type);
//		assertNull(cmp.getType().getSpecification());
//		st  = "DD1                  5576XC4T             5576XS4T        H=3.3                АЕ_Р.431260.734 ТУ                       ";
//		cmp = parser.get(compInst);
//		assertEquals(1, (int)cmp.getRefdes());

//		assertEquals(new DType("5576ХС4Т", null), type);
//		assertEquals("АЕЯР.431260.734 ТУ", cmp.getType().getSpecification());
//		st  = "DD3                  5559IN28U            5559IN28U       H=2.9                                                         ";
//		cmp = parser.get(compInst);
//		assertEquals(3, (int)cmp.getRefdes());

//		assertEquals(new DType("5559ИН28У", null), type);
//		assertNull(cmp.getType().getSpecification());
//		// ==================================================== ГЕНЕРАТОРЫ
//		st  = "G1                   ГК108-П              40M             H=1.6                АФТП 433520.007 ТУ                       ";
//		cmp = parser.get(compInst);
//		assertEquals(1, (int)cmp.getRefdes());
//		assertEquals(   new GType("ГК108-П", "40M", null), type);
//		assertNotEquals(new GType("ГК108-П", "41M", null), type);
//		assertEquals("АФТП 433520.007 ТУ", cmp.getType().getSpecification());
//		// ==================================================== ИНДУКТИВНОСТИ
//		// ==================================================== СОЕДИНИТЕЛИ
//		st  = "XP1                  СНП347-10ВП22-В      SNP347-10VP22-V H=13.4               РЮМК.430420.012 ТУ                       ";
//		cmp = parser.get(compInst);
//		assertEquals(1, (int)cmp.getRefdes());

//		assertEquals(new XType("СНП347-10ВП22-В", null), type);
//		assertEquals("РЮМК.430420.012 ТУ", cmp.getType().getSpecification());
//	}
	
//	ComponentReader reader = new AccelComponentReader("SVK.asc");
	
}
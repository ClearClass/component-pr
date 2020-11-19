//package lib.clearclass.model;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.TreeSet;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.BDDMockito.*;
//
//import lib.clearclass.dao.ComponentDAO;
//import lib.clearclass.entity.C;
//import lib.clearclass.entity.CSpec;
//import lib.clearclass.entity.CType;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SpecificationTest {
//    @MockBean
//    private ComponentDAO dao;
//    
//    TreeSet<Integer> refdesSet = new TreeSet<>();
//    
//    @Before
//    public void init(){
//		refdesSet.add(4);
//		refdesSet.add(22);
//		refdesSet.add(6);
//		refdesSet.add(18);
//		refdesSet.add(5);
//		refdesSet.add(3);
//		refdesSet.add(9);
//		refdesSet.add(10);
//		refdesSet.add(109);
//		refdesSet.add(115);
//		refdesSet.add(111);
//		refdesSet.add(110);
//    }
//    
//    
//    
//    
////    @Test
//	public void modelTest() {
//    	List<C> listC = new ArrayList<>();
//		listC.add(new C("sd", 8, 91, new CType(new CSpec("К53-68", null), "ЭЛТ", 10, "В", 10d, "мк", 10, false)));
//		listC.add(new C("sd", 8, 91, new CType(new CSpec("К53-68", null), "ЭЛТ", 10, "В", 10.1, "мк", 10, false)));
//		listC.add(new C("sd", 8, 91, new CType(new CSpec("К10-47Ма", null), "Н90", 50, null, 4.7, "мк", null, false)));
//		
//        given(dao.getComponentsForSp(" ", 16, C.class)).willReturn(listC);
//        
//        System.out.println(dao.getComponentsForSp(" ", 16, C.class));
//
//    }
//
//	
//	@Test
//	public void toNoteTest() {
//		String expected = "DA3-DA6, DA9, DA10, DA18, DA22, DA109-DA111, DA115";
//		assertEquals(expected, Specification.toNote("DA", refdesSet));
//	}
//	
//	@Test
//	public void toNotesTest() {
//		List<String> notes = Specification.toNotes("DA", refdesSet);
//		for(String note : notes)
//			System.out.println(note);
//	}
//
//}

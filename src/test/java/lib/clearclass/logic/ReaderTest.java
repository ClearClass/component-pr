package lib.clearclass.logic;

import java.util.Set;

import org.junit.Test;

import lib.clearclass.logic.parser.AccelComponentReader;
import lib.clearclass.logic.parser.CompInst;
import lib.clearclass.logic.parser.ComponentReader;

public class ReaderTest {
	@Test
	public void readerTest(){
		ComponentReader reader1 = new AccelComponentReader("Items/УТП/Control/sch/BLOCK_UTP.sch.ascii");
//		ComponentReader reader2 = new AttributeComponentReader("other/SVK.atr");
		
		Set<CompInst> set1 = reader1.getSet();
//		Set<CompInst> set2 = reader2.getSet();
//		set1.removeAll(set2);
//
		System.out.println(reader1.getDecml() + ", " + reader1.getTitle());
		System.out.println();
		
		for (CompInst compInst : set1)
			System.out.println(compInst);
		


		
//		for (CompInst compInst : set1){
//			if(compInst.getRefdes().contains(" "))
//				System.out.println(compInst.getRefdes());
//			if(compInst.getOriginalName().contains(" "))
//				System.out.println(compInst.getOriginalName());
//			if(compInst.getCompValue().contains(" "))
//				System.out.println(compInst.getCompValue());
//			if(compInst.getSpec().contains(" "))
//				System.out.println(compInst.getSpec());
//			if(compInst.getManufacturer().contains(" "))
//				System.out.println(compInst.getManufacturer());
//			if(compInst.getMark().contains(" "))
//				System.out.println(compInst.getMark());
//		}
		
		
	}
}
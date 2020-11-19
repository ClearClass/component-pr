package lib.clearclass.logic.services;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lib.clearclass.logic.CadFileType;
import lib.clearclass.logic.Items;
import lib.clearclass.model.ComponentList;
import lib.clearclass.model.ComponentList.Page;
import lib.clearclass.web.CLParams;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComponentListTest {
	@Autowired
	Items items;
	@Autowired
	CLService clService;
	@Autowired
	ExportService es;
	
	ComponentList mdl;
	
	@Before
	public void before() {
		items.delete();
		items.load(CadFileType.SCH);
		items.persist();
		CLParams prms = new CLParams("УТП", "УТП.01.001", "XXX.XX", true, "kemet", "full", 0, 0, 0, 0);
		mdl = clService.getComponentList(prms);
	}
	
//	@Test
	public void modelTest() {
		assertEquals("Стенд входного контроля", mdl.getTitle());
		assertEquals(25, mdl.getFirstPage().size());
		
		int num = 2; // текущий номер страницы
		List<Page> pages = mdl.getPages();
		assertEquals(6, pages.size()+1);
		for (Page page : pages) {
			assertEquals(30, page.getRows().size());
			assertEquals(num++, page.getNum());
		}
	}

	@Test
	public void exportTest() {
		es.createFileFrom(mdl);
		
	}
	
	
	
	
	
	
}
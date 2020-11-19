package lib.clearclass.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import lib.clearclass.dao.ComponentDAO;
import lib.clearclass.entity.CType;
import lib.clearclass.logic.CadFileType;
import lib.clearclass.logic.FileMonitor;
import lib.clearclass.logic.Items;
import lib.clearclass.model.Item;

@Controller
public class PageController {
	@Autowired
	ComponentDAO dao;
	@Autowired
	Items items;
	
	@RequestMapping("/")
	public String index(Model model){
		List<Item> items = FileMonitor.scan();
        model.addAttribute("items", items);
		return "index";
	}
		
	@RequestMapping("/searchPage")
	public String searchPage(Model model, String item){
		
				
		CType.setManufact("Murata");
		System.out.println(CType.getManufact());
		
		// нет соединения с интернетом
//        model.addAttribute("items", dao.getItemNames());
//        model.addAttribute("units", dao.getUnitNames());
		return "searchPage";
	}
	
	@RequestMapping("/orderPage")
	public String orderPage(CLParams prm){
		
		System.out.println(prm);
		
		
//Model model, String item
		return "orderPage";
	}
	
	@RequestMapping("/documentPage")
	public String documentPage(Model model, String item){
		items.delete();
		items.load(CadFileType.SCH);
		items.persist();
        model.addAttribute("items", dao.getItems());
		return "documentPage";
	}

	

	

}
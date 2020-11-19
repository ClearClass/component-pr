package lib.clearclass.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lib.clearclass.entity.CType;
import lib.clearclass.logic.Verifier;
import lib.clearclass.logic.services.CLService;
import lib.clearclass.logic.services.CSService;
import lib.clearclass.logic.services.ExportService;
import lib.clearclass.logic.services.SpService;
import lib.clearclass.model.ComponentList;
import lib.clearclass.model.VerifiedFile;

@Controller
public class ServiceController {
	@Autowired
	CLService clService;
	@Autowired
	SpService spService;
	@Autowired
	CSService csService;
	@Autowired
	ExportService exportService;
	
	@RequestMapping("/createItemFolder")
	public String createItemFolder(String name) throws SQLException{
		new File("Items/" + name + "/Units/sch").mkdirs();
		new File("Items/" + name + "/Units/pcb").mkdir();
		new File("Items/" + name + "/Control/sch").mkdirs();
		new File("Items/" + name + "/Control/pcb").mkdir();
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUsername("nadya");
        dataSource.setPassword("riot");
        dataSource.setUrl("jdbc:h2:./Items/" + name + "/verified;INIT=runscript from 'classpath:sql/input_files.sql'");
        dataSource.getConnection().close();
		return "redirect:/";
	}
	
	
	@RequestMapping("/verify")
	public String verify(Model model, String item, String unit, String ctrl, boolean u_sel, String cc_manuf){
		File schBinFile = new File("Items/" + item + (u_sel? "/Units" : "/Control") + "/sch/" + (u_sel? unit : ctrl));
		VerifiedFile vf = Verifier.perform(schBinFile, cc_manuf);
		model.addAttribute("vf", vf);
		return "verifiedList";
	}
	
	
	
	
	
	
	
	
		
	@RequestMapping("/searchList")
	public String searchList(Model model, String item, String unit){
//		model.addAttribute("rows", clService.getComponentList(item, unit));
		return "searchList";
	}
	
	// document-services ..........
	
	// метод обрабатывает нажатие обеих кнопок
	@RequestMapping("/componentList")
	public ModelAndView componentList(CLParams prm, String act, HttpServletRequest reqs){
		CType.setManufact(prm.cc_manuf);
		ComponentList cmpl = clService.getComponentList(prm);
		if(act.equals("Создать"))
			return new ModelAndView("output/documents/componentList", "cmpl", cmpl);
		
		reqs.setAttribute("cmpl", cmpl);
		return new ModelAndView("forward:/download");
	}
	
	@RequestMapping("/download")
	public ResponseEntity<InputStreamResource> download(HttpServletRequest reqs) throws FileNotFoundException{
		
		ComponentList cmpl =  (ComponentList) reqs.getAttribute("cmpl");
		
//		System.out.println("2: " + cmpl.getPages().size());
//		
//		CLParams prms = new CLParams("УТП", "УТП.01.001", "XXX.XX", true, "kemet", "full", 0, 0, 0, 0);
//		cmpl = clService.getComponentList(prms);
//		
//		
		File doc = exportService.createFileFrom(cmpl);
		
//		new ResponseEntity<Resource>(
//		ResponseEntity<Resource>

		

//		ResponseEntity<File> - нельзя. будет ошибка
		
		InputStreamResource resource = new InputStreamResource(new FileInputStream(doc));

		return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf("application/msword"))
                .header("Content-Disposition", "attachment; filename=" + doc.getName())
                .body(resource);
		
		
		
		
		
		
		
//		return null;
	}
	
	

	
//	
//	@GetMapping("/download")
//	public ResponseEntity downloadFileFromLocal() {
//		Path path = Paths.get("fileName");
//		Resource resource = new UrlResource(path.toUri());
//		
//
//		return ResponseEntity.ok()
//				.contentType(ContentType.)
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//				.body(resource);
//	}
	
	
	
	
	
	
	@RequestMapping("/specification")
	public String specification(Model model, String item, String unit, Integer initpos){
		model.addAttribute("rows", spService.getSpecification(item, unit, initpos));
		model.addAttribute("item", item);
		model.addAttribute("unit", unit);
		return "documents/specification";
	}
	
	@RequestMapping("/сomponentSheet")
	public String сomponentSheet(Model model, String item){
		model.addAttribute("rows", csService.getComponentSheet(item));
		model.addAttribute("item", item);
		return "documents/сomponentSheet";
	}
	
	
	
	
	
	
	
	
	
	
}
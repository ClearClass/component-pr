package lib.clearclass.logic;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.clearclass.dao.ComponentDAO;
import lib.clearclass.entity.*;
import lib.clearclass.logic.parser.AccelComponentReader;
import lib.clearclass.logic.parser.ComponentReader;
import lib.clearclass.logic.parser.Parser;

@Component
public class Items {
	@Autowired
	private ComponentDAO dao;

	private List<Item> items = new ArrayList<>(); // список изделий
	
	// список типономиналов для всех изделий
	private Set<AbstractSpec> specs = new LinkedHashSet<>();
	private Set<AbstractType> types = new LinkedHashSet<>();
	private Parser parser = new Parser(specs, types);
	private boolean saved = false;		
	
	public void load(CadFileType ext) {
		items.clear();
		Arrays.stream(new File("Items").listFiles()).map(item->new Item(item, ext)).forEach(items::add);
	}	
	
	private class Item { // изделие
		final String item; // наименование изделия
		final Date date;   // дата создания каталога изделия
		final List<Unit> units = new ArrayList<>();    // список приборов/контрольного оборудования
		
		Item(File itemDir, CadFileType ext) { // директория изделия
			item = itemDir.getName();
			date = getDate(itemDir);

			parser.setItem(item);
			System.out.println("Изделие: " + item);
			
			if(ext==CadFileType.SCH){ // - только те .sch, которые отмечены в БД как проверенные
				FileMonitor.getVerifiedFiles(item, "Units").map(f->new Unit(f, false)).forEach(units::add);
				FileMonitor.getVerifiedFiles(item, "Control").map(f->new Unit(f, true)).forEach(units::add);
			} else { // CadFileType.PCB    // - все имеющиеся .pcb
				Arrays.stream(new File(itemDir.getPath() + "/Units/pcb").listFiles((dir, name)->name.endsWith(".ascii")? true : false)).map(f->new Unit(f, false)).forEach(units::add);
				Arrays.stream(new File(itemDir.getPath() + "/Control/pcb").listFiles((dir, name)->name.endsWith(".ascii")? true : false)).map(f->new Unit(f, true)).forEach(units::add);
			}
		}
		
		class Unit {             // прибор/приставка	
			final String decml;  // децимальный номер
			final String title;  // название прибора, напр. "Прибор 8"
			final boolean isControl; // является ли приставкой (контрольным оборудованием)
			
			// компоненты прибора
//			Set<AbstractComponent<?>> q;
			Set<AbstractComponent<? extends AbstractType>> cmpset = new LinkedHashSet<>();
			
			Unit(File asciiUnitFile, boolean isControl) { // файл прибора *.asc
				ComponentReader reader = new AccelComponentReader(asciiUnitFile);
				this.decml = reader.getDecml();
				this.title = reader.getTitle();
				this.isControl = isControl;
				parser.setUnit(decml);
				System.out.println("\tПрибор " + decml + " (" + title + "), изд." + item);
	
				while(reader.hasNext())
					if(!cmpset.add(parser.get(reader.next())))
						throw new RuntimeException();
			}
			
			void print(PrintStream ps){
				ps.println("Прибор: " + decml + " (" + title + ")");
				for(AbstractComponent<? extends AbstractType> cmp : cmpset) 
					ps.println(cmp);
			}
			
			void persist(){
				dao.save(item, decml, title, isControl);
				dao.save(cmpset);
			}
		}
		
		void print(PrintStream ps){
			ps.println("Изделие: " + item);
			for(Unit unit : units) unit.print(ps);				
		}
		
		void persist(){
			dao.save(item, date);
			for(Unit unit : units)
				unit.persist();				
		}
	}
	
	private Date getDate(File dir){
		try {
			BasicFileAttributes attr = Files.readAttributes(dir.toPath(), BasicFileAttributes.class);
			return new Date(attr.creationTime().toMillis());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void print(PrintStream ps){
		ps.println(" === Типономиналы элементов ==============================");
		for(AbstractSpec spec : specs) 
			ps.println(spec);
		for(AbstractType type : types) 
			ps.println(type);
		ps.println(" === Применяемость элементов ==============================");
		for(Item item : items) 
			item.print(ps);
	}
	
	public void print(){
		print(System.out);
	}
	
	@Transactional
	public void persist(){
		if(!saved){
			dao.save(specs);
			dao.save(types);
			saved = true;
		}

		for(Item item : items)
			item.persist();
	}
	
	@Transactional
	public void delete(){
		dao.delete();
	}
}
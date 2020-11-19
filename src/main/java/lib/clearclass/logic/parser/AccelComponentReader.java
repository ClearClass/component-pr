package lib.clearclass.logic.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class AccelComponentReader implements ComponentReader{
	private TreeSet<CompInst> comps = new TreeSet<>();
	private Iterator<CompInst> it;
	private String decml;
	private String title;

	public AccelComponentReader(File asciiUnitFile) {
		try(BufferedReader fileReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(asciiUnitFile), "cp1251"))){
			String st; 
			while((st = fileReader.readLine())!=null){ 
				if(st.matches("  \\(compInst \"[ACDFGHLRSTUVXZ][^NICGO][^\\.]*")){
					String refdes = getFirstValue(st);
					// атрибуты
					String originalName = "";
					String compValue = "";
					String spec = "";
					String manufact = "";
					String mark = "";
					
					while(!(st = fileReader.readLine()).equals("  )")){ // чтение атрибутов
						if(st.contains("originalName"))
							originalName = getFirstValue(st).trim();
						else if (st.contains("compValue"))
							compValue = getFirstValue(st).trim();
						else if (st.contains("ТУ"))
							spec = getSecondValue(st).trim();
						else if (st.contains("Manufacturer"))
							manufact = getSecondValue(st).trim();
						else if (st.contains("Обозначение"))
							mark = getSecondValue(st).trim();
					}
					comps.add(new CompInst(refdes, originalName, compValue, spec, manufact, mark));
				}
				
				if(st.contains("designInfo")){
					while(!(st = fileReader.readLine()).equals("      )")){ // чтение атрибутов
						if(getFirstValue(st).equals("Наименование"))
							title = getSecondValue(st).trim();
						else if(getFirstValue(st).equals("Наименование 1"))
							title = title.isEmpty()? getSecondValue(st).trim() : title;
//								 + " " + get SecondValue(st).trim();
						else if(getFirstValue(st).equals("Обозначение"))
							decml = getSecondValue(st).trim().replace(" Э3", "");
					}
				}
			}
				
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		it = comps.iterator();
		
		
		
		String fileName = asciiUnitFile.getName();
		if(fileName.endsWith(".pcb.ascii"))
			decml = fileName.replace(".pcb.ascii", "");
		
		
		
		
		
		
		
		
	}
	
	public AccelComponentReader(String asciiUnitFile) {
		this(new File(asciiUnitFile));
	}

	private String getFirstValue(String st){
		return st.substring(st.indexOf("\"")+1, st.indexOf("\"", st.indexOf("\"")+1));
	}
	private String getSecondValue(String st){
		return st.split("\"")[3];
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public CompInst next() {
		return it.next();
	}

	@Override
	public String getTitle() {
//		if(unitName.length()>18)
//			return unitName.substring(0, 15).trim() + "...";
		return title;
	}

	@Override
	public String getDecml() {
		return decml;
	}

	@Override
	public Set<CompInst> getSet() {
		return comps;
	}
}
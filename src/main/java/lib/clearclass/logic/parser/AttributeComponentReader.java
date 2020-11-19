package lib.clearclass.logic.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

@Deprecated
public class AttributeComponentReader implements ComponentReader{
	private TreeSet<CompInst> comps = new TreeSet<>();
	private Iterator<CompInst> it;
	
	public AttributeComponentReader(File file) { //  *.atr файл
		try(BufferedReader unitReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), "cp1251"))){
			// чтение строк 5, 6 для определения границ столбцов в файле *.atr
			for(int i = 0; i < 4; i++)
				unitReader.readLine();
			String st5 = unitReader.readLine();
			String st6 = unitReader.readLine();
			
			// границы столбцов данных в файле *.atr
			final int rf1 = st5.indexOf("RefDes");
			final int rf2 = st6.indexOf(' ', rf1);
			final int tp1 = st5.indexOf("Type");
			final int tp2 = st6.indexOf(' ', tp1);
			final int vl1 = st5.indexOf("Value");
			final int vl2 = st6.indexOf(' ', vl1);
			
			final int sp1 = st5.indexOf("ТУ");
			final int sp2 = st6.indexOf(' ', sp1)==-1? st6.length() : st6.indexOf(' ', sp1);
			final int mf1 = st5.indexOf("Manufacturer");		
			final int mf2 = st6.indexOf(' ', mf1)==-1? st6.length() : st6.indexOf(' ', mf1);
			final int mr1 = st5.indexOf("Обозначение");
			final int mr2 = st6.indexOf(' ', mr1)==-1? st6.length() : st6.indexOf(' ', mr1);
			
			unitReader.readLine(); // 7-ая строка - пустая
			String st; // 8-ая строка - начало данных
			while(!(st = unitReader.readLine()).isEmpty()){ 
				if(st.matches(".*CONTACT_[10].[08].POINT.*"))
					continue;
				String refdes       = st.substring(rf1, rf2).trim();
				String originalName = st.substring(tp1, tp2).trim();
				String compValue    = st.substring(vl1, vl2).trim();
				String spec     = (sp1==-1)? "" : st.substring(sp1, sp2).trim();
				String manufact = (mf1==-1)? "" : st.substring(mf1, mf2).trim();
				String mark     = (mr1==-1)? "" : st.substring(mr1, mr2).trim();	
				comps.add(new CompInst(refdes, originalName, compValue, spec, manufact, mark));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		it = comps.iterator();
	}
	
	public AttributeComponentReader(String file) {
		this(new File(file));
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
	public String getDecml() {
		return null;
	}

	@Override
	public String getTitle() {
		// инициализируем объект Properties
//		Properties dec = new Properties(); // децимальные номера приборов
//		try(FileReader fr = new FileReader(unitsDir.getPath() + "/decimal.properties")){
//			dec.load(fr);
//		} catch (IOException e) {
//			System.err.println("Ошибка чтения файла децимальных номеров: " + e);
//		}
		return null;
	}

	@Override
	public Set<CompInst> getSet() {
		return comps;
	}
}
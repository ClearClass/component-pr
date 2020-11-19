package lib.clearclass.logic.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lib.clearclass.dao.ComponentDAO;
import lib.clearclass.entity.*;
import lib.clearclass.model.CSRow;

/**
 * Ведомость покупных изделий (ВП)
 */

@Service
public class CSService {
	private String item;
	private List<CSRow> rows = new ArrayList<>(); // список строк в Ведомости 
	private int rowNum = 0; // текущая заполненная строка #1..29
	
	private boolean isNewCategory = true;
	private boolean isNewPage = true;
	private boolean isDark = false;
	
	private final int lenght1 = 30; // ширина поля name
		
	@Autowired
	private ComponentDAO dao;
	
	public List<CSRow> getComponentSheet(String item){ 
		this.item = item;
		this.isDark = false;
		rows.clear();
		rowNum = 0;	
		fillPositions(R.class);
		fillPositions(C.class);
		fillPositions(D.class);
		fillPositions(L.class);
		fillPositions(V.class);
		fillPositions(G.class);
		fillPositions(X.class);
		fillPositions(U.class);
		fillPositions(T.class);
		return rows;
	}

	// метод формирует позиции для заданного типа - категорию
	private <E extends AbstractComponent<?>> void fillPositions(Class<E> type){
		List<E> comps = dao.getComponentsForCS(item, type); // компоненты, отсортированные по типу и прибору
		Iterator<E> it = comps.iterator();
		if(it.hasNext()){
			E e = it.next(); // извлекаем первый элемент
			//  ---------------------------------- значения текущей позиции
			AbstractType currentType = e.getType();
			boolean isNewType = true; // новый ли тип типономинала
			String currentUnit = e.getUnit();
			int amount = 1;
			TreeMap<String, Integer> u_a = new TreeMap<>(); // отображение unit-amount
			// -----------------------------------------------------------
			while(it.hasNext()){
				e = it.next();
				if(e.getType().equals(currentType)){ // тот же типономинал
					if(e.getUnit().equals(currentUnit)) // тот же unit
						amount++;
					else { // другой unit
						u_a.put(currentUnit, amount); 
						currentUnit = e.getUnit();
						amount = 1;
					}
				} else { // другой типономинал - новая позиция
					u_a.put(currentUnit, amount);
					flush(currentType, isNewType, u_a);
					// ------------------- обновить текущую позицию (заменить на новую)
					isNewType = !(e.getType().getType().equals(currentType.getType()));
					currentType = e.getType();
					currentUnit = e.getUnit();
					amount = 1;
					u_a.clear();
					// ----------------------------------------------------------------
				}
			}
			u_a.put(currentUnit, amount);
			flush(currentType, isNewType, u_a);
		}
		isNewCategory = true;
	}
	
	// добавить строки для данной позиции
	private void flush(AbstractType type,
			boolean isNewType, TreeMap<String, Integer> u_a){
				
		// сколько строк потребует позиция..
		int num = Split.num(type.toString(), lenght1); // количество строк для Наименования
		int req = (isNewCategory? 4:0)		
				  + Math.max(num, u_a.size()>1? (u_a.size()+1):1) 
				  + 1;
				
		// остаток строк на странице
		if((29-rowNum)<req){
			while(rowNum!=29) 
				rows.add(new CSRow(incNum(), isDark));
			isNewPage = true;
			isDark=!isDark;
		}
		
		// нужно ли указывать спецификацию
		boolean spFlag = isNewCategory || isNewPage || isNewType;
		
		// .. добавляем позицию		
		if(isNewCategory){
			rows.add(new CSRow(incNum(), isDark)); 
			rows.add(new CSRow(incNum(), type.getGenTypeNames(), isDark).setNewCategory());
		} 
		if(isNewCategory || isNewPage){  
			rows.add(new CSRow(incNum(), isDark));
			rows.add(new CSRow(incNum(), type.getGenTypeName(), isDark));
			isNewCategory = false;
			isNewPage = false;
		}
		// .. позиция
		Iterator<String> typeIt = Split.doo(type.toString(), lenght1).iterator();
		Iterator<String> unitIt = u_a.keySet().iterator();
		// .. первая строка
		String name = typeIt.next();
		String specSt = spFlag? type.getSpec() : "";
		String unit = unitIt.next();
//		String unitSt = dao.getDecimal(item, unit);
		String unitSt = null;
		Integer amount = u_a.get(unit);
		rows.add(new CSRow(incNum(), name, specSt, unitSt, amount, amount, isDark));
		// .. последующие строки позиции
		int sum = amount;
		while(typeIt.hasNext() || unitIt.hasNext()){
			name = typeIt.hasNext()? typeIt.next() : "";
			if (unitIt.hasNext()){
				unit = unitIt.next(); 
//				unitSt = dao.getDecimal(item, unit);
				
				amount = u_a.get(unit);
				sum+=amount;
			} else {
				unitSt = "";
				amount = null;
			}
			rows.add(new CSRow(incNum(), name, unitSt, amount, amount, isDark));
		}
		
		// строка суммы
		if(u_a.size()>1) 
			rows.add(new CSRow(incNum(), sum, isDark));
		
		// завершающая пустая
		rows.add(new CSRow(incNum(), isDark));
	}
	
	private int incNum(){
		return rowNum<29? ++rowNum : (rowNum=1);
	}
}
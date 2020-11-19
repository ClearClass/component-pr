package lib.clearclass.logic.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lib.clearclass.dao.ComponentDAO;
import lib.clearclass.entity.*;
import lib.clearclass.model.SPRow;

/**
 * Спецификация
 */

@Service
public class SearchService {
	private String item;
	private String unit;
	private List<SPRow> rows = new ArrayList<>(); // список строк в спецификации
		
	@Autowired
	private ComponentDAO dao;
		
	public List<SPRow> getSearchList(String item, String unit, boolean multi){ 
		this.item = item;
		this.unit = unit;
		
		rows.clear();
		
//		fillPositions(C.class);
//		fillPositions(D.class);
//		fillPositions(G.class);
//		fillPositions(L.class);
//		fillPositions(R.class);
//		fillPositions(T.class);
//		fillPositions(U.class);
//		fillPositions(V.class);
//		fillPositions(X.class);
		
		return rows;
	}

	
//	// метод формирует позиции для заданного типа
//	private <E extends AbstractComponent<?>> void fillPositions(Class<E> type){
//		List<E> comps = dao.getComponentsForSp(item, unit, type); // компоненты, отсортированные по типу
//		Iterator<E> it = comps.iterator();
//		if(it.hasNext()){
//			E e = it.next(); // извлекаем первый элемент
//			//  ---------------------------------- значения текущей позиции
//			AbstractType currentType = e.getType();
//			String refdesName = (e instanceof CompositeRefDes) ? ((CompositeRefDes)e).getRefDesName() : type.getSimpleName();
//			TreeSet<Integer> refdesSet = new TreeSet<>(); // множество позиционных номеров
//			refdesSet.add(e.getRefdes());
//	
//			// -----------------------------------------------------------
//			while(it.hasNext()){
//				e = it.next();
//				if(e.getType().equals(currentType)){ // тот же типономинал
//					refdesSet.add(e.getRefdes());
//				} else { // другой типономинал
//					if(currentType.getType().equals(e.getType().getType())){ // тот же тип типономинала
//						if(atr == PositionType.SINGLE) atr = PositionType.MULTI_START;
//						flush(currentPos++, currentType, refdesName, refdesSet, atr);
//						currentType = e.getType();
//						refdesSet.clear(); 
//						refdesSet.add(e.getRefdes());
//						atr = PositionType.MULTI_NEXT;
//					} else {
//						flush(currentPos++, currentType, refdesName, refdesSet, atr);
//						currentType = e.getType();
//						if(e instanceof CompositeRefDes) refdesName = ((CompositeRefDes)e).getRefDesName();
//						refdesSet.clear(); 
//						refdesSet.add(e.getRefdes());
//						atr = PositionType.SINGLE;
//					}
//				}
//			}
//			flush(currentPos++, currentType, refdesName, refdesSet, atr);
//		}
//	}
//	
//	// отправляет позицию на печать, в однострочное или многострочное представление
//	private void flush(int pNum, AbstractType type, String refdesName,
//			TreeSet<Integer> refdesSet, PositionType pType){
//		String posNum = String.valueOf(pNum);
//		switch(mode) {
//			case 0: toPrint(posNum, type, refdesName, refdesSet, pType); break;
//			case 1: toSingleRows(posNum, type, refdesName, refdesSet, pType); break;
//			case 2: toMultiRows(posNum, type, refdesName, refdesSet, pType); break;
//		}
//	}
//	
//	// выводит позицию на печать
//	private void toPrint(String posNum, AbstractType type, String refdesName, 
//			TreeSet<Integer> refdesSet, PositionType pType){
//		System.out.println(posNum+" "+type+" "+refdesName+" "+refdesSet+" "+pType);
//	}
//		
//	// преобразует позицию в однострочном (упрощенном) режиме
//	private void toSingleRows(String posNum, AbstractType type, String refdesName, 
//			TreeSet<Integer> refdesSet, PositionType pType){
//		String typeSt = type.toString();
//		String specSt = type.getSpecification();
//		String amount = String.valueOf(refdesSet.size());
//		String note = toNote(refdesName, refdesSet);
//		if(pType==PositionType.SINGLE){
//			rows.add(new SPRow(posNum, type.getTypeName(), amount, note));
//			rows.add(new SPRow(typeSt));
//			rows.add(new SPRow(specSt));
//			rows.add(new SPRow());
//		} else if(pType==PositionType.MULTI_START) {
//			rows.add(new SPRow(type.getTypeNames()));
//			rows.add(new SPRow(specSt));
//			rows.add(new SPRow());
//			rows.add(new SPRow(posNum, typeSt, amount, note));
//			rows.add(new SPRow());
//		} else if(pType==PositionType.MULTI_NEXT) {
//			rows.add(new SPRow(posNum, typeSt, amount, note));
//			rows.add(new SPRow());
//		}
//	}
//	
//	// преобразует множество позиционных обозначений в строку
//	static String toNote(String rdName, TreeSet<Integer> rdSet){
//		if(rdSet.isEmpty()) 
//			throw new RuntimeException("RefDes set is empty!");
//		StringBuilder note = new StringBuilder();
//		Iterator<Integer> it = rdSet.iterator();
//		int num1 = it.next();
//		int amount = 1;
//		int i = 1; // счетчик цикла
//		while(it.hasNext()){
//			int num2 = it.next();
//			if(num1 == (num2-i++)){
//				amount++;
//			} else {
//				if(amount==1)      note.append(rdName + num1 + ", ");
//				else if(amount==2) note.append(rdName + num1 + ", " + rdName + (num1+1) + ", ");
//				else if(amount>2)  note.append(rdName + num1 + "-" + rdName + (num1+amount-1) + ", ");
//				num1 = num2;
//				amount = 1;
//				i = 1;
//			}
//		}
//		if(amount==1)      note.append(rdName + num1);
//		else if(amount==2) note.append(rdName + num1 + ", " + rdName + (num1+1));
//		else if(amount>2)  note.append(rdName + num1 + "-" + rdName + (num1+amount-1));
//		return note.toString();
//	}
//	
//	// преобразует позицию в многострочном режиме
//	private void toMultiRows(String posNum, AbstractType type, String refdesName,
//			TreeSet<Integer> refdesSet, PositionType pType){
//		List<String> names = toNames(type, pType);
//		List<String> notes = toNotes(refdesName, refdesSet);
//		Iterator<String> it1 = names.iterator();
//		Iterator<String> it2 = notes.iterator();
//		String amount = String.valueOf(refdesSet.size());
//		if(pType==PositionType.MULTI_START){
//			rows.add(new SPRow(type.getTypeNames()));
//			rows.add(new SPRow(type.getSpecification()));
//			rows.add(new SPRow());
//		}
//		rows.add(new SPRow(posNum, it1.next(), amount, it2.next()));
//		while(it1.hasNext() || it2.hasNext())
//			rows.add(new SPRow(it1.hasNext()? it1.next():"", it2.hasNext()? it2.next():""));
//		rows.add(new SPRow());
//	}
	
	
	
	
}
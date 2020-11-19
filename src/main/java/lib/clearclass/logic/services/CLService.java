package lib.clearclass.logic.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lib.clearclass.dao.ComponentDAO;
import lib.clearclass.entity.*;
import lib.clearclass.model.ComponentList;
import lib.clearclass.model.ComponentList.Row;
import lib.clearclass.web.CLParams;

/**
 * Перечень элементов (ПЭ3)
 */

@Service
public class CLService {
	@Autowired
	private ComponentDAO dao;
	
	private String item;
	private String unit;
	
	public ComponentList getComponentList(CLParams prm){ 
		this.item = prm.getItem();
		this.unit = prm.getUnit();
		
		ComponentList cmpl = new ComponentList(item, unit, dao.getTitle(item, unit));
		
		List<Position> poss;
		poss = getPositions(A.class); cmpl.fillCat(poss, Mode.WithoutMerge, 0);
		poss = getPositions(C.class); cmpl.fillCat(poss, prm.c_mode().equals("full")? Mode.FullMerge : Mode.PartlyMerge, prm.c_lines());
		poss = getPositions(D.class); cmpl.fillCat(poss, Mode.PartlyMerge, prm.d_lines());
		poss = getPositions(F.class); cmpl.fillCat(poss, Mode.WithoutMerge, 0);
		poss = getPositions(G.class); cmpl.fillCat(poss, analyze(poss), 0);
		poss = getPositions(H.class); cmpl.fillCat(poss, analyze(poss), 0);
		poss = getPositions(L.class); cmpl.fillCat(poss, analyze(poss), 0);
		poss = getPositions(R.class); cmpl.fillCat(poss, Mode.FullMerge, prm.r_lines());
		poss = getPositions(S.class); cmpl.fillCat(poss, Mode.WithoutMerge, 0);
		poss = getPositions(T.class); cmpl.fillCat(poss, Mode.WithoutMerge, 0);
		poss = getPositions(U.class); cmpl.fillCat(poss, analyze(poss), prm.u_lines());
		poss = getPositions(V.class); cmpl.fillCat(poss, Mode.WithoutMerge, 0);
		poss = getPositions(X.class); cmpl.fillCat(poss, Mode.WithoutMerge, 0);
		poss = getPositions(Z.class); cmpl.fillCat(poss, Mode.WithoutMerge, 0);
		cmpl.finalizePage();
		return cmpl;
	}
	
	static class Group {
		
		
		
	}
	
	
	

	private <E extends AbstractComponent<?>> List<Position> getPositions(Class<E> type){
		List<Position> poss = new ArrayList<>();
		//_________________________________________________________________________________________________
		List<E> comps = dao.getComponentsForCL(item, unit, type); // компоненты, отсортированные по refdes
		Iterator<E> it = comps.iterator();
		if(it.hasNext()){
			E e = it.next(); // извлекаем первый элемент
			//  ---------------------------------- значения текущей строки
			String refdesName = (e instanceof CompositeRefDes)? ((CompositeRefDes)e).getRefDesName() : type.getSimpleName();
			int initRefDes = e.getRefdes();
			AbstractType currentType = e.getType();
			int amount = 1;
			// -----------------------------------------------------------
			while(it.hasNext()){
				e = it.next();
				if(e.getType().equals(currentType)){
					amount++;
				} else {
					poss.add(new Position(refdesName, initRefDes, currentType, amount));
					
					if(e instanceof CompositeRefDes && 
							!((CompositeRefDes)e).getRefDesName().equals(refdesName)){
						refdesName = ((CompositeRefDes)e).getRefDesName();
						poss.add(new Position(null, 0, null, 0));
					}
					
					initRefDes = e.getRefdes();
					currentType = e.getType();
					amount = 1;
				}
			}
			poss.add(new Position(refdesName, initRefDes, currentType, amount));
		}
		return poss;
	}
	
	

	
	private Mode analyze(List<Position> poss){
//		if(poss.isEmpty())
//			return null;
		
		// семисегментный
		// единичный
		
		if(poss.size()==1)
			return Mode.WithoutMerge; 
		
		Set<Row.Name> fullHeaders = new HashSet<>();
		
		for (Position pos : poss) {
			fullHeaders.add(pos.getFullHeader());
			
		}
		
//		if(fullHeaders.size()==1)
//			return Mode.FullMerge;
		return Mode.WithoutMerge;
	}
	
	// с полным объединением в группу
	// с частичным объединением (по названию)
	// без объединения (без заголовка)
	// WithoutMerge - строки отображаются без объединения в группу.
	//				  Каждая строка - это "имя компонента" + "тип" + "ТУ"
	public enum Mode {
		WithoutMerge, PartlyMerge, FullMerge;
		
		private int initSimilarRows;
		
		public int getInitSimilarRows() {
			return initSimilarRows;
		}
				
		public void setInitSimilarRows(List<Position> poss){
			int initSimilarRows = 0;
			Row.Name firstHeader = poss.get(0).getFullHeader();
			for(Position pos : poss)
				if(pos.getFullHeader().equals(firstHeader))
					initSimilarRows++;
				else break;
			this.initSimilarRows = initSimilarRows;
		}
	}
	
	

	
	public static class Position {
		String refdesName; 
		Integer initRefDes; 
		AbstractType type; 
		Integer amount;

		public Position(String refdesName, Integer initRefDes, AbstractType type, Integer amount) {
			this.refdesName = refdesName;
			this.initRefDes = initRefDes;
			this.type = type;
			this.amount = amount;
		}

		public Row.Name getBasicHeader() {
			return new Row.Name(type.getGenTypeNames(), null, null);
		}
		
		public Row.Name getFullHeader() {
			String typeSt = type.getType();
			if(typeSt.equals("CC") || typeSt.equals("CC_TAJ") || typeSt.equals("B41851") || typeSt.equals("CR"))
				return new Row.Name(type.getGenTypeNames(), null, type.getSpec());		
			return new Row.Name(type.getGenTypeNames(), typeSt, type.getSpec());
		}
		
		// ---------------------------------------------------------------------------------
		public String getRefdes() {
			if(amount==1) 
				return refdesName + initRefDes;
			if(amount==2)  
				return refdesName + initRefDes + ", " + refdesName + (initRefDes+1);           
			return refdesName + initRefDes + "-" + refdesName + (initRefDes+amount-1);
		}

		public List<Row.Name> getName(Mode mode) {
			List<Row.Name> names = new ArrayList<>();
			
			String st1 = type.getGenTypeName();
			String st2 = type.toString();
			String st3 = type.getSpec();
			
			if(mode==Mode.WithoutMerge){
				String st = st1 + " " + st2 + " " + st3;
				if(st.length()<43)
					names.add(new Row.Name(st1, st2, st3, type.isExtName(), type.isExtSpec()));
				else {
					names.add(new Row.Name(st1, st2, null, type.isExtName(), false));
					names.add(new Row.Name(null, null, st3, false, type.isExtSpec()));
				}
				return names;
			}
			if(mode==Mode.PartlyMerge){
				names.add(new Row.Name(null, st2, st3, false, type.isExtSpec()));
				return names;
			}
			if(mode==Mode.FullMerge){
				names.add(new Row.Name(null, st2, null));
				return names;
			}
			return null;
		}
	
		public boolean isDouble(Mode mode) {
			return getName(mode).size()==2? true : false;
		}
		
		public String getAmount() {
			return amount.toString();
		}
		
		public String getNote() {
			return type.toNote();
		}

		public boolean isNull() {
			return type==null;
		}
	}
	
	
	
	
	

}
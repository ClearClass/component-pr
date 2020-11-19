package lib.clearclass.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lib.clearclass.entity.AbstractComponent;
import lib.clearclass.entity.AbstractType;
import lib.clearclass.entity.CType;
import lib.clearclass.entity.DType;
import lib.clearclass.entity.GType;
import lib.clearclass.entity.HType;
import lib.clearclass.entity.LType;
import lib.clearclass.entity.TType;
import lib.clearclass.entity.VType;
import lib.clearclass.entity.XType;
import lib.clearclass.logic.services.CLService.Mode;
import lib.clearclass.logic.services.CLService.Position;

public class ComponentList {
	
	public static class Row {
		private String refdes;
		private Name name;
		private String amount;
		private Boolean centerName = false;
		private String note;
		
		private Row(String refdes, Name name, String amount, String note){
			this.refdes = refdes;
			this.name = name;
			this.amount = amount;
			this.note = note;
		}
		
		private Row(){
			this.refdes = "";
			this.name = new Name(null, null, null);
			this.amount = "";
			this.note = "";
		}
		
		// для [примечания]
		private Row(String name){
			this.refdes = "";
			this.name = new Name(name, null, null);
			this.amount = "";
			this.note = "";
		}
		
		// для заголовков
		private Row(Name name){
			this.refdes = "";
			this.name = name;
			this.centerName = true;
			this.amount = "";
			this.note = "";
		}
		

		

		
		public String getRefdes() {
			return refdes;
		}
		public Name getName() {
			return name;
		}
		public String getAmount() {
			return amount;
		}
		public Boolean getCenterName() {
			return centerName;
		}
		public String getNote() {
			return note;
		}
		
		
		
		
		
		public static class Name {
			private String name;
			private String type;
			private String spec;
			
			private boolean extName;
			private boolean extSpec;
			
			public Name(String name, String type, String spec, boolean extName, boolean extSpec) {
				this.name = name;
				this.type = type;
				this.spec = spec;
				this.extName = extName;
				this.extSpec = extSpec;
			}

			public Name(String name, String type, String spec) {
				this(name, type, spec, false, false);
			}

			public String getName() {
				return name;
			}
			public String getType() {
				return type;
			}
			public String getSpec() {
				return spec;
			}

			
			public boolean getExtName() {
				return extName;
			}
			public boolean getExtSpec() {
				return extSpec;
			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((name == null) ? 0 : name.hashCode());
				result = prime * result + ((spec == null) ? 0 : spec.hashCode());
				result = prime * result + ((type == null) ? 0 : type.hashCode());
				return result;
			}

			@Override
			public boolean equals(Object ob) {
				if (this == ob)
					return true;
				if (ob == null)
					return false;
				if (getClass() != ob.getClass())
					return false;
				Name other = (Name) ob;
				if (name == null) {
					if (other.name != null)
						return false;
				} else if (!name.equals(other.name))
					return false;
				if (spec == null) {
					if (other.spec != null)
						return false;
				} else if (!spec.equals(other.spec))
					return false;
				if (type == null) {
					if (other.type != null)
						return false;
				} else if (!type.equals(other.type))
					return false;
				return true;
			}
		}
	}
	

	
	
	
	
	public static class Page {
		private static int currentNum;
		private final int num;
		private final List<Row> rows = new ArrayList<>(30);

		Page(){
			this.num = ++currentNum;
			rows.add(new Row());
		}
		public int getNum() {
			return num;
		}
		public List<Row> getRows() {
			return rows;
		}
	}
	
	
	private String item;
	private String unit;
	private String title;
	
	public ComponentList(String item, String unit, String title){
		Page.currentNum = 1;
		firstPage.add(new Row());
		this.item = item;
		this.unit = unit;
		this.title = title;
	}
	
	
	
	
	public List<Row> getFirstPage() {
		return firstPage;
	}
	
	public List<Page> getPages() {
		return pages;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getItem() {
		return item;
	}




	public String getUnit() {
		return unit;
	}




	public String getTitle() {
		return title;
	}




	private List<Row>  firstPage = new ArrayList<>(25);
	private List<Page> pages = new ArrayList<>();



	
	
	
	private List<Row> getCurrentPage(){
		return (Page.currentNum==1)? firstPage : pages.get(Page.currentNum-2).rows;
	}
	
	
	
	
	
	private void addPos(Position pos, Mode mode){
		
		// получить текущую страницу
		List<Row> page = getCurrentPage();


		
		
		// количество оставшихся строк на странице
		int num = remainRows(page);
		
		
		
		
		if(pos.isNull()){
			if(num==0)
				return;
			page.add(new Row());
			return;
		}
				
		
		
		
		
		// =================================================================
		// ситуация 1. начало новой категории в конце страницы
		
		// условие перехода на новую страницу
		boolean moreThanOne = remain>1; // если группа - более одной позиции
		boolean withHeader = mode!=Mode.WithoutMerge; // если необходим заголовок
		boolean withFullHeader = mode==Mode.FullMerge;
		
		if(num==1 && isNewCat && moreThanOne){ // переход на новую страницу
			page.add(new Row());
			addPos(pos, mode);
			return;
		}
		
		
		if(num>0 && num<4 && isNewCat && withHeader){
			for (int i = 0; i < num; i++)
				page.add(new Row());
			addPos(pos, mode);
			return;
		}
		
		if(num==4 && isNewCat && withFullHeader){
			int n = mode.getInitSimilarRows();
			if(n==1){
				for (int i = 0; i < 4; i++)
					page.add(new Row());
				addPos(pos, mode);
				return;
			}
		}
		// =================================================================
		
		if(num==1 && pos.isDouble(mode)){
			page.add(new Row());
			addPos(pos, mode);
			return;
		}
		
		// проблема 2. добавление в конце страницы 'строка + заголовок'
		if(num==1 && withFullHeader && !headers.contains(pos.getFullHeader())){
			page.add(new Row());
			addPos(pos, mode);
			return;
		}

		if(num==0){
			pages.add(new Page());
			isNewPage = true;
	
			if(remain==1)
				mode = Mode.WithoutMerge;

			addPos(pos, mode);
			return;
		}
		
		
		
		if(mode==Mode.PartlyMerge && (isNewCat || isNewPage)){
			page.add(new Row(pos.getBasicHeader()));
			page.add(new Row());
			isNewCat = false;
			isNewPage = false;
		}
		
		if(mode==Mode.WithoutMerge && isNewCat){
			isNewCat = false;
		}

		if(mode==Mode.FullMerge){
			if(isNewCat || isNewPage){
				headers.clear();
				headerIndex = page.size();
				page.add(headerIndex++, new Row(pos.getFullHeader()));
				page.add(new Row());
				headers.add(pos.getFullHeader());
				isNewCat = false;
				isNewPage = false;
				
			} else if(headers.add(pos.getFullHeader()))
				page.add(headerIndex++, new Row(pos.getFullHeader()));
		}

		page.add(new Row(pos.getRefdes(), pos.getName(mode).get(0), pos.getAmount(), pos.getNote()));
		if(pos.isDouble(mode))
			page.add(new Row("", pos.getName(mode).get(1), "", ""));
	}
	
	
	Set<Row.Name> headers = new HashSet<>();
	private int headerIndex;
	
	
	
	private boolean isNewCat;
	private boolean isNewPage;
	
	
	// декрементируется только после выхода из addPos()
	int remain;
	
	
	
	public void fillCat(List<Position> poss, Mode mode, int n){
		if(poss.isEmpty()) return;
	
		isNewCat = true;

		if(mode==Mode.FullMerge)
			mode.setInitSimilarRows(poss);
		
		
		
		
		
		for(remain = poss.size(); remain>0; remain--) 
			addPos(poss.get(poss.size()-remain), mode);
		
		
		
		
		
			
		
		
		// получить текущую страницу
		List<Row> page = getCurrentPage();
		
		// пустая строка по окончании группы позиций
		if(remainRows(page)!=0)
			page.add(new Row());
			
		
		
		boolean doubleSpacing = false;
		
		if(remainRows(page)!=0 && doubleSpacing)
			page.add(new Row());
		
		
		

		
			
		
		
		
		if(n!=0){ // [примечание]
			for (int i=0; i<n; i++){
				if(remainRows(page)==0){
					pages.add(new Page());
					page = getCurrentPage();
				}
				page.add(new Row("[примечание]"));
			}
			if(remainRows(page)!=0)
				page.add(new Row());
		}
	}
	
	private int remainRows(List<Row> page){
		return ((Page.currentNum==1)? 25 : 30) - page.size();
	}
	
	
	public void finalizePage(){
		// получить текущую страницу
		List<Row> page = getCurrentPage();
		// количество оставшихся строк на странице
		int num = remainRows(page);
		
		for (int i = 0; i < num; i++)
			page.add(new Row());

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public <E extends AbstractComponent<?>> void beginCategory(Class<E> type){
		
	}

	// добавить строки для данной позиции
	public void flush(String refdesName, int initRefDes, AbstractType currentType, int amount) {
		
		// столбец 2
		boolean isInlineSpec = currentType instanceof DType || 
							   currentType instanceof VType || 
							   currentType instanceof XType || 
							   currentType instanceof GType || 
							   currentType instanceof TType;
		
		String spec = isInlineSpec? currentType.getSpec() : "";
		
		String metal = (currentType instanceof CType)? (((CType) currentType).getMetal()? "луженые" : "") : "";
		
		
		
		
		
		boolean isInitName = currentType instanceof GType || 
							 currentType instanceof HType ||
							 currentType instanceof LType || 
							 currentType instanceof TType;
		
		String initName = isInitName? currentType.getTypeName() : "";
		
		
		
		
		
		
		
		
		String name = initName + " " + currentType.toString() + "   " + spec + " " + metal;
		
		
		
		
		
		
		
		
		
		// столбец 3
		String amnt = String.valueOf(amount);
		
		
		// столбец 4
		String note = currentType.toNote();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		rows.add(new CLRow(refdes, name, amnt, note));
	}




	@Override
	public String toString() {
		return "ComponentList [item=" + item + ", unit=" + unit + ", title=" + title + "]";
	}
	
	

	

	
	

	
	
	
}

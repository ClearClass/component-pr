package lib.clearclass.logic.parser;

import java.util.HashMap;
import java.util.Set;

import lib.clearclass.entity.*;

public class Parser {
	/** Добавляет и/или возвращает объект из множества */
	public static <E> E addGet(E ob, Set<E> set) {
		if(set.add(ob)) return ob;
		int hashCode = ob.hashCode();
		for(E obs : set)
			if(obs.hashCode()==hashCode && obs.equals(ob)) return obs;
		throw new IllegalStateException();
	}
	
	private Set<AbstractSpec> specs; 
	private Set<AbstractType> types;
	
	private String item;
	private String unit;
	
	private HashMap<Character, String> dims = new HashMap<>();
	private HashMap<String, String> diels = new HashMap<>();
	private HashMap<String, String> cases = new HashMap<>();
	
	public Parser(Set<AbstractSpec> specs, Set<AbstractType> types){
		this.specs = specs;
		this.types = types;
		
		// определить таблицы преобразования
		dims.put('p', "п");
		dims.put('n', "н");
		dims.put('u', "мк");
		dims.put('m', "м");
		dims.put('k', "к"); // англ - рус
		dims.put('K', "к"); // англ - рус
		dims.put('к', "к"); // рус  - рус
		dims.put('К', "к"); // рус  - рус
		dims.put('M', "М"); // англ - рус
		dims.put('М', "М"); // рус  - рус
		diels.put("MPO", "МП0");
		diels.put("MP",  "МП0");
		diels.put("N3",  "Н30");
		diels.put("N30", "Н30");
		diels.put("N90", "Н90");
		
		
//		'NP0', 'C0G', 'X7R', 'X5R', 'Z5U', 'Y5V'
		
		cases.put("C0805", "2012М");
		cases.put("C1206", "3216М");
		cases.put("C2220", "5750М");
		cases.put("А", "A"); // рус  - англ
		cases.put("В", "B"); // рус  - англ
		cases.put("С", "C"); // рус  - англ
		cases.put("Е", "E"); // рус  - англ
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public AbstractComponent<? extends AbstractType> get(CompInst сompInst){
		String refdesSt = сompInst.getRefdes();       //refdes
		String typeSt   = сompInst.getOriginalName(); //originalName
		String valueSt  = сompInst.getCompValue();    //compValue
		String specSt   = сompInst.getSpec();         //spec
		String manufSt  = сompInst.getManufacturer(); //manufact
		String markSt   = сompInst.getMark();         //mark
		
		String spec = (specSt.isEmpty() || specSt.equals("{ТУ}"))? null : specSt.replace('_', 'Я');
		spec = manufSt.isEmpty()? spec : manufSt; // переопределяем строкой manufact
		
		if(refdesSt.startsWith("A")){
			int refdes = Integer.parseInt(refdesSt.substring(1));
			AType aType = new AType(typeSt, markSt, spec);
			return new A(item, unit, refdes, (AType)addGet(aType, types));
		}
		
		if(refdesSt.startsWith("C")){ // C1       CC0603               0.1u_25V_10_X7R
									  // C26      CC_TAJ(CASE_D)       22u_35V_20
									  // C41      B41851_10X20         100u_100V_20        Epcos
			// заполняем.. REFDES
			int refdes = Integer.parseInt(refdesSt.substring(1));
			
			// заполняем.. ТИП И КОРПУС
			String type;
			String size;
			
			if(typeSt.matches("CC[0-9][0-9][0-9][0-9]")){
				type = "CC";
				size = typeSt.substring(2);
			} else if(typeSt.startsWith("CC_TAJ")){
				type = "CC_TAJ";
				size = String.valueOf(typeSt.charAt(12));
			} else if(typeSt.startsWith("B41851")){
				type = "B41851";
				size = typeSt.substring(7).replace("X", "x");
			} else { // .. для отечественных элементов
				// C1 K10-69_(C0805)       0.1u_25v_+80-20 АЖЯР.673511.002 ТУ  
				// C4 K53-68_(B)           10u_10v_20      АЖ_Р.673546.007 ТУ  
				// C5 K10-47MA-50B-4.7     4.7u_50v_N90    ОЖО.460.174-МТУ     
				//......
				// C6 К10-43В              7500p_50V_1     ОЖО.460.165 ТУ (луже
				
				// индекс разделителя "тип-корпус"
				int index = (typeSt.indexOf("_"))==-1? typeSt.indexOf("-", typeSt.indexOf("-")+1) : typeSt.indexOf("_");
				// в русскоязычных наименованиях - только русские символы!
				type = typeSt.substring(0, index).replace('K', 'К').replace('M', 'М').replace('A', 'а');
				type = type.equals("К10-69") ? "К10-69в" : type;
				size = typeSt.charAt(index+1)=='(' ? typeSt.substring(index+2, typeSt.length()-1) : null;
				size = cases.getOrDefault(size, size);
			}
			
			// ЕМКОСТЬ, РАЗМЕРНОСТЬ, НАПРЯЖЕНИЕ..
			String[] msv = valueSt.split("_");
			// ..
			Double value = Double.parseDouble(msv[0].substring(0, msv[0].length()-1));
			String dim = dims.get(msv[0].charAt(msv[0].length()-1));
			Integer volt = Integer.parseInt(msv[1].substring(0, msv[1].length()-1));
			
			// ДОПУСК (м.б. ==null), ДИЭЛЕКТРИК..
			Integer toler = null;
			String diel;
			if(msv.length==4){
				toler = msv[2].equals("+80-20")? 8020 : Integer.parseInt(msv[2]);
				diel = diels.getOrDefault(msv[3], msv[3]);
//				if((diel = diels.get(msv[3]))==null)
//					throw new RuntimeException("dielectric undefined: " + msv[3]);
			} else if(msv.length==3){
				if((diel = diels.get(msv[2]))==null){ // если diel не определен - это или допуск, или неизвестный diel
					toler = msv[2].equals("+80-20")? 8020 : Integer.parseInt(msv[2]); // except, если неизвестный diel
					if(type.equals("К10-69в") && toler==8020)
						diel = "Н90";
					else if(type.equals("К53-68") || type.equals("CC_TAJ") || type.equals("B41851"))
						diel = "ЭЛТ";
					else throw new RuntimeException("dielectric undefined for: " + typeSt + " " + valueSt);
				}
			} else throw new IllegalArgumentException("Illegal valueCol: " + msv.length + ", for: " + valueSt);
			
			Boolean metal = type.equals("К10-69в")? true : false;
			CSpec cSpec = (CSpec)addGet(new CSpec(type, spec), specs);
			CType cType = new CType(cSpec, diel, volt, size, value, dim, toler, metal);
			return new C(item, unit, refdes, (CType)addGet(cType, types));
		}
		
		if(refdesSt.startsWith("D")){
			int refdes = Integer.parseInt(refdesSt.substring(2));
			D.Type dtype = (refdesSt.charAt(1)=='A')? D.Type.A : D.Type.D;
			
			// ---------------------------------------------------------------------------------------
			if(!valueSt.isEmpty())
				typeSt = valueSt;
			else {
				typeSt = typeSt.endsWith("_(DA)")? typeSt.substring(0, typeSt.length()-5) : typeSt;
				typeSt = typeSt.startsWith("1303")? typeSt.replace('_', ',').replace(",0", "") : typeSt;
				typeSt = typeSt.endsWith("-")? typeSt.substring(0, typeSt.length()-1) : typeSt;
			
				if(item.equals("364") || typeSt.equals("5559IN28U"))
					typeSt = translate(typeSt);
			}
			// ---------------------------------------------------------------------------------------
			
			DType dType = new DType(typeSt, spec);
			return new D(item, unit, dtype, refdes, (DType)addGet(dType, types));
		}
		
		if(refdesSt.startsWith("F")){ // F1  B1250T   1A
			// ток игнорируется...
			int refdes = Integer.parseInt(refdesSt.substring(1));
			FType fType = new FType(typeSt, spec);
			return new F(item, unit, refdes, (FType)addGet(fType, types));
		}
		
		if(refdesSt.startsWith("G")){ //G1       KXO-V97_(3.3V)       40M
			int refdes = Integer.parseInt(refdesSt.substring(1));
			String type = typeSt.replace("_(3.3V)", "");
			String freqs = valueSt.replace('М', 'M'); // заменяем русскую на английскую
			GType gType = new GType(type, freqs, spec);
			return new G(item, unit, refdes, (GType)addGet(gType, types));
		}
		
		if(refdesSt.startsWith("H")){ // HL1      L-603G
			int refdes = Integer.parseInt(refdesSt.substring(2));
			H.Type htype = (refdesSt.charAt(1)=='L')? H.Type.L : null;
			HType hType = new HType(typeSt, spec);
			return new H(item, unit, htype, refdes, (HType)addGet(hType, types));
		}
		
		if(refdesSt.startsWith("L")){
			int refdes = Integer.parseInt(refdesSt.substring(1));
			if(typeSt.startsWith("ДФ")){
				String type = typeSt.replace('_', ',');
				LType lType = new LType(type, spec);
				return new L(item, unit, refdes, (LType)addGet(lType, types));
			}
			if(typeSt.startsWith("ДМ") || 
			   typeSt.equals("B82464G4") ||
			   typeSt.equals("XAL4030")) {               // L1   ДМ-0,4-80   80u_5
				                                         // L3   B82464G4    33u_20
				                                         // L5   XAL4030     4.7u_20
				String type = typeSt.startsWith("ДМ")?
						typeSt.substring(0, typeSt.indexOf('-', typeSt.indexOf('-')+1)) : typeSt;
				String[] msv = valueSt.split("_");
				Double value = Double.parseDouble(msv[0].substring(0, msv[0].length()-1));
				String dim = dims.get(msv[0].charAt(msv[0].length()-1));
				Integer toler = Integer.parseInt(msv[1]);
				LType lType = new LType(type, value, dim, toler, spec);
				return new L(item, unit, refdes, (LType)addGet(lType, types));
			}
			throw new IllegalArgumentException("unknown inductor type: " + typeSt);
		}
		
		if(refdesSt.startsWith("R")){ // R1       CR0603               4.7k_5
			                          // R48      CR0805               100_5
			int refdes = Integer.parseInt(refdesSt.substring(1));

			String type;
			String size;
			Double power;
			
			if(typeSt.startsWith("R1-12")){
				type = "Р1-12";
				int p_ = typeSt.lastIndexOf('_'); // разделитель мощность - корпус
				if(p_==-1){
					size = null;
					p_ = typeSt.length();
				} else {
					size = typeSt.substring(p_+2, typeSt.length()-1);
					size = cases.getOrDefault(size, size);
				}
				power = Double.parseDouble(typeSt.substring(6, p_).replace('_', '.'));
			} else if(typeSt.startsWith("СП5-2ВБ")){
				type = "СП5-2ВБ";
				size = null;
				power = 0.5;	
			} else if(typeSt.startsWith("CR")){
				type = "CR";
				size = typeSt.substring(2);
				power = null;	
			} else 
				throw new IllegalArgumentException("unknown resistor type: " + typeSt);
			
			String[] msv = valueSt.replace(',', '.').split("_");
			char leftLastChar = msv[0].charAt(msv[0].length()-1); // цифра или размерность
			
			Double value;
			String dim;
			if(dims.containsKey(leftLastChar)){
				value = Double.parseDouble(msv[0].substring(0, msv[0].length()-1));
				dim = dims.get(leftLastChar);
			} else {
				value = Double.parseDouble(msv[0]);
				dim = "";
			}
			Integer toler = Integer.parseInt(msv[1]);
			
			RSpec rSpec = (RSpec)addGet(new RSpec(type, spec), specs);
			RType rType = new RType(rSpec, power, size, value, dim, toler);
			return new R(item, unit, refdes, (RType)addGet(rType, types));
		}
		
		if(refdesSt.startsWith("S")){ // S1       КНОПКА_PSM-1-1-0     PSM-1-1-0   
			int refdes = Integer.parseInt(refdesSt.substring(1));
			SType sType = new SType(typeSt.replace("КНОПКА_", ""), spec);
			return new S(item, unit, refdes, (SType)addGet(sType, types));
			
		}
		
		// T U V X Z +++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		if(refdesSt.startsWith("T")){ // T1   B82721A   1m_30
			int refdes = Integer.parseInt(refdesSt.substring(1));
			if(typeSt.equals("TIL6V")){
				TType tType = new TType("ТИЛ6В", spec);
				return new T(item, unit, refdes, (TType)addGet(tType, types));
			}
			if(typeSt.equals("TIM238")){
				TType tType = new TType("ТИМ-238", spec);
				return new T(item, unit, refdes, (TType)addGet(tType, types));
			}
			if(typeSt.equals("B82721A")){
				TType tType = new TType("B82721A", spec);
				return new T(item, unit, refdes, (TType)addGet(tType, types));
			}
			throw new IllegalArgumentException("unknown transformer type: " + typeSt);
		}

		if(refdesSt.startsWith("U")){ // U1       IRM-30-48  
			int refdes = Integer.parseInt(refdesSt.substring(1));
			if(typeSt.startsWith("МДМ"))
				typeSt = typeSt.replace('.', ',');
			UType uType = new UType(typeSt, spec);
			return new U(item, unit, refdes, (UType)addGet(uType, types));
		}
		
		if(refdesSt.startsWith("V")){
			int refdes = Integer.parseInt(refdesSt.substring(2));
			V.Type vtype = (refdesSt.charAt(1)=='D')? V.Type.D : V.Type.T;
			
			if(item.equals("364"))
				typeSt = typeSt.replace('A', 'А')
							   .replace('C', 'С')
							   .replace('T', 'Т')
							   .replace('O', 'О'); // англ - рус
			VType vType = new VType(typeSt, spec);
			return new V(item, unit, vtype, refdes, (VType)addGet(vType, types));
		}
		
		if(refdesSt.startsWith("X")){
			int refdes = Integer.parseInt(refdesSt.substring(2));
			X.Type xtype = (refdesSt.charAt(1)=='P')? X.Type.P : X.Type.S;
			
			XType.Type xtype4 = null;	
			if(typeSt.equals("КЛЕММА"))
				xtype4 = XType.Type.K;
			else if(typeSt.equals("ГНЕЗДО"))
				xtype4 = XType.Type.G;
			else if(xtype == X.Type.P)
				xtype4 = XType.Type.P;
			else if(xtype == X.Type.S)
				xtype4 = XType.Type.S;

			if(typeSt.startsWith("VILKA") || typeSt.startsWith("KOLODKA")
										  || typeSt.equals("КЛЕММА")
					                      || typeSt.equals("ГНЕЗДО"))
				typeSt = markSt;
			else if(typeSt.startsWith("RSG-")){
				String n = typeSt.replace("RSG-", "")
						         .replace("_(РОЗЕТКА)", "")
						         .replace("_(ВИЛКА)", "");
				
				if(xtype==X.Type.P)
					typeSt = "РСГ" + n + "ТВ";
				else
					typeSt = "РС" + n + "ТВ с кожухом";
			}
			
			XType xType = new XType(typeSt, xtype4, spec);
			return new X(item, unit, xtype, refdes, (XType)addGet(xType, types));				
		}
		
		if(refdesSt.startsWith("Z")){
			int refdes = Integer.parseInt(refdesSt.substring(1));
			if(typeSt.equals("NFL18ST"))
				typeSt = valueSt;
				
			ZType zType = new ZType(typeSt, spec);
			return new Z(item, unit, refdes, (ZType)addGet(zType, types));
//			throw new IllegalArgumentException("unknown z-filter type: " + typeSt);
		}
		
		throw new IllegalArgumentException("Illegal start symbol in refdesCol: " + refdesSt);
	}

	public static boolean isForeignChip(String type, String value, String spec, String manuf){
		if(!spec.isEmpty())
			return false;
		if(!manuf.isEmpty())
			return true;

		return false;
	}
	
	public static String translate(String st){
		return st.replace('N', 'Н')
				 .replace('A', 'А')
				 .replace('X', 'Х')
				 .replace('C', 'С')
				 .replace('T', 'Т')
				 .replace('I', 'И')
				 .replace('U', 'У');
	}
}
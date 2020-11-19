package lib.clearclass.logic;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import lib.clearclass.logic.parser.AccelComponentReader;
import lib.clearclass.logic.parser.CompInst;
import lib.clearclass.logic.parser.ComponentReader;
import lib.clearclass.logic.parser.Parser;
import lib.clearclass.model.VerifiedFile;

import static lib.clearclass.model.VerifiedFile.VerifiedRow.Status;

public class Verifier {
	/* принимает бинарный файл, для которого уже существует связанный ascii-файл */
	public static VerifiedFile perform(File schBinFile, String cc_manuf){
		File asciiLinkedFile = new File(schBinFile.getPath()+ ".ascii");
		if(!asciiLinkedFile.exists()) throw new RuntimeException("file not exists: " + asciiLinkedFile);
		VerifiedFile vf = new VerifiedFile(schBinFile.getName()); // объект результатов проверки
		ComponentReader reader = new AccelComponentReader(asciiLinkedFile);
		
		// проверка децимального номера ..
		String decml = reader.getDecml();
		if(decml.isEmpty())
			vf.setEmptyDecmlErr();
		else if(!decml.matches("[А-Я]+\\.[0-9]+\\.[0-9]+")) // УЖБИ.468157.031    УТП.01.001
			vf.setDecmlNotMatchesWarn(decml);
				
		// проверка наименования ..
		String title = reader.getTitle();
		if(title.isEmpty())
			vf.setEmptyTitleWarn();
		
		// проверка компонентов .. 
		while(reader.hasNext()){
			CompInst compInst = reader.next();
			
			String refdes= compInst.getRefdes();
			String type  = compInst.getOriginalName();
			String value = compInst.getCompValue();
			String spec  = compInst.getSpec();
			String manuf = compInst.getManufacturer();
			String mark  = compInst.getMark();
			
			if(refdes.matches("A[1-9]")){
				// проверка атрибута "Обозначение"
				if(mark.isEmpty()){
					vf.add(refdes, type, mark, manuf, Status.Err, "атрибут \"Обозначение\" не установлен");
					continue;
				}
				// проверка атрибута "Manufacturer"
				if(manuf.isEmpty()){
					vf.add(refdes, type, mark, manuf, Status.Warn, "атрибут \"Manufacturer\" не установлен");
					continue;
				}
				continue;
			}
			
			if(refdes.matches("C[1-9][0-9]*")){
				// синтаксическая корректность атрибута Value /0.1u_25V_10_X7R/
				boolean isCeramic = type.matches("CC[0-9][0-9][0-9][0-9]");
				String valTemplate = isCeramic? 
						"[0-9]*\\.?[0-9]+[up]_[0-9]+V_[0-9]+_[XCN][70P][RG0]" :
						"[0-9]*\\.?[0-9]+[up]_[0-9]+V_[0-9]+";
				
				if(value.isEmpty()){
					vf.add(refdes, type, value, manuf, Status.Err, "незаполненная строка Value");
					continue;
				}
				
				if(!value.matches(valTemplate)){
					vf.add(refdes, type, value, manuf, Status.Err, "некорректная строка Value");
					continue;
				}

				// проверка на соответствие стандартным рядам
				double val = Double.parseDouble(value.substring(0, value.indexOf('_')-1));
				int tol = Integer.parseInt(value.split("_")[2]);
				
				if(!inSeries(val, tol)){
					vf.add(refdes, type, mark, manuf, Status.Err, "несоответствие номинала стандартному ряду");
					continue;
				}
				
				// проверка наличия типономинала у заданного производителя
				if(isCeramic){
					String size = type.substring(2);
					String[] ms = value.split("_");
					String dim = ms[0].substring(ms[0].length()-1);
					Integer volt = Integer.parseInt(ms[1].replace("V", ""));
					String diel = ms[3];
					String name = OrderCodes.getFor(cc_manuf, val, dim, tol, diel, size, volt);
				
					if(name==null){ // проверка у остальных производителей
						Set<String> manufs = new HashSet<>();
						manufs.add("kemet");
						manufs.add("murata");
						manufs.add("avx");
						manufs.remove(cc_manuf);
						
						StringBuilder manSt = new StringBuilder();
						for (String man : manufs) {
							String name_ = OrderCodes.getFor(man, val, dim, tol, diel, size, volt);
							if(name_!=null){
								if(manSt.length()==0)
									manSt.append("; доступен только " + man);
								else 
									manSt.append(", " + man);
							}
						}
						vf.add(refdes, type, value, manuf, Status.Warn, cc_manuf + ": не найдено" + manSt);
						continue;
					}
				}
				continue;
			}

			if(refdes.matches("D[AD][1-9][0-9]*")){
				if(value.isEmpty()){
					String msg = "атрибут value не заполнен; будет использовано имя type: " + type;
					vf.add(refdes, type, value, spec+manuf, Status.Warn, msg);
					continue;
				}

				if(hasCyrillAndLatin(value)){
					String msg = "строка value содержит русские и латинские символы";
					vf.add(refdes, type, value, spec+manuf, Status.Warn, msg);
					continue;
				}
				
				if(manuf.isEmpty()){
					String name = Parser.translate(value.isEmpty()? type : value);
					String spec_ = Mop.getSpec(name);
					if(spec.isEmpty() && spec_==null) // оба поля пустые
						vf.add(refdes, type, value, spec+manuf, Status.Err, "не найдены ТУ/не указан Manufacturer");
					else if (!spec.isEmpty() && spec_!=null && !spec.equals(spec_))
						vf.add(refdes, type, value, spec+manuf, Status.Warn, "введенные ТУ не совпадают с ТУ в базе: " + spec_);
					continue;
				}
				continue;
			}
			
			if(refdes.matches("F[1-9][0-9]*")){
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				continue;
			}
			
			if(refdes.matches("G[1-9][0-9]*")){
				
				continue;
			}
			
			if(refdes.matches("HL[1-9][0-9]*")){
				
				continue;
			}
			
			if(refdes.matches("L[1-9][0-9]*")){
				
				continue;
			}
			
			if(refdes.matches("R[1-9][0-9]*")){
				
				continue;
			}
			
			if(refdes.matches("S[1-9][0-9]*")){
				
				continue;
			}
			
			if(refdes.matches("T[1-9][0-9]*")){
				
				continue;
			}
			
			if(refdes.matches("U[1-9][0-9]*")){
				
				continue;
			}

			if(refdes.matches("V[DT][1-9][0-9]*")){
				
				continue;
			}
			
			if(refdes.matches("X[PS][1-9][0-9]*")){
				
				continue;
			}
			
			if(refdes.matches("Z[1-9][0-9]*")){
				
				continue;
			}

			vf.add(refdes, type, mark, manuf, Status.Err, "неизвестный RefDes");
		}
		
		if(!vf.hasError()){
			FileMonitor.setAsVerified(schBinFile);
			System.out.println("файл верифицирован: " + schBinFile);
		}
			
		
		return vf;	
	}
	
	


	
	
	public static boolean inSeries(double val, int tol){
		
		return true;
	}

	public static boolean hasCyrillAndLatin(String st){
		return st.matches(".*[А-Я].*") && st.matches(".*[A-Z].*");
	}
	
	
	
}

package lib.clearclass.logic.services;

import java.util.ArrayList;
import java.util.List;

// разбиение строки с преобразованием в список строк
class Split {
	// добавить строку типономинала type в список names
	static void add_type(String type, List<String> names, int lenght) {
		if(type.length()<=lenght)
			names.add(type);
		else {
			int index;
			if(type.startsWith("К10-69в"))
				index = type.indexOf("М-") + 2;
			else if(type.startsWith("Р1-12"))
				index = type.indexOf("М-") + 2;
			else
				index = lenght;
			names.add(type.substring(0, index));
			names.add(type.substring(index));
		}
	}
	
	static List<String> doo(String type, int lenght){
		List<String> names = new ArrayList<>();
		add_type(type, names, lenght);
		return names;
	}
	
	static int num(String type, int lenght){
		return doo(type, lenght).size();
	}
	
	
	
	
	
	//-------------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
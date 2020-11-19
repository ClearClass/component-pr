package lib.clearclass.logic;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class Mop {
	private static JdbcTemplate jdbc = 
			new JdbcTemplate(new EmbeddedDatabaseBuilder()
								 .setType(EmbeddedDatabaseType.H2)
								 .addScript("classpath:sql/mop.sql").build());
	
	
	// замена 3 З, 0 O
	public static String getSpec(String type){
		
		
		
//		Object[] args = {"%"+type+"%"};
		Object[] args = {type};

		String sql = "SELECT DISTINCT spec FROM specs WHERE type LIKE ? AND num=1";
		

		 
		List<String> strLst  = jdbc.queryForList(sql, String.class, args);

			if ( strLst.isEmpty() ){
			  return "----------";
			}else if ( strLst.size() == 1 ) { // list contains exactly 1 element
			  return strLst.get(0);
			}else{  // list contains more than 1 elements
				return strLst.get(0);
				//your wish, you can either throw the exception or return 1st element.    
			}

		
		

	}
	
//	public static String getName(String type){

		
		
		
//		Object[] args = {"0603", 50, "U2J", 3900, 5};
//		return jdbc.queryForObject(sql, args, String.class);
		
		
//		return type;
//	}
}

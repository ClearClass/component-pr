package lib.clearclass.logic;

import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class OrderCodes {
	private static JdbcTemplate jdbc = 
			new JdbcTemplate(new EmbeddedDatabaseBuilder()
								 .setType(EmbeddedDatabaseType.H2)
								 .addScript("classpath:sql/kemet_comm.sql")
								 .addScript("classpath:sql/murata_grm.sql")
								 .addScript("classpath:sql/avx_general.sql")
								 .build());
	
	private static HashMap<Integer, Character> tolCodes = new HashMap<>();
	static {
		tolCodes.put(1, 'F');
		tolCodes.put(2, 'G');
		tolCodes.put(5, 'J');
		tolCodes.put(10, 'K');
		tolCodes.put(20, 'M');
		tolCodes.put(30, 'N');
		tolCodes.put(8020, 'Z');
	}
	
	private static int e(String dim){
		switch(dim) {
			case "п" : return -12;
			case "p" : return -12;
			case "н" : return -9;
			case "мк": return -6;
			case "u": return -6;
			case "м" : return -3;
			case ""  : return 0;
			case "к" : return 3;
			case "М" : return 6;
			default: throw new IllegalArgumentException(dim);
		}
	}
	
	private static Double cap2pF(Double value, String dim){
		return value*Math.pow(10, e(dim)+12);
	}
	
	private static String valCode(Integer val){
		// представление емкости "2 знака + порядок"
		String valSt = val.toString();
		return valSt.substring(0, 2) + valSt.substring(2).length();	
	}
	
	public static String forKemet(final Double value, final String dim, 
			final Integer tol, String diel, String size, Integer volt){
		
		// подготоваливаем данные для sql-запроса
		// ================================================================
		if(diel.equals("NP0"))
			diel = "C0G";
		// ----------------------------------------------------------------
		final Double val = cap2pF(value, dim); // фактическая емкость, пФ
		Double val_; // для запроса - переводим в pF или uF
		String dim_;
		if(val>=100_000){
			val_ = val/1000/1000;
			dim_="uF";
		} else {
			val_ = val;
			dim_="pF";
		}
		// ----------------------------------------------------------------
		String tol_ = "%"+ tolCodes.get(tol) +"%";
		// ----------------------------------------------------------------
		HashMap<Double, Character> voltCodes = new HashMap<>();
		voltCodes.put(100d, '1');
		voltCodes.put(200d, '2');
		voltCodes.put(25d,  '3');
		voltCodes.put(16d,  '4');
		voltCodes.put(50d,  '5');
		voltCodes.put(35d,  '6');
		voltCodes.put(4d,   '7');
		voltCodes.put(10d,  '8');
		voltCodes.put(6.3,  '9');
		voltCodes.put(250d, 'A');
		
		String volt_ = "%"+ voltCodes.get((double)volt) +"%";
		// ================================================================
		String sql = "SELECT COUNT(*) FROM kemet "
				   + "WHERE diel=? AND val=? AND dim=? "
				   + "AND tol LIKE ? AND size=? AND volt LIKE ?";
		Object[] args = {diel, val_, dim_, tol_, size, volt_};
		int num = jdbc.queryForObject(sql, args, Integer.class);
		
		if(num==1){
			String valCode;
			if(val>=0.5 && val<=0.99)
				valCode = String.format("%1.2f8", val).substring(2); // пример: 0.7 пФ = 708
			else if(val>=1.0 && val<=9.9)
				valCode = val.toString().replace(".", "") + "9";     // пример: 2.7 пФ = 279
			else valCode = valCode(val.intValue());
			
			HashMap<String, Character> dielCodes = new HashMap<>();
			dielCodes.put("NP0", 'G');
			dielCodes.put("C0G", 'G');
			dielCodes.put("X7R", 'R');
			dielCodes.put("X5R", 'P');
			dielCodes.put("Z5U", 'U');
			dielCodes.put("Y5V", 'V');
			
			return "C" + size + "C" + valCode + tolCodes.get(tol) 
			+ voltCodes.get((double)volt) + dielCodes.get(diel) + "AC";
		}
	
		if(num==0) return null;
		throw new IllegalStateException("num of rows: " + num);
	}
	
	public static String forMurata(final Double value, final String dim, 
			final Integer tol, String diel, String size, Integer volt){
		
		// подготоваливаем данные для sql-запроса
		// ================================================================
		if(diel.equals("NP0"))
			diel = "C0G";
		// ----------------------------------------------------------------
		final Double val = cap2pF(value, dim); // фактическая емкость, пФ
		Double val_; // для запроса - переводим в pF или uF
		String dim_;
		if(val>=100_000){
			val_ = val/1000/1000;
			dim_="uF";
		} else {
			val_ = val;
			dim_="pF";
		}
		// ----------------------------------------------------------------
		String sql = "SELECT partnum FROM murata "
				   + "WHERE diel=? AND val=? AND dim=? "
				   + "AND tol=? AND size=? AND volt=?";
		Object[] args = {diel, val_, dim_, tol, size, volt};
		List<String> partnums = jdbc.queryForList(sql, args, String.class);
		if(partnums.isEmpty())
			return null;
		else
			return partnums.get(0);
	}
	
	public static String forAVX(final Double value, final String dim, 
			final Integer tol, String diel, String size, Integer volt){
		// подготоваливаем данные для sql-запроса
		// ================================================================
		if(diel.equals("C0G"))
			diel = "NP0";
		// ----------------------------------------------------------------
		final Double val = cap2pF(value, dim); // фактическая емкость, пФ
		Double val_; // для запроса - переводим в pF или uF
		String dim_;
		if(val>=100_000){
			val_ = val/1000/1000;
			dim_="uF";
		} else {
			val_ = val;
			dim_="pF";
		}
		// ----------------------------------------------------------------
		String sql = "SELECT name FROM avx "
				   + "WHERE diel=? AND val=? AND dim=? "
				   + "AND tol=? AND size=? AND volt=?";
		Object[] args = {diel, val_, dim_, tol, size, volt};
		List<String> names = jdbc.queryForList(sql, args, String.class);
		if(names.isEmpty())
			return null;
		else
			return names.get(0);
	}
	
	public static String forTantal(final Double value, final String dim, 
			final Integer tol, String diel, String size, Integer volt){
		
		Double val = cap2pF(value, dim);
		
		HashMap<Double, String> voltCodes = new HashMap<>();
		voltCodes.put(2.5, "2R5");
		voltCodes.put(3d,  "003");
		voltCodes.put(4d,  "004");
		voltCodes.put(6.3, "006");
		voltCodes.put(10d, "010");
		voltCodes.put(16d, "016");
		voltCodes.put(20d, "020");
		voltCodes.put(25d, "025");
		voltCodes.put(35d, "035");
		voltCodes.put(50d, "050");
		return "T491" + size + valCode(val.intValue()) + 
				tolCodes.get(tol) + voltCodes.get((double)volt) + "AH";
	}
		
	public static String forEpcos(final Double value, final String dim, 
			final Integer tol, String diel, String size, Integer volt){
		
		Double val = cap2pF(value, dim);
		
		HashMap<Double, Integer> voltCodes = new HashMap<>();
		voltCodes.put(3d, 1);
		voltCodes.put(6.3, 2);
		voltCodes.put(10d, 3);
		voltCodes.put(12d, 3);
		voltCodes.put(15d, 4);
		voltCodes.put(16d, 4);
		voltCodes.put(25d, 5);
		voltCodes.put(30d, 6);
		voltCodes.put(50d, 6);
		voltCodes.put(35d, 7);
		voltCodes.put(40d, 7);
		voltCodes.put(63d, 8);
		voltCodes.put(70d, 8);
		voltCodes.put(100d, 9);                       
		// пример: 1500u 16V 10x20  B41851 F 4158M000
		char c = (volt==100 && value==100 && dim.equals("мк"))? 'A' : '_';
		return "B41851" + c + voltCodes.get((double)volt) + 
				valCode(val.intValue()) + tolCodes.get(tol) + "000";
		
	}
	
	
	public static String forBourns(String size, Double value, String dim, Integer toler){
		Double val = value*(Math.pow(10, e(dim)));
		char tol;
		char TCR;
		String valCode;
		if(toler==1){
			// --- tol
			tol = 'F';
			// --- TCR
			if(val>=10 && val<=1E6)
				TCR = 'X';
			else if(val>1E6 && val<=10E6)
				TCR = 'W';
			else 
				throw new RuntimeException();
			// --- valSt
			if(val<100)
				valCode = val.toString().replace('.', 'R');
			else {
				String valSt = String.valueOf(val.intValue());
				valCode = valSt.substring(0, 3) + valSt.substring(3).length();
			}
		} else if(toler==5){
			// --- tol
			tol = 'J';
			// --- TCR
			if(val<=9.1)
				TCR = '/';
			else if(val>=10 && val<=10E6)
				TCR = 'W';
			else if(val>10E6 && val<=20E6)
				TCR = 'Z';
			else 
				throw new RuntimeException();
			// --- valSt
			if(val<1)
				valCode = "000";
			else if(val<10)
				valCode = val.toString().replace('.', 'R');
			else {
				String intSt = String.valueOf(val.intValue());
				valCode = intSt.substring(0, 2) + intSt.substring(2).length();
			}
		} else throw new RuntimeException();
		return "CR" + size + "-" + tol + TCR + "-" + valCode + "E";	
	}
	
	
	
	
	
	
	
	
	
	
	public static String getFor(String cc_manuf, Double value, String dim, 
			Integer tol, String diel, String size, Integer volt){
		if(cc_manuf.equalsIgnoreCase("kemet"))
			return forKemet(value, dim, tol, diel, size, volt);
		if(cc_manuf.equalsIgnoreCase("murata"))
			return forMurata(value, dim, tol, diel, size, volt);
		if(cc_manuf.equalsIgnoreCase("avx"))
			return forAVX(value, dim, tol, diel, size, volt);
		throw new IllegalArgumentException(cc_manuf);
	}
	
	public String getForMurata(String  size, Double value, String dim, Integer toler, String diel, Integer volt){
		String sql = "SELECT partnumber FROM murata_cap "
				   + "WHERE size=? AND vdc=? AND tc=? AND cap=? AND tol<=?";
		
		
		String val = null;
		
		Object[] args = {size, volt, diel, val, toler};

		 
		List<String> partnumbers  = jdbc.queryForList(sql, String.class, args);

			if ( partnumbers.isEmpty() ){
			  return "(не найдено)";
			}else if ( partnumbers.size() == 1 ) { // list contains exactly 1 element
			  return partnumbers.get(0);
			}else{  // list contains more than 1 elements
				return partnumbers.get(0);
				//your wish, you can either throw the exception or return 1st element.    
			}
	}

}

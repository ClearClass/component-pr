package lib.clearclass.logic;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import lib.clearclass.model.Item;

public class FileMonitor {
	private static DriverManagerDataSource dataSource = new DriverManagerDataSource();
	static {
	    dataSource.setDriverClassName("org.h2.Driver");
	    dataSource.setUsername("nadya");
	    dataSource.setPassword("riot");
	}	
	private static JdbcTemplate jdbc = new JdbcTemplate(dataSource);

	/** 1. считывает и возвращает список всех имеющихся .sch по всем изделиям
	  * 2. создает для каждого файла .ascii
	  * 3. делает запись в БД (verified.mv.db) - **/
	public static List<Item> scan(){
		// создаем директорию Items, если она отсутствует
		File rootItems = new File("Items");
		if(!rootItems.exists()) 
			   rootItems.mkdir();
		//
		// результирующий список - каждой папке изделия сопоставлен объект Item
		return Arrays.stream(rootItems.listFiles())
				.map(itemDir->new Item(itemDir.getName(), scanDir(itemDir, "Units"), scanDir(itemDir, "Control")))
				.collect(Collectors.toList());
	}
	
	// возвращает список имен всех .sch файлов в директории Units/Control
	public static List<String> scanDir(File itemDir, String dir){
		File dir_sch = new File(itemDir.getPath() + "/" + dir + "/sch");
		FilenameFilter schBin = (dir_, name)-> name.substring(name.length()-4).equalsIgnoreCase(".sch")? true : false;
		
		for(File schBinFile : dir_sch.listFiles(schBin)) {
			Date actualDate = new Date(schBinFile.lastModified());
			Date savedDate = getSavedDate(schBinFile);
			if(!actualDate.equals(savedDate)){
				convert2ascii(schBinFile); // rewrite asciiLinkedFile if exists
				updateSavedRow(schBinFile);
			}
		}
		return Arrays.asList(dir_sch.list(schBin));
	}
	
	public static Date getSavedDate(File schBinFile) {
							 // аргумент - регулярное выражение \\
							 // /Items/УТП/Control/sch/BLOCK_SVK.sch
		String[] pathParts = schBinFile.getPath().split("\\\\");
		String item = pathParts[1];
		dataSource.setUrl("jdbc:h2:./Items/" + item + "/verified");
		String dir = pathParts[2];
		String filename = schBinFile.getName();
		try { // запрос по первичному ключу: возвращает 1 или 0 значений
			return jdbc.queryForObject("SELECT modified FROM files WHERE dir=? AND ext ='sch' AND filename=?", Date.class, dir, filename);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	public static void convert2ascii(File schBinFile) {
		String outputFile = schBinFile.getAbsolutePath() + ".ascii";
		String[] cmd = {"C:/Program Files/P-CAD 2006/Sch.exe", "/A", 
				schBinFile.getAbsolutePath(), outputFile};
		try {
			Process conv = Runtime.getRuntime().exec(cmd);
			conv.waitFor();
		} catch (IOException | InterruptedException e) {
			System.err.println("pcad convert error: " + schBinFile);
		}
	}
	
	public static void updateSavedRow(File schBinFile) {
		if(!schBinFile.exists()){
			System.err.println(schBinFile + " not exists!");
			return;
		}
		String[] pathParts = schBinFile.getPath().split("\\\\");
		String item = pathParts[1];
		dataSource.setUrl("jdbc:h2:./Items/" + item + "/verified;mode=MySQL");
		String dir = pathParts[2];
		String filename = schBinFile.getName();
		Date modified = new Date(schBinFile.lastModified());	
	    jdbc.update("INSERT INTO files VALUES(?, 'sch', ?, ?, 'false') ON DUPLICATE KEY UPDATE modified=?, verified='false'", dir, filename, modified, modified);
	}

	public static void setAsVerified(File schBinFile) {
		String[] pathParts = schBinFile.getPath().split("\\\\");
		String item = pathParts[1];
		dataSource.setUrl("jdbc:h2:./Items/" + item + "/verified");
		String dir = pathParts[2];
		String filename = schBinFile.getName();
	    jdbc.update("UPDATE files SET verified='true' WHERE dir=? AND ext ='sch' AND filename=?", dir, filename);
	}
	
	// dir = Control/Units
	public static Stream<File> getVerifiedFiles(String item, String dir) {
		dataSource.setUrl("jdbc:h2:./Items/" + item + "/verified");
		return jdbc.queryForList("SELECT filename FROM files WHERE dir=? AND verified='true'", String.class, dir)
				.stream().map(filename->new File("Items/" + item + "/" + dir + "/sch/" + filename + ".ascii"));
	}
	
	
	
	public static void viewDb(String item){
		dataSource.setUrl("jdbc:h2:./Items/" + item + "/verified");
		List<Map<String, Object>> list = jdbc.queryForList("SELECT * FROM files ORDER BY dir, filename");
		
		System.out.println("item dir: " + item);
		System.out.println();
		System.out.println(String.format("%-10s %-5s %-30s %-25s %-6s", 
				"dir", "ext", "filename", "modified", "verified"));
		System.out.println(String.format("%-10s %-5s %-30s %-25s %-6s", 
				"----------", "-----", "------------------------------", "-------------------------", "--------"));
		for (Map<String, Object> map : list)
			System.out.println(String.format("%-10s %-5s %-30s %-25s %-6s", 
					map.get("dir"), map.get("ext"), map.get("filename"), map.get("modified"), map.get("verified")));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}

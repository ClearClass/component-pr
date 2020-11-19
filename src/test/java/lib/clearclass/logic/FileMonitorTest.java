package lib.clearclass.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileMonitorTest {
	File schBinFile = new File("Items/УТП/Control/sch/BLOCK_UTP.sch");

//	@Test
	public void getSavedDateTest() {
		FileMonitor.updateSavedRow(schBinFile);
		Date modified = FileMonitor.getSavedDate(schBinFile);
		
		
		System.out.println(modified);

  	
//		FileMonitor.updateSavedRow(file);
		
		
		
		
	}
	
	
	@Test // convert2ascii()
	public void test12() {

		FileMonitor.scan();
		FileMonitor.viewDb("УТП");
//		FileMonitor.setAsVerified(schBinFile);
		
//		FileMonitor.getVerifiedFiles("УТП", "Control").forEach(System.out::println);
	}
	
	
	
	
	
	
	
//	@Test // convert2ascii()
	public void test1() {

	}
	
//	@Test // updateSavedDate() и getSavedDate()
	public void test2() {
		
		
		FileMonitor.updateSavedRow(schBinFile);
		
		System.out.println(schBinFile.getPath());
		
		String[] pathParts = schBinFile.getPath().split("\\\\");
		
		System.out.println("qq "+Arrays.toString(pathParts));
		
//		assertEquals(new Date(file.lastModified()), FileMonitor.getSavedDate(file));
		
//		System.out.println(FileMonitor.getSavedDate(file));
		
		FileMonitor.setAsVerified(schBinFile);
		
		FileMonitor.getVerifiedFiles("УТП", "Control").forEach(System.out::println);
		
	}
	
	
	
//	scan()
//	scanDir
//
//
//
//
//
//	setAsVerified
//	getVerifiedFiles
	
	

//	@Test
	public void scanDirTest() {
		
		List<String> schFiles = FileMonitor.scanDir(new File("Items/УТП"), "Control");
		

		schFiles.stream().forEach(System.out::println);
		
		
		
		
	
	
//		String[] files = FileMonitor.scanDir("Items/УТП", "Units");
//		
		

  	
		
	}
	

}
















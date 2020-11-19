package lib.clearclass.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import lib.clearclass.model.VerifiedFile;
import lib.clearclass.model.VerifiedFile.VerifiedRow;
import lib.clearclass.model.VerifiedFile.VerifiedRow.Status;

public class VerifierTest {
	
	@Test
	public void performTest() {
		
		File schBinFile = new File("Items/УТП/Control/sch/BLOCK_UTP.sch");

		VerifiedFile vf = Verifier.perform(schBinFile, "kemet");
	
		for (VerifiedRow row : vf.getRows()) {
			if(row.getStatus()!=Status.Ok)
				System.out.println(row.toString());
		}
		 
	}
}
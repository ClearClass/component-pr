package lib.clearclass.model;

import java.util.ArrayList;
import java.util.List;

import lib.clearclass.logic.parser.CompInst;
import lib.clearclass.model.VerifiedFile.VerifiedRow;
import lib.clearclass.model.VerifiedFile.VerifiedRow.Status;

public class VerifiedFile {
	private String filename; // binary filename
	private boolean emptyDecml = false;
	

	
	
	public VerifiedFile(String filename){
		this.filename = filename;
	}
	
	public String getFilename() {
		return filename;
	}	
	


	public boolean isEmptyDecml() {
		return emptyDecml;
	}



//---------------------------------------------------------------


	public void setEmptyDecmlErr() {
		this.emptyDecml = true;
	}
	public void setDecmlNotMatchesWarn(String decml){
	}
	public void setEmptyTitleWarn() {
	}
//-------------------------------------------------------------------
	
	

	
	
	
	private List<VerifiedRow> rows = new ArrayList<>();
	
	
	
	public List<VerifiedRow> getRows() {
		return rows;
	}



	public static class VerifiedRow {
		private String refdes;
		private String type;
		private String val_mark;
		private String spec_man;
		private Status status;
		private String message;
		
		public enum Status {
			Ok, Warn, Err
		}
			
		public VerifiedRow(String refdes, String type, String val_mark, 
				String spec_man, Status status, String message) {

			this.refdes = refdes;
			this.type = type;
			this.val_mark = val_mark;
			this.spec_man = spec_man;
			this.status = status;
			this.message = message;
		}

		
		
		
		public Status getStatus() {
			return status;
		}




		@Override
		public String toString() {
			return refdes + " " + type + " " + val_mark + " "
					+ spec_man + "    " + status + "    " + message ;
		}
		

		





	}



	public void add(String refdes, String type, String val_mark, 
			String spec_man, Status status, String message) {
		rows.add(new VerifiedRow(refdes, type, val_mark, spec_man, status, message));
	}

	public boolean hasError() {
		return false;
	}
	



}

package lib.clearclass.logic;

public enum CadFileType {
	SCH, PCB;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
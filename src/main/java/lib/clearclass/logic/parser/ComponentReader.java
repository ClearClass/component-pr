package lib.clearclass.logic.parser;

import java.util.Iterator;
import java.util.Set;

public interface ComponentReader extends Iterator<CompInst> {
	String getDecml();
	String getTitle();
	Set<CompInst> getSet();
}

package lib.clearclass.logic.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

import lib.clearclass.model.ComponentList;

@Service
public class ExportService {
	private static final int wdPasteDefault = 0;
	private static final int wdCollapseEnd = 0;
	private static final int wdPrintView = 3;
	
	@Autowired
	private TemplateEngine engine;
	
	public File createFileFrom(ComponentList mdl) {
		File outputDir = new File("Items/" + mdl.getItem() + "/output");
		if(!outputDir.exists()) outputDir.mkdir();

		// 1. создаем html-результат
		File html = new File(outputDir.getPath() + "/table.html");
		try(Writer htmlWriter = new FileWriter(html)){
			Context context = new Context();
			context.setVariable("cmpl", mdl);
			engine.process("output/documents/word-table.html", context, htmlWriter);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// 2.копируем шаблон word с рамкой
		File blank = new File(outputDir.getPath() + "/blank.doc");
		if(blank.exists()) blank.delete();
		try {
			Resource r = new ClassPathResource("export/win32/blank.doc");
			Files.copy(r.getInputStream(), blank.toPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// копирование библиотеки jacob-1.18-x86.dll
		File dll = new File(System.getenv("JAVA_HOME") + "/bin/jacob-1.18-x86.dll"); 
		if(!dll.exists())
			try {
				Resource r = new ClassPathResource("export/win32/jacob-1.18-x86.dll");
				Files.copy(r.getInputStream(), dll.toPath());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		
		// 3. Копируем таблицы
		ActiveXComponent xApplication = new ActiveXComponent("Word.Application");
//		xApplication.setProperty("Visible", true);
		ActiveXComponent xDocuments = xApplication.getPropertyAsComponent("Documents");
		ActiveXComponent blankXDocument = xDocuments.invokeGetComponent("Open", new Variant(blank.getAbsolutePath()));
		ActiveXComponent htmlXDocument = xDocuments.invokeGetComponent("Open", new Variant(html.getAbsolutePath()));
		ActiveXComponent tablesXCollection = htmlXDocument.invokeGetComponent("Tables");
		int numOfTables = tablesXCollection.getPropertyAsInt("Count");
		
		for (int i = 1; i <= numOfTables; i++) {
			tablesXCollection.invokeGetComponent("Item", new Variant(i))
							 .getPropertyAsComponent("Range")
							 .invoke("Copy");
			
			ActiveXComponent docXRange = blankXDocument.invokeGetComponent("Content");
			docXRange.invoke("Collapse", wdCollapseEnd);
			docXRange.invoke("PasteAndFormat", wdPasteDefault);
			docXRange = blankXDocument.invokeGetComponent("Content");
			docXRange.invoke("Collapse", wdCollapseEnd);
			docXRange.invoke("InsertAfter", "\n\n\n\n\n");
		}

		htmlXDocument.invoke("Close");
		html.delete();
		
		blankXDocument.invoke("Save");
		blankXDocument.invoke("Close");
		
		// пересохраняем blank.doc в режим разметки, и с новым именем
		blankXDocument = xDocuments.invokeGetComponent("Open", new Variant(blank.getAbsolutePath()));
		blankXDocument.getPropertyAsComponent("ActiveWindow")
					  .getPropertyAsComponent("View")
					  .setProperty("Type", wdPrintView);
		
		// файл, в который пересохраняем
		File doc = new File(outputDir.getPath() + "/" + mdl.getUnit() + "_ПЭ3.doc");
		if(doc.exists()) doc.delete();
		blankXDocument.invoke("SaveAs", doc.getAbsolutePath());
		blankXDocument.invoke("Close");
		
		xApplication.invoke("Quit");

		blank.delete();
		return doc;
	}
}
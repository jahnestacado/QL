package org.uva.sea.ql.output.generators.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.uva.sea.ql.gui.qlform.output.QLOutputSelectorFrame;
import org.uva.sea.ql.output.generators.pdf.QLToPDF;
import org.uva.sea.ql.visitor.evaluator.values.Value;

public class QLToJSON {

	private final Map<String,Value> allRunTimeValues;
	private final JSONObject qlForm = new JSONObject();
	private final JFrame frame;

	private QLToJSON(Map<String,Value> allRunTimeValues, JFrame frame) {
		this.allRunTimeValues=allRunTimeValues;
		this.frame= frame;
	}

	public static void generateJson(JFrame frame,Map<String,Value> allRunTimeValues) {
		QLToJSON generator = new QLToJSON(allRunTimeValues, frame);
		generator.createForm(frame.getTitle());
		generator.writeToFile(frame.getTitle());

	}

	@SuppressWarnings("unchecked")
	private void createForm(String frameName) {
		JSONObject title = new JSONObject();
		title.put("formName", frameName);
		
		JSONArray structure = new JSONArray();
		structure.add(title);
		
		JSONObject content = getContentList();
		structure.add(content);
		qlForm.put("qlForm", structure);
	}

	private void writeToFile(String frameName) {
		try {
			String path = QLToPDF.showSaveDialog(frameName+".json", new TypeOfJson());
			if (path.isEmpty())
				return;
			File filePath = new File(path);
			FileWriter file = new FileWriter(filePath.getAbsolutePath());
			file.write(qlForm.toJSONString());
			file.flush();
			file.close();
			QLOutputSelectorFrame.showConfirmationMessage(frame, frame.getTitle()+".json file successful created!");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private JSONObject getContentList() {
		JSONArray contentList = new JSONArray();
		Iterator<String> iterator = allRunTimeValues.keySet().iterator();

		while (iterator.hasNext()) {
			JSONObject question = new JSONObject();
			String key = iterator.next().toString();
			question.put("ident", key);
			question.put("answer",String.valueOf(allRunTimeValues.get(key).getValue()));
			contentList.add(question);

		}
		JSONObject questionsList = new JSONObject();
		questionsList.put("questions", contentList);
		return questionsList;
	}
	

	class TypeOfJson extends FileFilter {
		public boolean accept(File file) {
			return file.isDirectory();
		}

		public String getDescription() {
			return ".json";
		}
	}

}

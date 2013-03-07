package org.uva.sea.ql.gui.ide;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.antlr.runtime.ANTLRFileStream;
import org.uva.sea.ql.ast.form.Form;
import org.uva.sea.ql.parser.antlr.ANTLRParser;
import org.uva.sea.ql.parser.test.ParseError;

public class QLInputReader {

	public static String readFile(File path) {
		try {
			ANTLRFileStream charStream = new ANTLRFileStream(
					path.getAbsolutePath());
			return charStream.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Form getParsedForm(String input, ConsoleArea consoleArea) {
		ANTLRParser parser = new ANTLRParser();
		try {
			Form parsedForm = parser.parseForm(input);
			List<String> errorList = parser.getParserErrors();
			if(!errorList.isEmpty()){
			consoleArea.displayParseErrors(parser.getParserErrors());
			}
			return parsedForm;
		} catch (ParseError e) {

			e.printStackTrace();
		}
		return null;

	}
	
}

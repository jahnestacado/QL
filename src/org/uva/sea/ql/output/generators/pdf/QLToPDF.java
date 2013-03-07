package org.uva.sea.ql.output.generators.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import org.uva.sea.ql.gui.qlform.output.QLOutputSelectorFrame;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
 
public class QLToPDF {
	private final static Font symbolFont = new Font(Font.FontFamily.ZAPFDINGBATS, 15, Font.BOLD);
	private final static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.UNDERLINE);
    private final static Font questionFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,Font.ITALIC);
    private final static Font resultsFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,Font.BOLD);
    private final static URL imgPath =  QLToPDF.class.getResource("/org/uva/sea/ql/output/generators/pdf/images/uva_logo.jpg");
    private final static Map<String,String> boolValues;
    static
    {
    	boolValues = new HashMap<String, String>();
    	boolValues.put("true", "Yes");
    	boolValues.put("false","No");
    }
    
    private final static Map<String,String> yesOrNoSymbol;
    static
    {
    	yesOrNoSymbol = new HashMap<String, String>();
    	yesOrNoSymbol.put("true", "4");   //** Values '4' and '6' corresponds to 'tick' and 'X' symbols of
    	yesOrNoSymbol.put("false","6");   //** the ZAPFDINGBATS font. 
    }


    private final List<String> questionLabels;
    private final List<String> questionValues;
    private final JFrame frame;
	
	
	
	private QLToPDF(List<String> questionLabels,List<String> questionValues, JFrame frame){
		this.questionLabels=questionLabels;
		this.questionValues=questionValues;
		this.frame= frame;
	}

    public static void generatePdf(JFrame frame,List<String> questionLabels,List<String> questionValues) {
    	QLToPDF generator=new QLToPDF(questionLabels,questionValues, frame);
    	generator.putContent(frame.getTitle());
    	
    }
    
    
	private void putContent(String frameName) {
		try {
			String path = showSaveDialog(frameName+".pdf",new TypeOfPDF());
			if (path.isEmpty())
				return;
			File filePath = new File(path);
			OutputStream file = new FileOutputStream(filePath.getAbsolutePath());
			Document document = new Document();
			PdfWriter.getInstance(document, file);
			document.open();
			Image img = Image.getInstance(imgPath);
			document.add(setHeaderLogo(img));
			document.add(setTitle(frameName));

			for (int i = 0; i <= questionLabels.size() - 1; i++) {
				Paragraph p1 = new Paragraph(questionLabels.get(i),
						questionFont);

				p1 = getProperDisplayedValue(p1, questionValues.get(i));
				addEmptyLine(p1, 1);
				document.add(new Paragraph(p1));
			}
			document.close();
			file.close();
			QLOutputSelectorFrame.showConfirmationMessage(frame, frame.getTitle()+".pdf file successful created!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
          paragraph.add(new Paragraph(" "));
        }
      }
    
	private static Paragraph setHeaderLogo(Image img) {
		Paragraph logoHeader = new Paragraph();
		img.scalePercent(15f);
		Chunk imgChunk = new Chunk(img, 0, 0, true);
		logoHeader.add(imgChunk);
		logoHeader.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(logoHeader, 1);
		return logoHeader;
	}
    
	private static Paragraph setTitle(String frameName) {
		Paragraph header = new Paragraph();
		addEmptyLine(header, 1);
		Paragraph title = new Paragraph(frameName + " Questionnaire", titleFont);
		header.add(title);
		addEmptyLine(header, 2);
		title.setAlignment(Element.ALIGN_CENTER);
		return header;
	}
    
	private Paragraph getProperDisplayedValue(Paragraph paragraph, String value) {
		paragraph.add(new Chunk("     "));
		if (boolValues.containsKey(value)) {
			paragraph.add(new Chunk(yesOrNoSymbol.get(value), symbolFont));
			paragraph.add(new Chunk("  (" + boolValues.get(value) + ")", resultsFont));
		} else {
			paragraph.add(new Chunk("  " + value, resultsFont));

		}

		return paragraph;

	}
	
	public static String showSaveDialog(String fileName,FileFilter fileType) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setSelectedFile(new File(fileName));
        fileChooser.setFileFilter(fileType);
		int returnValue = fileChooser.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File outputFile = fileChooser.getSelectedFile();
			return outputFile.getAbsolutePath();
		}
		return "";
	}
	
	class TypeOfPDF extends FileFilter {
		public boolean accept(File file) {
			return file.isDirectory();
		}

		public String getDescription() {
			return ".pdf";
		}
	}

	
	
}
package OperateXML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class TestFileXMLGenerate {

	// ���ؽ������ΪDocument����
	public Document generateDocumentByMethod(String fileName) {
		Document document = DocumentHelper.createDocument();

		// ProcessingInstruction
		Map<String, String> inMap = new HashMap<String, String>();
		inMap.put("type", "text/xsl");
		inMap.put("href", "TestFileXML.xml");
		document.addProcessingInstruction("xml-stylesheet", inMap);
		// root element
		Element ArticleElement = document.addElement("ArticleStore");
		ArticleElement.addComment("Article Catalog"); // ����ע��
		// son element
		Element nameElement = ArticleElement.addElement("article");
		nameElement.addAttribute("title", fileName);
		
		
		// Element nameElement1 = fileElement1.addElement("name");
		// nameElement1.setText("a.java");
//		Element filePathElement = nameElement.addElement("filePath");
//		filePathElement.addAttribute("Path", Path);
		
//		Element timesElement = wordPathElement.addElement("times");
//		timesElement.setText("0");
//		Element weightElement = wordPathElement.addElement("weight");
//		weightElement.setText("0");
		return document;
	}

	public void saveDocument(Document document, File outputXml) {
		try {
			// ������ʽ
			OutputFormat format = OutputFormat.createPrettyPrint();
			/*
			 * // ������ʽ OutputFormat format = OutputFormat.createCompactFormat();
			 */
			/*
			 * // ָ��XML���� format.setEncoding("GBK");
			 */
			XMLWriter output = new XMLWriter(new FileWriter(outputXml), format);
	//		format.setEncoding("unicode");
			format.setEncoding("GBK");
			output.write(document);
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void InterfaceGenerate(String filePath, String fileName){
		TestFileXMLGenerate dom4j = new TestFileXMLGenerate();
		Document document = null;
		document = dom4j.generateDocumentByMethod(fileName);
		dom4j.saveDocument(document, new File(filePath));
	}

	public static void main(String[] argv) {
	}
}

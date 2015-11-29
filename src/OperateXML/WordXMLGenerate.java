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

public class WordXMLGenerate {

	// 返回结果类型为Document类型
	public Document generateDocumentByMethod() {
		Document document = DocumentHelper.createDocument();

		// ProcessingInstruction
		Map<String, String> inMap = new HashMap<String, String>();
		inMap.put("type", "text/xsl");
		inMap.put("href", "WordXML.xml");
		document.addProcessingInstruction("xml-stylesheet", inMap);
		// root element
		Element WordElement = document.addElement("WordStore");
		WordElement.addComment("Word Catalog"); // 增添注释
		

		return document;
	}

	public void saveDocument(Document document, File outputXml) {
		try {
			// 美化格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			/*
			 * // 缩减格式 OutputFormat format = OutputFormat.createCompactFormat();
			 */
			/*
			 * // 指定XML编码 format.setEncoding("GBK");
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
	
	public void InterfaceGenerate(String storePath){
		WordXMLGenerate dom4j = new WordXMLGenerate();
		Document document = null;
		document = dom4j.generateDocumentByMethod();
		dom4j.saveDocument(document, new File(storePath+"WordXML.xml"));
	}

	public static void main(String[] argv) {

	}
}

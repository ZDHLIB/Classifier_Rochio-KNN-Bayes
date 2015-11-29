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

public class FileXmlGenerate {

	// 返回结果类型为Document类型
	public Document generateDocumentByMethod(String fileName, String category) {
		Document document = DocumentHelper.createDocument();

		// ProcessingInstruction
		Map<String, String> inMap = new HashMap<String, String>();
		inMap.put("type", "text/xsl");
		inMap.put("href", "FileXML.xml");
		document.addProcessingInstruction("xml-stylesheet", inMap);
		// root element
		Element ArticleElement = document.addElement("ArticleStore");
		ArticleElement.addComment("Article Catalog"); // 增添注释
		// son element
		Element nameElement = ArticleElement.addElement("article");
		nameElement.addAttribute("title", fileName);
		
		// son son element
		Element Category_Element = nameElement.addElement("category");
		Category_Element.addComment("Article category"); // 增添注释
		Category_Element.addAttribute("name", category);
		
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
			// 美化格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			/*
			 * // 缩减格式 OutputFormat format = OutputFormat.createCompactFormat();
			 */
			/*
			 * // 指定XML编码 format.setEncoding("GBK");
			 */
			XMLWriter output = new XMLWriter(new FileWriter(outputXml), format);
			format.setEncoding("GBK");
			output.write(document);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void InterfaceGenerate(String filePath, String fileName, String category){
		FileXmlGenerate dom4j = new FileXmlGenerate();
		Document document = null;
		document = dom4j.generateDocumentByMethod(fileName,category);
		dom4j.saveDocument(document, new File(filePath));
	}

	public static void main(String[] argv) {
		FileXmlGenerate a = new FileXmlGenerate();
		a.InterfaceGenerate("", "C29-Transport026.txt", "C29-Transport");
	}
}

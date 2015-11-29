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

public class VectorXMLGenerate {

	// ���ؽ������ΪDocument����
	public Document generateDocumentByMethod() {
		Document document = DocumentHelper.createDocument();

		// ProcessingInstruction
		Map<String, String> inMap = new HashMap<String, String>();
		inMap.put("type", "text/xsl");
		inMap.put("href", "VectorXML.xml");
		document.addProcessingInstruction("xml-stylesheet", inMap);
		// root element
		Element WordElement = document.addElement("VectorStore");
		WordElement.addComment("Vector Catalog"); // ����ע��
		

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
	
	public void InterfaceGenerate(String storePath, String head){
		VectorXMLGenerate dom4j = new VectorXMLGenerate();
		Document document = null;
		document = dom4j.generateDocumentByMethod();
		dom4j.saveDocument(document, new File(storePath+head+"VectorXML.xml"));
	}

	public static void main(String[] argv) {

	}
}

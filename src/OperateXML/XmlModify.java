package OperateXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class XmlModify {

	public void updateXML(String wordName, String fileURL)
			throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		InputStream file = new FileInputStream(
				"D:\\Java\\lrc\\classify\\ymake.xml");
		Document doc = builder.build(file);// 获得文档对象
		Element root = doc.getRootElement();// 获得根节点
		List<Element> list = root.getChildren();
		for (Element e : list) {
			// 获取ID值
			if (e.getAttributeValue("wordName").equals(wordName)) {
				System.out.println("--------------------");

				List<Element> list1 = e.getChildren();
				for (Element e1 : list1) {
					if (e1.getAttributeValue("Path").equals(fileURL)) {
						System.out.println("*********************");
						e1.getChild("times").setText("11xxx561");
					}
				}
			}
		}

		// 文件处理
		XMLOutputter out = new XMLOutputter();
		out.output(doc, new FileOutputStream(
				"D:\\Java\\lrc\\classify\\ymake.xml"));
	}

	/*
	 * public boolean changeXML(String fileURL) {
	 * 
	 * try { Document doc = null; File file = new File(fileURL); SAXBuilder
	 * builder = new SAXBuilder(); doc = builder.build(file); Element foo =
	 * doc.getRootElement(); foo.getChild("times").setText("12345"); return
	 * true; } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return false; }
	 */
	public static void main(String[] args) {
		XmlModify xmlmod = new XmlModify();
		String fileName = "D:\\Java\\lrc\\classify\\ymake.xml";
		try {
			xmlmod.updateXML("11hxdcsjhvh","G:/clsdaoudplat");
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// xmlmod.changeXML(fileName);
	}
}

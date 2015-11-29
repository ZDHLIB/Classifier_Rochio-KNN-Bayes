package OperateXML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

//import XmlGenerate;

public class AddElement {

	/**
	 *the method formatXML Ĭ�ϵ������ʽΪ���շ�ʽ��Ĭ�ϱ���ΪUNICODE�� ���������ǵ�Ӧ�ö��ԣ�һ�㶼Ҫ�õ����ģ�
	 * ����ϣ����ʾʱ���Զ������ķ�ʽ����ʾ��������õ�OutputFormat�ࡣ
	 */
	public boolean formatXML(String fileName) {
		boolean isOk = false;
		try {
			SAXReader reader = new SAXReader();
			org.dom4j.Document doc = reader.read(new File(fileName));
			XMLWriter formatWriter = null;
			/**
			 *��ʽ�����,����IE���һ�� ָ��XML����
			 */
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");
			formatWriter = new XMLWriter(new FileOutputStream(fileName), format);
			formatWriter.write(doc);
			formatWriter.close();

			isOk = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOk;
	}

	/**
	 * @param fileName
	 * @param wordTree
	 * @return
	 * @function add chiledElement of FileXML
	 */
	@SuppressWarnings("unchecked")
	public boolean addChildElement(String fileXMLPath, String fileName,
			TreeMap<String, Integer> wordTree) {
		
		if( wordTree.isEmpty() ){
			return true;
		}
		
		try {

			boolean flag = true;
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream(fileXMLPath);
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�
			List<Element> list = root.getChildren();
			for (Element e : list) {
				// ��ȡIDֵ
				if (e.getAttributeValue("title").equals(fileName)) {
					for (Iterator itTree = wordTree.keySet().iterator(); itTree
							.hasNext();) {
						// System.out.println(wordTree.get(itTree.next()));//
						// �������tr7�����е�Ԫ��
						String word = (String) itTree.next();
						Element eleCount = new Element("tf");
						eleCount.setText(String.valueOf(wordTree.get(word)));

						word = word.replaceAll("\\r\\n", "");
						Element element = new Element("word");
						element.setAttribute("name", word);

						e.getChild("category").addContent(element);
						element.addContent(eleCount);
					}
				}
			}
			// �ļ�����
			XMLOutputter out = new XMLOutputter();
			out.output(doc, new FileOutputStream(fileXMLPath));
			formatXML(fileXMLPath);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * @param fileName
	 * @param wordTree
	 * @return
	 * @function add chiledElement of TestFileXML
	 */
	@SuppressWarnings("unchecked")
	public boolean addTestFileChildElement(String fileXMLPath, String fileName,
			TreeMap<String, Integer> wordTree) {
		
		if( wordTree.isEmpty() ){
			return true;
		}
		
		try {

			boolean flag = true;
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream(fileXMLPath);
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�
			List<Element> list = root.getChildren();
			for (Element e : list) {
				// ��ȡIDֵ
				if (e.getAttributeValue("title").equals(fileName)) {
					for (Iterator itTree = wordTree.keySet().iterator(); itTree
							.hasNext();) {
						// System.out.println(wordTree.get(itTree.next()));//
						// �������tr7�����е�Ԫ��
						String word = (String) itTree.next();
						Element eleCount = new Element("tf");
						eleCount.setText(String.valueOf(wordTree.get(word)));

						word = word.replaceAll("\\r\\n", "");
						Element element = new Element("word");
						element.setAttribute("name", word);

						e.addContent(element);
						element.addContent(eleCount);
					}
				}
			}
			// �ļ�����
			XMLOutputter out = new XMLOutputter();
			out.output(doc, new FileOutputStream(fileXMLPath));
			formatXML(fileXMLPath);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public boolean addWordChildElement(String wordName, String count, String titleName){
		boolean flag = false;
		boolean flag2 = false;
		boolean flag3 = false;
		try{
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("WordXML.xml");
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�
			List<Element> list = root.getChildren();
			for (Element e : list) {
				if (e.getAttributeValue("name").equals(wordName)) {
					flag = true;
					System.out.println(flag);
					Element Article_Element = new Element("Article");
					Article_Element.setAttribute("title", titleName);

					Element Count_Element = new Element("tf");
					Count_Element.setText(count);

					Article_Element.addContent(Count_Element);
					e.getChild("Source").addContent(Article_Element);
				}
			}
			
			if( !flag ){
				System.out.println(flag);
				Element nameElement = new Element("word");
				nameElement.setAttribute("name", wordName);

				// son son element
				Element DF_Element = new Element("DF");
				DF_Element.setAttribute("name", "0");

				Element Source_Element = new Element("Source");
				Source_Element.setAttribute("name", "source");

				// son son son element
				Element Article_Element = new Element("Article");
				Article_Element.setAttribute("title", titleName);

				Element Count_Element = new Element("tf");
				Count_Element.setText(count);

				Article_Element.addContent(Count_Element);
				Source_Element.addContent(Article_Element);
				nameElement.addContent(DF_Element);
				nameElement.addContent(Source_Element);
				root.addContent(nameElement);
			}
			
			XMLOutputter out = new XMLOutputter();
			out.output(doc, new FileOutputStream("WordXML.xml"));
			formatXML("WordXML.xml");
		}catch(Exception e){
			System.out.println("addWordChildElement(String wordName, String count, String titleName)");
		}
		return false;
	}
	
	public boolean addWordChildElement(String wordName, String count, String titleName, String category) {
		boolean flag = false;
		boolean flag2 = false;
		boolean flag3 = false;
		try {
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("WordXML.xml");
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�

			List<Element> list = root.getChildren();
			for (Element e : list) {
				if (e.getAttributeValue("name").equals(wordName)) {

					flag2 = true;// ���������ʣ�flag=true;
					List<Element> list1 = e.getChildren(); // ��ȡ�ô��µ��������
					for (Element e1 : list1) {
						if (e1.getAttributeValue("name").equals(category)) {
							Element Article_Element = new Element("Article");
							Article_Element.setAttribute("title", titleName);

							Element Count_Element = new Element("tf");
							Count_Element.setText(count);

							Article_Element.addContent(Count_Element);
							e1.getChild("Source").addContent(Article_Element);
							flag = true; // �������������flag=true
						}
					}

					// ����ô���û�������������һ�����
					if (!flag) {
						Element CategoryElement = new Element("category");
						CategoryElement.setAttribute("name", category);

						Element DF_Element = new Element("DF");
						DF_Element.setAttribute("name", "0");

						Element Source_Element = new Element("Source");
						Source_Element.setAttribute("name", "source");

						Element Article_Element = new Element("Article");
						Article_Element.setAttribute("title", titleName);

						Element Count_Element = new Element("tf");
						Count_Element.setText(count);

						Article_Element.addContent(Count_Element);
						Source_Element.addContent(Article_Element);
						CategoryElement.addContent(DF_Element);
						CategoryElement.addContent(Source_Element);
						e.addContent(CategoryElement);

					}

				}
			}

			// ���û������ʣ������һ����
			if (!flag2) {

				Element nameElement = new Element("word");
				nameElement.setAttribute("name", wordName);

				// son element
				Element CategoryElement = new Element("category");
				CategoryElement.setAttribute("name", category);

				// son son element
				Element DF_Element = new Element("DF");
				DF_Element.setAttribute("name", "0");

				Element Source_Element = new Element("Source");
				Source_Element.setAttribute("name", "source");

				// son son son element
				Element Article_Element = new Element("Article");
				Article_Element.setAttribute("title", titleName);

				Element Count_Element = new Element("tf");
				Count_Element.setText(count);

				Article_Element.addContent(Count_Element);
				Source_Element.addContent(Article_Element);
				CategoryElement.addContent(DF_Element);
				CategoryElement.addContent(Source_Element);
				nameElement.addContent(CategoryElement);
				root.addContent(nameElement);
			}

			XMLOutputter out = new XMLOutputter();
			out.output(doc, new FileOutputStream("WordXML.xml"));
			formatXML("WordXML.xml");

			return true;
		} catch (Exception e) {
		}
		return flag;
	}

	public boolean addPropertyElement(String category, String fileName, String wordName,
			String tf, String df, String idf, String tfidf) {
		boolean flag = false;
		boolean flag2 = false;
		try {
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("PropertyXML.xml");
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�

			List<Element> list = root.getChildren();//������е����
			
			for( Element e : list ){
				if (e.getAttributeValue("name").equals(category)) {
					flag = true;
					List<Element> list1 = e.getChildren();//��ø�������е�����
					for( Element e1 : list1 ){
						if (e1.getAttributeValue("title").equals(fileName)) {
							flag2 = true;
							
							Element Word_Element = new Element("word");
							Word_Element.setAttribute("name", wordName);

							Element category_Element = new Element("category");
							category_Element.setText(category);
							
							Element tf_Element = new Element("tf");
							tf_Element.setText(tf);

							Element df_Element = new Element("df");
							df_Element.setText(df);
							
							Element idf_Element = new Element("idf");
							idf_Element.setText(idf);

							Element tfidf_Element = new Element("tfidf");
							tfidf_Element.setText(tfidf);

							e1.addContent(Word_Element);
							Word_Element.addContent(category_Element);
							Word_Element.addContent(tf_Element);
							Word_Element.addContent(df_Element);
							Word_Element.addContent(idf_Element);
							Word_Element.addContent(tfidf_Element);

							break;
						}
					}
					
					if (!flag2) {

						Element article_Element = new Element("article");
						article_Element.setAttribute("title", fileName);

						Element Word_Element = new Element("word");
						Word_Element.setAttribute("name", wordName);
						
						Element category_Element = new Element("category");
						category_Element.setText(category);

						Element tf_Element = new Element("tf");
						tf_Element.setText(tf);

						Element df_Element = new Element("df");
						df_Element.setText(df);
						
						Element idf_Element = new Element("idf");
						idf_Element.setText(idf);

						Element tfidf_Element = new Element("tfidf");
						tfidf_Element.setText(tfidf);

						e.addContent(article_Element);
						article_Element.addContent(Word_Element);
						Word_Element.addContent(category_Element);
						Word_Element.addContent(tf_Element);
						Word_Element.addContent(df_Element);
						Word_Element.addContent(idf_Element);
						Word_Element.addContent(tfidf_Element);
					}
					
				}
			}
			//��������ڴ��࣬�����Ӹ���
			if (!flag) {

				Element category_Ele = new Element("category");
				category_Ele.setAttribute("name", category);
				
				Element article_Element = new Element("article");
				article_Element.setAttribute("title", fileName);

				Element Word_Element = new Element("word");
				Word_Element.setAttribute("name", wordName);
				
				Element category_Element = new Element("category");
				category_Element.setText(category);

				Element tf_Element = new Element("tf");
				tf_Element.setText(tf);

				Element df_Element = new Element("df");
				df_Element.setText(df);
				
				Element idf_Element = new Element("idf");
				idf_Element.setText(idf);

				Element tfidf_Element = new Element("tfidf");
				tfidf_Element.setText(tfidf);

				root.addContent(category_Ele);
				category_Ele.addContent(article_Element);
				article_Element.addContent(Word_Element);
				Word_Element.addContent(category_Element);
				Word_Element.addContent(tf_Element);
				Word_Element.addContent(df_Element);
				Word_Element.addContent(idf_Element);
				Word_Element.addContent(tfidf_Element);
			}

			XMLOutputter out = new XMLOutputter();
			out.output(doc, new FileOutputStream("PropertyXML.xml"));
			formatXML("PropertyXML.xml");
		} catch (Exception e) {
		}

		return flag;
	}

	public boolean addVectorElement(String category, TreeMap<String, Double> vector, String head){
		boolean flag = false;
		Object wordName = "";
		String weight = "";
		String finalWeight = "";
		
		try{
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream(head+"VectorXML.xml");
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�
			
			Element category_Ele = new Element("category");
			category_Ele.setAttribute("name", category);
			
			for (Iterator iterator = vector.keySet().iterator(); iterator.hasNext();) {
				wordName = iterator.next();
				weight = String.valueOf(vector.get(wordName));
				
				Element wordEle = new Element("word");
				wordEle.setAttribute("name", String.valueOf(wordName));
				
				Element weightEle = new Element("weight");
				weightEle.setText(weight);
				
				wordEle.addContent(weightEle);
				category_Ele.addContent(wordEle);
			}
			root.addContent(category_Ele);
			
			XMLOutputter out = new XMLOutputter();
			out.output(doc, new FileOutputStream(head+"VectorXML.xml"));
			formatXML(head+"VectorXML.xml");
		}catch(Exception e){
			System.out.println("addVectorElement");
		}
		return flag;
	}
	
	public boolean addXml(String wName, String wPath) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build("D:\\Java\\lrc\\classify\\ymake.xml");// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�

			// �����Ԫ��
			Element element = new Element("name");
			element.setAttribute("wordName", wName);
			Element element0 = new Element("filePath");
			element0.setAttribute("Path", wPath);

			Element element1 = new Element("times");
			element1.setText("1");
			Element element2 = new Element("weight");
			element2.setText("0");

			element.addContent(element0);
			element0.addContent(element1);
			element0.addContent(element2);
			root.addContent(element);
			doc.setRootElement(root);

			// �ļ�����
			XMLOutputter out = new XMLOutputter();
			out.output(doc, new FileOutputStream(
					"D:\\Java\\lrc\\classify\\ymake.xml"));
			formatXML("D:\\Java\\lrc\\classify\\ymake.xml");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

	public static void main(String[] args) {
		AddElement ae = new AddElement();
		// boolean bool=ae.addElement("animal.avi",
		// "G:/a/b","Jack","all the animals live togther in harmony","2012-5-1",
		// "10.10.135.60");
		boolean bool = ae.addXml("������ˮ��Ϊδ����", "G:/clo��rjtyu67r4563");
		// boolean bool = ae.addChildElement("fnmvbnvbf",
		// "G:/clo��rjtyu67r4563");
		if (bool == true)
			System.out.println("successful");
		else
			System.out.println("failed");
	}
}

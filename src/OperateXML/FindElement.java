package OperateXML;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class FindElement {
		private File inputXml;

		public FindElement(File inputXml) {
				this.inputXml = inputXml;
			}

		public Document getDocument() {
				SAXReader saxReader = new SAXReader();
				Document document = null;
				try {
						document = saxReader.read(inputXml);
				} catch (DocumentException e) {
						e.printStackTrace();
					}
				return document;
		}

		public Element getRootElement() {       //��ø��ڵ�
				return getDocument().getRootElement();
			}

		public List<String>  traversalDocumentByIterator(String fileName) {
				int[] node=new int[5];
		//		String[][]  result = new String[][]{};
				List<String> result = new ArrayList<String>();
				String fName=fileName;
				Element root = getRootElement();
				String cnode="";
				// ö�ٸ��ڵ��������ӽڵ�
				for (Iterator ie = root.elementIterator(); ie.hasNext();) {
					Element element = (Element) ie.next();
					// ö������
					Iterator ia = element.attributeIterator(); 
					Attribute attribute = (Attribute) ia.next();
					String a= attribute.getData().toString();
					//���Ұ����ؼ��ֵ��ļ�
					if( attribute.getData().toString().contains(fName)){
					//	System.out.println(attribute.getName() + ":"
						//		+ attribute.getData());     //�ļ�����
						result.add(attribute.getData().toString());
						
						// ö�ٵ�ǰ�ڵ��������ӽڵ�
						for (Iterator ieson = element.elementIterator(); ieson.hasNext();) {
							Element elementSon = (Element) ieson.next();
							cnode = elementSon.getText();
						    //System.out.println(elementSon.getName() + ":"+ cnode);
						    result.add(cnode);
						}
		      }
				
		}
				if(cnode==null || cnode=="")
					System.out.println("û�и��ļ������ļ�δ���ϴ��򴴽�");
				return result;		
	}

	public static void main(String[] argv) {
		FindElement dom4jParser = new FindElement(new File("G:\\cloudplat\\make.xml"));
		List<String>  allResult=dom4jParser.traversalDocumentByIterator("����");		
		System.out.println(allResult.toString());
	}
}

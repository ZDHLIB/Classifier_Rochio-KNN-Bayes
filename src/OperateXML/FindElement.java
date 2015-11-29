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

		public Element getRootElement() {       //获得根节点
				return getDocument().getRootElement();
			}

		public List<String>  traversalDocumentByIterator(String fileName) {
				int[] node=new int[5];
		//		String[][]  result = new String[][]{};
				List<String> result = new ArrayList<String>();
				String fName=fileName;
				Element root = getRootElement();
				String cnode="";
				// 枚举根节点下所有子节点
				for (Iterator ie = root.elementIterator(); ie.hasNext();) {
					Element element = (Element) ie.next();
					// 枚举属性
					Iterator ia = element.attributeIterator(); 
					Attribute attribute = (Attribute) ia.next();
					String a= attribute.getData().toString();
					//查找包含关键字的文件
					if( attribute.getData().toString().contains(fName)){
					//	System.out.println(attribute.getName() + ":"
						//		+ attribute.getData());     //文件名称
						result.add(attribute.getData().toString());
						
						// 枚举当前节点下所有子节点
						for (Iterator ieson = element.elementIterator(); ieson.hasNext();) {
							Element elementSon = (Element) ieson.next();
							cnode = elementSon.getText();
						    //System.out.println(elementSon.getName() + ":"+ cnode);
						    result.add(cnode);
						}
		      }
				
		}
				if(cnode==null || cnode=="")
					System.out.println("没有该文件，该文件未被上传或创建");
				return result;		
	}

	public static void main(String[] argv) {
		FindElement dom4jParser = new FindElement(new File("G:\\cloudplat\\make.xml"));
		List<String>  allResult=dom4jParser.traversalDocumentByIterator("弗洛");		
		System.out.println(allResult.toString());
	}
}

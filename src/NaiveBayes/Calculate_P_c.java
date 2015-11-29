package NaiveBayes;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class Calculate_P_c {

	/**
	 * ����ĳһ�������ֵ
	 */
	public Double Calculate_PreValue(String category){
		Double preValue = 0.0;
		int allFileSum = 0;
		int categoryFileSum = 0;
		try{
			
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("PropertyXML.xml");
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�
			List<Element> list = root.getChildren();//������е���
			for( Element e : list ){
				List<Element> list1 = e.getChildren();//��ø���������������
				allFileSum += list1.size();
				if( e.getAttributeValue("name").equals(category)){
					categoryFileSum = list1.size();
				}
			}
			
			preValue = Double.valueOf(categoryFileSum) / Double.valueOf(allFileSum);
			
			return preValue;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return preValue;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

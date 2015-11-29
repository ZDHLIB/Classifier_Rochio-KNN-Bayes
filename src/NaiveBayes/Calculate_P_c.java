package NaiveBayes;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class Calculate_P_c {

	/**
	 * 计算某一类的先验值
	 */
	public Double Calculate_PreValue(String category){
		Double preValue = 0.0;
		int allFileSum = 0;
		int categoryFileSum = 0;
		try{
			
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("PropertyXML.xml");
			Document doc = builder.build(file);// 获得文档对象
			Element root = doc.getRootElement();// 获得根节点
			List<Element> list = root.getChildren();//获得所有的类
			for( Element e : list ){
				List<Element> list1 = e.getChildren();//获得该类下所有文章数
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

package NaiveBayes;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class Calculate_P_w_c {

	public Double Calculate_Pwc(TreeMap<String, Integer> testVector, String category){
		Double Pwc = 0.0;
		int n = 0;
		int sumTime = 0;
		int currentTime = 0;
		int finalCurrentTime = 1;
		String tempCategory = "";
		String wordName = "";
		
		try{
			for (Iterator it_Test = testVector.keySet().iterator(); it_Test.hasNext();) {
				wordName = String.valueOf(it_Test.next());
				wordName = wordName.replaceAll("\\r\\n", "");
				currentTime = Appear_in_Category(wordName,category);
				if( currentTime != 0 ){
					sumTime += currentTime;
					finalCurrentTime *= currentTime;
					n++;
				}
				
			}
			
			Pwc = Double.valueOf(finalCurrentTime / Math.pow(sumTime, n));
			return Pwc;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return Pwc;
	}
	
	public int Appear_in_Category(String wordName, String category){
		int time = 0;
		
		try{
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("PropertyXML.xml");
			Document doc = builder.build(file);// 获得文档对象
			Element root = doc.getRootElement();// 获得根节点
			List<Element> list = root.getChildren();//获得所有的类
			for( Element e : list ){
				if( e.getAttributeValue("name").equals(category)){
					
					List<Element> list1 = e.getChildren();//获得该类中所有文章
					for( Element e1 : list1 ){
						
						List<Element> list2 = e1.getChildren();//获得该文章中所有词
						for( Element e2 : list2 ){
							
							if( e2.getAttributeValue("name").equals(wordName)){
								time += Integer.valueOf(e2.getChildText("tf"));
							}
							
						}
						
					}
					
				}
			}
			return time;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return time;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

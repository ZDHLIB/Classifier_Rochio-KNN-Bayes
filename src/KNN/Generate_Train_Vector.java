package KNN;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import OperateXML.AddElement;
import OperateXML.VectorXMLGenerate;

public class Generate_Train_Vector {

	/**
	 * create the TrainVector.xml to store the vector of each classify
	 */
	public boolean Create_TrainVector_XML(String storePath){
		boolean flag = false;
		String wordName = "";
		Double tempTfidf = 0.0;
		String tfidf = "";
		String category = "";
		try{
			VectorXMLGenerate CreateXML = new VectorXMLGenerate();
			File WordXML = new File(storePath+"KNN_VectorXML.xml");
			if (!WordXML.exists()) {
				CreateXML.InterfaceGenerate(storePath,"KNN_");
			}
			
			AddElement AddEle = new AddElement();
			TreeMap<String, Double> Vector = new TreeMap<String, Double>();
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("PropertyXML.xml");
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�
			List<Element> list = root.getChildren();//������е����
			for( Element e : list ){
				category = e.getAttributeValue("name");
				List<Element> list1 = e.getChildren();//���������е�����
				for( Element e1 : list1 ){
					List<Element> list2 = e1.getChildren();//����������еĴ�
					for( Element e2 : list2 ){
						wordName = e2.getAttributeValue("name");
						tfidf = e2.getChildText("tfidf");
						//���ÿ����������
						if( Vector.containsKey(wordName) ){
							tempTfidf = Vector.get(wordName);
							Vector.put(wordName, Double.valueOf(tfidf)+tempTfidf);
						} else {
							Vector.put(wordName,Double.valueOf(tfidf));
						}
					}
				}
				AddEle.addVectorElement(category, Vector, "KNN_");
			}
			
		}catch(Exception e){
			e.printStackTrace();

		}
		return flag;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

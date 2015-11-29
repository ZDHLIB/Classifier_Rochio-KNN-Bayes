package KNN;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class KNNClassify {

	public int KNN(ArrayList<TreeMap<String, String>> trainArrayVector, TreeMap<String, Integer> testVector){

		int flag = 0;
		Double tempMinCOS = 0.0;
		Double MinCOS = 0.0;
		String category = "";
		String wordName = "";
		String weight = "";
		ArrayList<TreeMap<String, String>> arrayVector = new ArrayList<TreeMap<String, String>>();
		TreeMap<String, String> Vector = new TreeMap<String, String>();
		
		ArrayList<TreeMap<String, String>> arrayVectorTest = new ArrayList<TreeMap<String, String>>();
		TreeMap<String, String> VectorTest = new TreeMap<String, String>();
		
		try{
			for( int i = 0; i < trainArrayVector.size(); i++ ){
				
				tempMinCOS = Double.valueOf(Calculate_COS(testVector,trainArrayVector.get(i)));
				if( tempMinCOS > MinCOS ){
					MinCOS = tempMinCOS;
					flag = i;
				}
				
			}
			
			return flag;
			/*
			//���½���ѵ��Ԥ�ϵ�������
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("VectorXML.xml");
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�
			List<Element> list = root.getChildren();//������е���
			for( Element e : list ){
				List<Element> list1 = e.getChildren();//����������еĴ�
				for( Element e1 : list1 ){
					wordName = e1.getAttributeValue("name");
					weight = e1.getChildText("weight");
					Vector.put(wordName, weight);
				}
				arrayVector.add(Vector);
				Vector.clear();
			}
			
			//��������Ԥ�ϵ�������
			SAXBuilder builder2 = new SAXBuilder();
			Document doc2 = null;
			File TestFileXML = new File(TestFileXMLParh);
			File [] fileList = TestFileXML.listFiles();
			for( int i = 0; i < fileList.length; i++ ){
				InputStream file2 = new FileInputStream(fileList[i].getAbsolutePath());
				doc2 = builder.build(file2);// ����ĵ�����
				Element rootTest = doc.getRootElement();// ��ø��ڵ�
				List<Element> listTest = root.getChildren();//������е�����
				for( Element e : listTest ){
					List<Element> list1 = e.getChildren();//������������еĴ�
					for( Element e1 : list1 ){
						wordName = e1.getAttributeValue("name");
						weight = e1.getChildText("tf");
						VectorTest.put(wordName, weight);
					}
					arrayVectorTest.add(VectorTest);
					VectorTest.clear();
				}
			}
			
			//�ҳ�COS��С��
			for( int i = 0; i < arrayVectorTest.size(); i++ ){
				for( int j = 0; j < arrayVector.size(); j++ ){
					tempMinCOS = Double.valueOf(Calculate_COS(arrayVector.get(j),arrayVectorTest.get(i)));
					if( tempMinCOS <= MinCOS ){
						MinCOS = tempMinCOS;
						flag = j;
						flagTest = i;
					}
				}
			}
			int m = 0; 
			int n = 0;
			String fileName = "";
			Element cate_Ele = list.get(flag);
			category = cate_Ele.getAttributeValue("name");
			
			n = flagTest / 20;
			m = flagTest % 20;
			
			InputStream file3 = new FileInputStream(fileList[n].getAbsolutePath());
			doc2 = builder.build(file3);// ����ĵ�����
			Element rootTest = doc.getRootElement();// ��ø��ڵ�
			List<Element> listTest = root.getChildren();//������е�����
			Element e = listTest.get(m);
			fileName = e.getAttributeValue("title");
			*/
			
			
		}catch(Exception e){
			
		}
		return flag;
	}
	
	public String Calculate_COS(TreeMap<String, Integer> TrainVector, TreeMap<String, String> TestVector){
		Object testWordName = "";
		String testWordWeight = "";
		Object trainWordName = "";
		Integer trainWordWeight = 0;
		Double numerator = 0.0;
		Double tempTest = 0.0;
		Double tempTrain = 0.0;
		Double temp = 0.0;
		Double COS = 0.0;
		String finalCOS = "";
		
		try{
			
			//������������ƶȵķ���numerator
			for (Iterator it_Test = TestVector.keySet().iterator(); it_Test.hasNext();) {
				testWordName = it_Test.next();
				testWordWeight = TestVector.get(testWordName);
				
				for(Iterator it_Train = TrainVector.keySet().iterator(); it_Train.hasNext();){
					trainWordName = it_Train.next();
					trainWordWeight = TrainVector.get(trainWordName);
					trainWordName = String.valueOf(trainWordName).replaceAll("\\r\\n", "");
					
					if( trainWordName.equals(testWordName) ){
						numerator += Double.valueOf(testWordWeight) * Double.valueOf(trainWordWeight);
					}
				}
			}
			
			for (Iterator it_Test = TestVector.keySet().iterator(); it_Test.hasNext();) {
				testWordName = it_Test.next();
				testWordWeight = TestVector.get(testWordName);
				tempTest += Double.valueOf(testWordWeight) * Double.valueOf(testWordWeight);
			}
			tempTest = Math.sqrt(tempTest);
			
			for (Iterator it_Train = TrainVector.keySet().iterator(); it_Train.hasNext();) {
				trainWordName = it_Train.next();
				trainWordWeight = TrainVector.get(trainWordName);
				tempTrain += Double.valueOf(trainWordWeight) * Double.valueOf(trainWordWeight);
			}
			tempTrain = Math.sqrt(tempTrain);
			
			temp = tempTest * tempTrain;
			
			COS = numerator / temp;
			
			DecimalFormat formatNUM = new DecimalFormat("#0.000");
			finalCOS = formatNUM.format(COS); // result is 123.457 
			
			return finalCOS;
		}catch(Exception e){
			System.out.println("Calculate_COS");
		}
		return finalCOS;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

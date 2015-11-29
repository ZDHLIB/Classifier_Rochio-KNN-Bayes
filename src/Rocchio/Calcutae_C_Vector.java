package Rocchio;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class Calcutae_C_Vector {

	public ArrayList<TreeMap<String, Double>> Rocchio_C_Vector(){
		Double CSum = 1.0;
		Double NCSum = 0.0;
		Double weight = 0.0;
		String wordName = "";
		String category = "";
		
		TreeMap<String, Double> NCVector = new TreeMap<String, Double>();
		ArrayList<TreeMap<String, Double>> arrayCVector = new ArrayList<TreeMap<String, Double>>();
		
		try{
			
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("Rocchio_VectorXML.xml");
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�
			List<Element> list = root.getChildren();//������е���
			for( int i = 0; i < list.size(); i++ ){
				Element e = list.get(i);
				category = e.getAttributeValue("name");
				//CSum�Ǵ�������м�ƪ����
				CSum = Get_SUM_CateFiles(category);
				List<Element> list1 = e.getChildren(); //��ø���������������
				TreeMap<String, Double> CVector = new TreeMap<String, Double>();
				for( Element e1 : list1 ){
					CVector.put(e1.getAttributeValue("name"), Double.valueOf(e1.getChildText("weight")) / CSum);
				}
				System.out.println("��֮ǰ��"+CVector);
				for( int j = 0; j < list.size(); j++ ){
					if( j != i ){
						
						Element e2 = list.get(j);
						category = e2.getAttributeValue("name");
						NCSum += Get_SUM_CateFiles(category);
						List<Element> list2 = e2.getChildren(); //��ø���������������
						for( Element e3 : list2 ){
							wordName = e3.getAttributeValue("name");
							if( NCVector.containsKey(wordName) ){
								weight = NCVector.get(wordName);
								NCVector.put(wordName, Double.valueOf(e3.getChildText("weight"))+weight);
							} else {
								NCVector.put(e3.getAttributeValue("name"), Double.valueOf(e3.getChildText("weight")));
							}
						}
						
					}
				}
				//��NCVector�е�Ȩ�س���NCSum
				Object Key = "";
				Double tempWeight = 0.0;
				for (Iterator it = NCVector.keySet().iterator(); it.hasNext();) {
					Key = it.next();
					tempWeight = NCVector.get(Key);
					tempWeight = tempWeight / NCSum;
					NCVector.put(String.valueOf(Key), tempWeight);
//					System.out.println(tr7.get(it11.next()));// �������tr7�����е�Ԫ��
				}
				
				//��������������� 
				Object NwordName = "";
				Double NWeight = 0.0;
				Object PwordName = "";
				Double PWeight = 0.0;
				for (Iterator it_N = NCVector.keySet().iterator(); it_N.hasNext();) {
					NwordName = it_N.next();
					NWeight = NCVector.get(NwordName);

					if( CVector.containsKey(NwordName) ){
						PWeight = CVector.get(NwordName);
						PWeight = PWeight - NWeight;
						CVector.put(String.valueOf(NwordName), PWeight);
					}
					
				}
				System.out.println("��֮��"+CVector);
				//�洢������������ 
				arrayCVector.add(CVector);
			}
			for( int i = 0; i < arrayCVector.size(); i++ ){
				System.out.println(i+" "+arrayCVector.get(i));
			}
			return arrayCVector;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return arrayCVector;
	}
	
	public Double Get_SUM_CateFiles(String category) {
		Double sum = 1.0;
		try{
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("PropertyXML.xml");
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�
			List<Element> list = root.getChildren();//������е���
			for( Element e : list ){
				if( e.getAttributeValue("name").equals(category)){
					sum = Double.valueOf(e.getChildren().size());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sum;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

package Calculate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import FileOperate.GetAllFilePath;

public class Calculate_TF_IDF {

	/**
	 * 
	 * @param fileName
	 * @param DF
	 * @param tf
	 * @return String
	 * @function calculate the TFIDF of one word in one article, TFIDF = TF * IDF,
	 *           TF = tf / tf_SUM; (It can prevent the influence of the different length of articles)
	 *           IDF = log10( |D| / DF )
	 *           tf_SUM is the sum of all words' tf in one article
	 */
	public ArrayList<Double> Calculate_TFIDF(List<String> pathList, String fileName,String DF,String tf){
		int articleSUM = 0;
		int TF_SUM = 0;
		double TF = 0;
		double IDF = 0;
		double TFIDF = 0;
		String finalTFIDF = "";
		String[] SplitString = new String[] {};
		String[] SplitPath = new String[] {};
		ArrayList<Double> result = new ArrayList<Double>();
		
		try{
/*
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("FileXML.xml");
			Document doc = builder.build(file);// 获得文档对象
			Element root = doc.getRootElement();// 获得根节点
			List<Element> list = root.getChildren();
			articleSUM = list.size();
			for( Element e : list ){
				if( e.getAttributeValue("title").equals(fileName) ){
					List<Element> list1 = e.getChildren();
					for( Element e1 : list1 ){
						TF_SUM += Integer.parseInt(e1.getChildText("tf"));
					}
				}
			}
*/
			GetAllFilePath getAllFilePath = new GetAllFilePath();
			
			articleSUM = pathList.size();
			
			
			TF = (double)Integer.parseInt(tf);
			TF = Math.log10(TF + 1);
			IDF = Math.log10( (double)articleSUM / ( Integer.parseInt(DF) ) );		
			
			TFIDF = TF * IDF;
			
			DecimalFormat formatNUM = new DecimalFormat("#0.000");
			finalTFIDF = formatNUM.format(TFIDF); // result is 123.457 
	
			result.add(IDF);
			result.add(Double.valueOf(finalTFIDF));
		}catch(Exception e){}
		
		
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

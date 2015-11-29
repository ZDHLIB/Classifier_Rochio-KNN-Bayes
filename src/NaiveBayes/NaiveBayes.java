package NaiveBayes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.ictclas4j.bean.SegResult;
import org.ictclas4j.segment.SegTag;
import org.ictclas4j.utility.DebugUtil;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import Calculate.Calculate_Evaluate;
import CreateWordsTable.CreateWordTable;
import CreateWordsTable.UsefulWordsTable;
import FileOperate.GetAllFilePath;
import FileOperate.ReadFromFile;
import FileOperate.WriteFile;
import KNN.KNNClassify;
import OperateXML.AddElement;
import OperateXML.TestFileXMLGenerate;

public class NaiveBayes {

	static private String massege1 = "";
	static private String massege2 = "";
	static private String massege3 = "";
	static private int sum = 0;
	static private int current = 0;
	
	public void setSum(int num){
		sum = num;
	}
	public int getSum(){
		return sum;
	}
	
	public void setCurrent(int now){
		current = now;
	}
	public int getCurrent(){
		return current;
	}
	
	public void setMassege1(String mas){
		massege1 = mas;
	}
	public String getMassege1(){
		return massege1;
	}
	
	public void setMassege2(String mas){
		massege2 = mas;
	}
	public String getMassege2(){
		return massege2;
	}

	public void setMassege3(String mas){
		massege3 = mas;
	}
	public String getMassege3(){
		return massege3;
	}
	
	/**
	 * 
	 * @param dirPath
	 * @param StorePath
	 * @param count
	 * @author U66
	 * @function splite the Testfiles and create FileXML
	 */
	public void SplitTestEachFile(List<String> pathList, String StorePath, int count){
		int n = 0;
		int m = 0;
		int closest = 0;

		String fileName = "";
		String[] SplitPath = new String[] {};
		String fileCategory = "";
		
		
		TreeMap<String, Integer> testVector = new TreeMap<String, Integer>();
		ArrayList<TreeMap<String, String>> trainArrayVector = new ArrayList<TreeMap<String, String>>();
		
		// paths
		GetAllFilePath getAllFilePath = new GetAllFilePath();
		ReadFromFile readFile = new ReadFromFile();
		String readString = new String();
		SegTag segTag = new SegTag(1);
		DebugUtil writeToFile = new DebugUtil();
		CreateWordTable createWordTable = new CreateWordTable();
		UsefulWordsTable secondWordsTable = new UsefulWordsTable();
		Calculate_Evaluate c_Evaluate = new Calculate_Evaluate();
		WriteFile writeFile = new WriteFile();

		try {
	
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("PropertyXML.xml");
			Document doc = builder.build(file);// 获得文档对象
			Element root = doc.getRootElement();// 获得根节点
			List<Element> list = root.getChildren();//获得所有的类
			c_Evaluate.setBAYESCategory(list);
			////////////////////////////////////////////////////////////////////////
			setMassege1("正在Bayes分类!");
			setSum(pathList.size());
			setMassege2("文章总数:"+pathList.size()+"篇");
			for (int i = 0; i < pathList.size(); i++) {
				setMassege3("正在分类第"+(i+1)+"篇");
				setCurrent(i+1);
				writeFile.writeFile(pathList.get(i), "filename", "txt", "GBK");
				SplitPath = pathList.get(i).split("\\\\");
				fileName = SplitPath[SplitPath.length - 1];
				fileCategory = SplitPath[SplitPath.length - 2];
				// Read the file's text saving in readStirng;
				System.out.println("读取文章(<20),"+pathList.get(i));
				readString = readFile.readTxtFile(pathList.get(i),"GBK");;
				SegResult seg_res = segTag.split(readString);
				writeToFile.writeTxtFile("SplitResult.txt", seg_res
						.getFinalResult(), false);
				createWordTable.CreateTable();
				// delete the worthless words
				secondWordsTable.GetWorthlessWords();
				System.out.println("开始建立"+pathList.get(i)+"的testVector。");
				testVector = CreateTestVector(pathList.get(i), count);
				closest = NaiveBayes(testVector);
				c_Evaluate.CBAYES_Evaluate(fileCategory, list.get(closest).getAttributeValue("name"));
				CopyFile(pathList.get(i), StorePath + "NAIVEBAYES\\" + list.get(closest).getAttributeValue("name"),fileName);
			}
			c_Evaluate.C_BAYESEvaluate(c_Evaluate.getBAYESCategory());

		} catch (Exception e) {
		}
	}
	
	public int NaiveBayes(TreeMap<String, Integer> testVector){
		int flag = 0; 
		Double P_c = 0.0;
		Double Pwc = 0.0;
		Double P = 0.0;
		Double sumP = 0.0;
		Double temp_P = 0.0;
		Double Max_P = 0.0;
		String category = "";
		String wordName = "";
		
		Calculate_P_c cal_P_c = new Calculate_P_c();
		Calculate_P_w_c cal_Pwc = new Calculate_P_w_c();
		ArrayList<Double> P_List = new ArrayList<Double>();
		
		try{
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("PropertyXML.xml");
			Document doc = builder.build(file);// 获得文档对象
			Element root = doc.getRootElement();// 获得根节点
			List<Element> list = root.getChildren();//获得所有的类
			for( Element e : list ){
				category = e.getAttributeValue("name");
				P_c = cal_P_c.Calculate_PreValue(category);//计算这一类的先验值
				
				Pwc = cal_Pwc.Calculate_Pwc(testVector, category);
				P = P_c * Pwc;
				sumP += P;
				P_List.add(P);
			}
			
			for( int i = 0; i < P_List.size(); i++ ){
				temp_P = P_List.get(i) / sumP;
				if( temp_P > Max_P ){
					Max_P = temp_P;
					flag = i;
					System.out.println("Max_P="+Max_P+"    flag="+flag);
				}
			}
			return flag;
		}catch( Exception e ){
			e.printStackTrace();
		}
		
		return flag;
	}
	
	
	/**
	 * @param filePath
	 * @param count
	 * @return TreeMap<String, Integer>
	 * 计算出测试预料的vector
	 */
	public TreeMap<String, Integer> CreateTestVector(String filePath, int count){
		String fileXMLPath = "";
		String fileName = "";
		String[] SplitPath = new String[] {};
		String splitWord = "";
		String[] SplitString = new String[] {};
		int m = 0;
		
		TestFileXMLGenerate CreateXML = new TestFileXMLGenerate();
		ReadFromFile ReadFile = new ReadFromFile();
		TreeMap<String, Integer> wordTree = new TreeMap<String, Integer>();
		AddElement AddEle = new AddElement();
		
		try{
			SplitPath = filePath.split("\\\\");
			fileName = SplitPath[SplitPath.length - 1];	
			
			splitWord = ReadFile.readFileByLines("WordsTable.txt");
			SplitString = splitWord.split("#");
			for (int i = 0; i < SplitString.length - 1; i++) {
				for (int j = i + 1; j < SplitString.length; j++) {
					if (SplitString[i].equals(SplitString[j])
							&& (!SplitString[i].equals(" "))) {
						m++;
						SplitString[j] = " ";
					}
				}
				//tf>3的词保留
				if (m > count) {
					wordTree.put(SplitString[i], m);  //vector of test file
				}
				m = 0;
			}

			return wordTree;
//			AddEle.addTestFileChildElement(fileXMLPath, fileName, wordTree);
		}catch(Exception e){}
		return null;
	}
	
	/**
	 * copy file
	 */
	public void CopyFile(String oldPath, String newFoldPath, String fileName){
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			File newfolder = new File(newFoldPath);
			File newfile = new File(newFoldPath+"\\"+fileName);
			if( !newfolder.exists() ){
				newfolder.mkdirs();
			}
			if( !newfile.exists() ){
				newfile.createNewFile();
			}
			
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newfile);
				byte[] buffer = new byte[4444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					// System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

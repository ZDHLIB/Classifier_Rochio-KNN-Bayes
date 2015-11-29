package Rocchio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
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
import OperateXML.AddElement;
import OperateXML.TestFileXMLGenerate;

public class RocchioClassify {

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
	 * @param dirPath,待分类文档路径  
	 * @param StorePath，分类后存储路径
	 * @param count，待分类文档tf的临界值
	 */
	public void Rocchio(List<String> pathList, String StorePath, int count){
		//定义变量
		int closest = 0;
		String fileName = "";
		TreeMap<String, Integer> testVector = new TreeMap<String, Integer>();
		ArrayList<TreeMap<String, Double>> arrayCVector = new ArrayList<TreeMap<String, Double>>();
		String[] SplitPath = new String[] {};
		String fileCategory = "";
		
		//定义对象
		Calcutae_C_Vector RocchioVector = new Calcutae_C_Vector();
		ReadFromFile readFile = new ReadFromFile();
		String readString = new String();
		SegTag segTag = new SegTag(1);
		DebugUtil writeToFile = new DebugUtil();
		CreateWordTable createWordTable = new CreateWordTable();
		UsefulWordsTable secondWordsTable = new UsefulWordsTable();
		Calculate_Evaluate c_Evaluate = new Calculate_Evaluate();
		WriteFile writeFile = new WriteFile();
		//***************************************************************************************
		
		//Rocchio 各个类别的向量集合
		arrayCVector = RocchioVector.Rocchio_C_Vector();
		
		try{
			
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("Rocchio_VectorXML.xml");
			Document doc = builder.build(file);// 获得文档对象
			Element root = doc.getRootElement();// 获得根节点
			List<Element> list = root.getChildren();//获得所有的类
			c_Evaluate.setROCCHIOCategory(list);
			setMassege1("正在Rocchio分类!");
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
				System.out.println("开始计算"+pathList.get(i)+"的closest。");
				closest = VectorCompare(arrayCVector, testVector);
				System.out.println(pathList.get(i)+"的closest="+closest);
				System.out.println(pathList.get(i)+"的closest对应的类别为："+list.get(closest).getAttributeValue("name"));
				c_Evaluate.CROCCHIO_Evaluate(fileCategory, list.get(closest).getAttributeValue("name"));
				CopyFile(pathList.get(i), StorePath + "ROCCHIO\\" + list.get(closest).getAttributeValue("name"),fileName);
			}

			c_Evaluate.C_ROCCHIOEvaluate(c_Evaluate.getROCCHIOCategory());
		}catch(Exception e){
			e.printStackTrace();
		}
		
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
	
	public int VectorCompare(ArrayList<TreeMap<String, Double>> trainArrayVector, TreeMap<String, Integer> testVector){

		int flag = 0;
		Double tempMinCOS = 0.0;
		Double MinCOS = 0.0;
		
		try{
			for( int i = 0; i < trainArrayVector.size(); i++ ){
				
				tempMinCOS = Double.valueOf(Calculate_COS(testVector,trainArrayVector.get(i)));
				if( tempMinCOS > MinCOS ){
					MinCOS = tempMinCOS;
					flag = i;
				}
				
			}
			
			return flag;			
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public String Calculate_COS(TreeMap<String, Integer> TrainVector, TreeMap<String, Double> TestVector){
		Object testWordName = "";
		Double testWordWeight = 0.0;
		Object trainWordName = "";
		Integer trainWordWeight = 0;
		Double numerator = 0.0;
		Double tempTest = 0.0;
		Double tempTrain = 0.0;
		Double temp = 0.0;
		Double COS = 0.0;
		String finalCOS = "";
		
		try{
			
			//计算出余弦相似度的分子numerator
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

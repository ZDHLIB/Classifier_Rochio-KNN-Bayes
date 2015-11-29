package KNN;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.ictclas4j.bean.SegResult;
import org.ictclas4j.segment.SegTag;
import org.ictclas4j.utility.DebugUtil;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import Calculate.Calculate_Evaluate;
import CreateWordsTable.CreateWordTable;
import CreateWordsTable.UsefulWordsTable;
import FileOperate.GetAllFilePath;
import FileOperate.ReadFromFile;
import FileOperate.WriteFile;
import InterfaceMain.SplitWords;
import OperateXML.AddElement;
import OperateXML.FileXmlGenerate;
import OperateXML.TestFileXMLGenerate;
import FileOperate.ReadFromFile;

public class Generate_Test_Vector {

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

		int closest = 0;
		
		String wordName = "";
		String weight = "";
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
		KNNClassify KNN = new KNNClassify();
		Calculate_Evaluate c_Evaluate = new Calculate_Evaluate();
		WriteFile writeFile = new WriteFile();

		try {
			
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("KNN_VectorXML.xml");
			Document doc = builder.build(file);// 获得文档对象
			Element root = doc.getRootElement();// 获得根节点
			List<Element> list = root.getChildren();//获得所有的类
			
			c_Evaluate.setKNNCategory(list);
			
			for( Element e : list ){
				TreeMap<String, String> trainVector = new TreeMap<String, String>();
				List<Element> list1 = e.getChildren();//获得类中所有的词
				for( Element e1 : list1 ){
					wordName = e1.getAttributeValue("name");
					weight = e1.getChildText("weight");
					trainVector.put(wordName, weight);
				}
				trainArrayVector.add(trainVector);
			}
			
			
			setMassege1("正在KNN分类!");
			setSum(pathList.size());
			setMassege2("文章总数:"+pathList.size()+"篇");
			////////////////////////////////////////////////////////////////////////
			
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
				closest = KNN.KNN(trainArrayVector, testVector);
				System.out.println(pathList.get(i)+"的closest="+closest);
				System.out.println(pathList.get(i)+"的closest对应的类别为："+list.get(closest).getAttributeValue("name"));
				
				c_Evaluate.CKNN_Evaluate(fileCategory, list.get(closest).getAttributeValue("name"));
				
				CopyFile(pathList.get(i),StorePath + "KNN\\" + list.get(closest).getAttributeValue("name"),fileName);
			}
			
			c_Evaluate.C_KNNEvaluate(c_Evaluate.getKNNCategory());

		} catch (Exception e) {
		}
	}
	
	/**
	 * @param filePath
	 * @param count
	 * @return TreeMap<String, Integer>
	 * 计算出测试预料的vector
	 */
	public TreeMap<String, Integer> CreateTestVector(String filePath, int count){

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
	
	
	
	public void Create_Test_Vector(String fileXMLPath){
		String titleName = "";
		String wordName = "";
		String tf = "";
		try{
			SAXBuilder builder = new SAXBuilder();
			Document doc = null;
			
			SAXBuilder builder2 = new SAXBuilder();
			Document doc2 = null;
			
			
			File fileXML = new File(fileXMLPath);
			File[] fileXMLList = fileXML.listFiles();
			System.out.println(fileXMLList.length);
			for (int i = 0; i < fileXMLList.length; i++) {
				InputStream file = new FileInputStream(fileXMLList[i]
						.getAbsolutePath());
				doc = builder.build(file);// 获得文档对象
				Element root = doc.getRootElement();// 获得根节点
				List<Element> list = root.getChildren();//list 为所有文章
				for (Element e : list) {
					titleName = e.getAttributeValue("title");
					List<Element> wordList = e.getChildren();
					// e1为该篇文章下的词
					for (Element e1 : wordList) {
						wordName = e1.getAttributeValue("name");
						tf = e1.getChildText("tf");
						
						InputStream file2 = new FileInputStream("PropertyXML.xml");
						doc2 = builder2.build(file);// 获得文档对象
						Element root2 = doc2.getRootElement();// 获得根节点
						List<Element> list2 = root.getChildren();
					}
				}
			}
		}catch(Exception e){
			System.out.println("Create_Test_Vector(String fileXMLPath){");
		}
	}
	
	
	/**
	 * @param fileName
	 * @return
	 * @function check the XML's file whether it has the element of 'fileName'
	 */
	public boolean CheckArticle(String fileName, String fileXMLPath) {
		boolean flag = false;
		try {
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream(fileXMLPath);
			Document doc = builder.build(file);// 获得文档对象
			Element root = doc.getRootElement();// 获得根节点
			List<Element> list = root.getChildren();
			for (Element e : list) {
				// 获取ID值
				if (e.getAttributeValue("title").equals(fileName)) {
					flag = true;
					break;
				}
			}
		} catch (Exception e) {
		}
		return flag;
	}
	
	public boolean formatXML(String fileName) {
		boolean isOk = false;
		try {
			SAXReader reader = new SAXReader();
			org.dom4j.Document doc = reader.read(new File(fileName));
			XMLWriter formatWriter = null;
			/**
			 *格式化输出,类型IE浏览一样 指定XML编码
			 */
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");
			formatWriter = new XMLWriter(new FileOutputStream(fileName), format);
			formatWriter.write(doc);
			formatWriter.close();

			isOk = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOk;
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

		String storePath = "E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\ResultFiles\\";
		String dirPath = "E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\SourceFiles\\TestFile\\";
		Generate_Test_Vector vector = new Generate_Test_Vector();
//		vector.SplitTestEachFile(dirPath,storePath,2);
	}

}

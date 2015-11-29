package InterfaceMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import Calculate.Calculate_TF_IDF;
import CreateWordsTable.CreateWordTable;
import CreateWordsTable.UsefulWordsTable;
import FileOperate.GetAllFilePath;
import FileOperate.ReadFromFile;
import GUI.MainPanel;
import KNN.Generate_Test_Vector;
import KNN.Generate_Train_Vector;
import NaiveBayes.NaiveBayes;
import OperateXML.AddElement;
import OperateXML.FileXmlGenerate;
import OperateXML.PropertyXMLGenerate;
import OperateXML.WordXMLGenerate;
import Rocchio.Generate_Rocchio_Train_Vector;
import Rocchio.RocchioClassify;

public class SplitWords {

	/**
	 * @param args
	 * @author 钟敦昊
	 * @function 对目录下的文件进行扫描，并对每篇的文章进行分词,并建立以文章为根节点的xml文件
	 * 
	 */
	
	static private String Path = "";
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
	
	/*
	 * dirPath:语料路径
	 * StorePath:存储路径
	 * count:tf筛选条件,tf>count的词保留
	 */
	public void SplitEachFile(List<String> pathList, String StorePath, int count) {
		int n = 0;
		int m = 0;
		
		
		ReadFromFile readFile = new ReadFromFile();
		String readString = new String();
		SegTag segTag = new SegTag(1);
		DebugUtil writeToFile = new DebugUtil();
		CreateWordTable createWordTable = new CreateWordTable();
		UsefulWordsTable secondWordsTable = new UsefulWordsTable();
		
		
		try {
			setSum(pathList.size());
			setMassege1("正在分词!");
			setMassege2("训练语料:"+pathList.size()+"篇");
			if (pathList.size() < 20) {
				
				for (int i = 0; i < pathList.size(); i++) {
					setCurrent(i+1);
					setMassege3("处理第"+(i+1)+"篇");
					// Read the file's text saving in readStirng;
					System.out.println("读取文章(<20)");
					readString = readFile.readTxtFile(pathList.get(i),"GBK");
//					System.out.println(readString);
					SegResult seg_res = segTag.split(readString);
					writeToFile.writeTxtFile("SplitResult.txt", seg_res
							.getFinalResult(), false);
					createWordTable.CreateTable();
					// delete the worthless words
					secondWordsTable.GetWorthlessWords();
					CreateFileXML(StorePath, pathList.get(i), 0, count);
				}
			} else {
				n = pathList.size() / 20;
				m = pathList.size() % 20;
				for (int i = 0; i < n; i++) {
					
					for (int j = (i * 20); j < ((i * 20) + 20); j++) {
						// Read the file's text saving in readStirng;
						setCurrent(j);
						setMassege3("处理第"+(j+1)+"篇");
						System.out.println("总文章数：" + pathList.size() + ",余数m="
								+ m + ",读取文章(>20)" + "第" + (j+1) + "篇文章");
						System.out.println(pathList.get(j));
						readString = readFile.readTxtFile(pathList.get(j),"GBK");
//						System.out.println(readString);
						try{
							SegResult seg_res = segTag.split(readString);
							writeToFile.writeTxtFile("SplitResult.txt", seg_res
									.getFinalResult(), false);
						}catch(Exception e){
							e.printStackTrace();
							continue;
						}
						
						createWordTable.CreateTable();
						// delete the worthless words
						secondWordsTable.GetWorthlessWords();
						CreateFileXML(StorePath, pathList.get(j), i, count);
					}
				}
				for (int k = n * 20; k < (n * 20) + m; k++) {
					setCurrent(k+1);
					setMassege3("处理第"+(k+1)+"篇");
					System.out.println("剩下的。总文章数：" + pathList.size()
							+ ",读取文章(>20)" + "第" + k + 1 + "篇文章");
					System.out.println(pathList.get(k));
					// Read the file's text saving in readStirng;
					readString = readFile.readTxtFile(pathList.get(k),"GBK");
//					System.out.println(readString);
					SegResult seg_res = segTag.split(readString);
					writeToFile.writeTxtFile("SplitResult.txt", seg_res
							.getFinalResult(), false);
					createWordTable.CreateTable();
					// delete the worthless words
					secondWordsTable.GetWorthlessWords();
					CreateFileXML(StorePath, pathList.get(k), n, count);
				}
			}

		} catch (Exception e) {
		}
	}

	/**
	 * Create XML based on file name, the file's root is file's name and each
	 * filename includes the words without worthlesswords
	 * 
	 * @throws IOException
	 * @throws JDOMException
	 */
	public boolean CreateFileXML(String storePath, String filePath, int n, int count) throws JDOMException,
			IOException {

		boolean flag = false;
		int m = 0;
		String fileXMLPath = "";
		String fileCategory = "";
		String fileName = "";
		String splitWord = "";
		String[] SplitString = new String[] {};
		String[] SplitPath = new String[] {};
		TreeMap<String, Integer> wordTree = new TreeMap<String, Integer>();

		ReadFromFile ReadFile = new ReadFromFile();
		FileXmlGenerate CreateXML = new FileXmlGenerate();
		AddElement AddEle = new AddElement();

		SplitPath = filePath.split("\\\\");
		fileName = SplitPath[SplitPath.length - 1];
		fileCategory = SplitPath[SplitPath.length - 2];

		fileXMLPath = storePath+"/FileXML_" + String.valueOf(n) + ".xml";
//		System.out.println("fileCategory="+fileCategory);
		System.out.println(fileXMLPath);
		File articleXML = new File(fileXMLPath);
		if (!articleXML.exists()) {
			CreateXML.InterfaceGenerate(fileXMLPath, fileName, fileCategory);
		}

		if (!CheckArticle(fileName, fileXMLPath)) {
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream(fileXMLPath);
			Document doc = builder.build(file);// 获得文档对象
			Element root = doc.getRootElement();
			Element ArticleElement = new Element("article");
			ArticleElement.setAttribute("title", fileName);

			Element CategoryElement = new Element("category");
			CategoryElement.setAttribute("name", fileCategory);

			ArticleElement.addContent(CategoryElement);
			root.addContent(ArticleElement);
			// 文件处理
			XMLOutputter out = new XMLOutputter();
			out.output(doc, new FileOutputStream(fileXMLPath));
			formatXML(fileXMLPath);
		}
		System.out.println("读取词表,计算词频");
		splitWord = ReadFile.readTxtFile("WordsTable.txt","GBK");
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
				wordTree.put(SplitString[i], m);
			}
			m = 0;
		}
		AddEle.addChildElement(fileXMLPath, fileName, wordTree);
		return flag;
	}

	/**
	 * Create XML based on word's name, the file's root is word's name and each
	 * wordname includes the para of df and the articles which are contained it;
	 * storePath为保存WordXML的路径，以“\\”结尾
	 */
	public boolean CreateWordXML(String storePath, String storeFileXMLPath, int maxcount) {
		boolean flag = false;
		String count = "";
		String wordName = "";
		String titleName = "";
		String category = "";

		AddElement AddWordEle = new AddElement();
		WordXMLGenerate CreateXML = new WordXMLGenerate();

		File WordXML = new File(storePath+"WordXML.xml");
		if (!WordXML.exists()) {
			CreateXML.InterfaceGenerate(storePath);
		}

		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = null;
			File fileXML = new File(storeFileXMLPath + "FirstFileXMLFolder");
			
			File[] fileXMLList = fileXML.listFiles();
			System.out.println(fileXMLList.length);
			setMassege1("正在统计特征向量!");
			setSum(fileXMLList.length);
			setMassege2("共"+fileXMLList.length+"个文件");
			// 扫描FileXMLFolder文件夹下的文件，对每个文件进行统计
			for (int i = 0; i < fileXMLList.length; i++) {
				setMassege3("处理第"+(i+1)+"个文件");
				setCurrent(i+1);
				InputStream file = new FileInputStream(fileXMLList[i]
						.getAbsolutePath());
				doc = builder.build(file);// 获得文档对象
				Element root = doc.getRootElement();// 获得根节点
				List<Element> list = root.getChildren();
				
				for (Element e : list) {
					titleName = e.getAttributeValue("title");
					category = e.getChild("category").getAttributeValue("name");
					List<Element> wordList = e.getChild("category").getChildren();
					// e1为该篇文章下的词
					for (Element e1 : wordList) {
						wordName = e1.getAttributeValue("name");
						count = e1.getChildText("tf");
						AddWordEle.addWordChildElement(wordName, count, titleName, category);
					}
				}
			}
			
			ArrayList<Element> remove = new ArrayList<Element>();
			ArrayList<Element> remove2 = new ArrayList<Element>();
			InputStream file2 = new FileInputStream(storePath+"WordXML.xml");
			doc = builder.build(file2);
			Element root2 = doc.getRootElement();// 获得根节点
			List<Element> list2 = root2.getChildren(); // list2为所有的词
			for (Element e2 : list2) {
				List<Element> list3 = e2.getChildren();//词下的所有类
				for( Element e3 : list3 ){
					List<Element> list4 = e3.getChild("Source").getChildren();
					
					e3.getChild("DF").setAttribute("name", String.valueOf(list4.size()));
				}
					
			}
			
			int df = 0;
			//将所有的DF加起来，删除小于3的词
			for (Element e2 : list2) {
				List<Element> list3 = e2.getChildren();//词下的所有类
				for( Element e3 : list3 ){
					df += Integer.valueOf(e3.getChild("DF").getAttributeValue("name"));
				}
				if( df < maxcount ){
					remove.add(e2);
				}
				df = 0;
			}
			
			
			for(int i = 0; i < remove.size(); i++){ // 删除节点elem
				root2.removeContent(remove.get(i)); }
			 

			// 文件处理
			XMLOutputter out = new XMLOutputter();
			out.output(doc, new FileOutputStream(storePath+"WordXML.xml"));
			formatXML(storePath+"WordXML.xml");
			/**/
			

		} catch (Exception e) {
		}
		return flag;
	}

	public boolean CreatePropertyXML( ArrayList<String> trainList) {
		int i = 1;
		boolean flag = false;
		String category = "";
		String fileName = "";
		String wordName = "";
		String tf = "";
		String df = "";
		int DF = 0;
		String TFIDF = "";
		ArrayList<Double> result = new ArrayList<Double>();

		PropertyXMLGenerate CreateXML = new PropertyXMLGenerate();
		AddElement AddProperty = new AddElement();
		Calculate_TF_IDF Cal_TFIDF = new Calculate_TF_IDF();

		File WordXML = new File("PropertyXML.xml");
		if (!WordXML.exists()) {
			CreateXML.InterfaceGenerate();
		}

		try {
			SAXBuilder builder = new SAXBuilder();
			InputStream file = new FileInputStream("WordXML.xml");
			Document doc = builder.build(file);// 获得文档对象
			Element root = doc.getRootElement();// 获得根节点
			List<Element> list = root.getChildren();//获得所有的词
			setMassege1("整理特征向量!");
			setSum(list.size());
			setMassege2("特征向量:"+list.size()+"个");
			for (Element e : list) {
				setMassege3("整理第"+i+"个");
				setCurrent(i);
				i++;
				wordName = e.getAttributeValue("name");
				
				List<Element> list2 = e.getChildren();//词下的所有类
				for( Element e2 : list2 ){
					DF += Integer.valueOf(e2.getChild("DF").getAttributeValue("name"));
				}
				df = String.valueOf(DF);
				for (Element e2 : list2) {
					List<Element> list3 = e2.getChild("Source").getChildren();
					category = e2.getAttributeValue("name");
					for( Element e3 : list3 ){
						fileName = e3.getAttributeValue("title");
						tf = e3.getChildText("tf");
						result = Cal_TFIDF.Calculate_TFIDF(trainList, fileName, df, tf);
						AddProperty.addPropertyElement(category, fileName, wordName, tf, df, String.valueOf(result.get(0)), String.valueOf(result.get(1)));
					}
				}
				DF = 0;
			}

		} catch (Exception e) {
		}

		return flag;
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
			format.setEncoding("UTF-8");
			formatWriter = new XMLWriter(new FileOutputStream(fileName), format);
			formatWriter.write(doc);
			formatWriter.close();

			isOk = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOk;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 使用训练预料产生分类器 
		Path = "E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\SourceFiles\\Sample2\\";
		SplitWords testSplit = new SplitWords();
		Generate_Test_Vector generate_Test_Vector = new Generate_Test_Vector();
		Generate_Train_Vector TrainVector = new Generate_Train_Vector();
//		testSplit.SplitEachFile(Path, "FirstFileXMLFolder", 0);
//		testSplit.CreateWordXML("","",5);
//		testSplit.CreatePropertyXML();
		
		// 使用KNN算法进行分类
		SplitWords testSplit_KNN = new SplitWords();
//		TrainVector.Create_TrainVector_XML("");
//		generate_Test_Vector.SplitTestEachFile("E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\SourceFiles\\TestFile\\", "E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\ResultFiles\\", 3);
		
		// 使用Rocchio算法进行分类
		RocchioClassify rocchioClassify = new RocchioClassify();
		Generate_Rocchio_Train_Vector trainVector = new Generate_Rocchio_Train_Vector();
//		trainVector.Create_TrainVector_XML("");
//		rocchioClassify.Rocchio("E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\SourceFiles\\TestFile\\", "E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\ResultFiles\\", 4);
		
		// 使用Naive Bayes算法进行分类
//      NaiveBayes bayes = new NaiveBayes();
//		bayes.SplitTestEachFile("E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\SourceFiles\\TestFile\\", "E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\ResultFiles\\", 4);
	}

}

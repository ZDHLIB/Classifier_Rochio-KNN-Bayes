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
	 * @author �Ӷ��
	 * @function ��Ŀ¼�µ��ļ�����ɨ�裬����ÿƪ�����½��зִ�,������������Ϊ���ڵ��xml�ļ�
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
	 * dirPath:����·��
	 * StorePath:�洢·��
	 * count:tfɸѡ����,tf>count�Ĵʱ���
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
			setMassege1("���ڷִ�!");
			setMassege2("ѵ������:"+pathList.size()+"ƪ");
			if (pathList.size() < 20) {
				
				for (int i = 0; i < pathList.size(); i++) {
					setCurrent(i+1);
					setMassege3("�����"+(i+1)+"ƪ");
					// Read the file's text saving in readStirng;
					System.out.println("��ȡ����(<20)");
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
						setMassege3("�����"+(j+1)+"ƪ");
						System.out.println("����������" + pathList.size() + ",����m="
								+ m + ",��ȡ����(>20)" + "��" + (j+1) + "ƪ����");
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
					setMassege3("�����"+(k+1)+"ƪ");
					System.out.println("ʣ�µġ�����������" + pathList.size()
							+ ",��ȡ����(>20)" + "��" + k + 1 + "ƪ����");
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
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();
			Element ArticleElement = new Element("article");
			ArticleElement.setAttribute("title", fileName);

			Element CategoryElement = new Element("category");
			CategoryElement.setAttribute("name", fileCategory);

			ArticleElement.addContent(CategoryElement);
			root.addContent(ArticleElement);
			// �ļ�����
			XMLOutputter out = new XMLOutputter();
			out.output(doc, new FileOutputStream(fileXMLPath));
			formatXML(fileXMLPath);
		}
		System.out.println("��ȡ�ʱ�,�����Ƶ");
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
			//tf>3�Ĵʱ���
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
	 * storePathΪ����WordXML��·�����ԡ�\\����β
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
			setMassege1("����ͳ����������!");
			setSum(fileXMLList.length);
			setMassege2("��"+fileXMLList.length+"���ļ�");
			// ɨ��FileXMLFolder�ļ����µ��ļ�����ÿ���ļ�����ͳ��
			for (int i = 0; i < fileXMLList.length; i++) {
				setMassege3("�����"+(i+1)+"���ļ�");
				setCurrent(i+1);
				InputStream file = new FileInputStream(fileXMLList[i]
						.getAbsolutePath());
				doc = builder.build(file);// ����ĵ�����
				Element root = doc.getRootElement();// ��ø��ڵ�
				List<Element> list = root.getChildren();
				
				for (Element e : list) {
					titleName = e.getAttributeValue("title");
					category = e.getChild("category").getAttributeValue("name");
					List<Element> wordList = e.getChild("category").getChildren();
					// e1Ϊ��ƪ�����µĴ�
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
			Element root2 = doc.getRootElement();// ��ø��ڵ�
			List<Element> list2 = root2.getChildren(); // list2Ϊ���еĴ�
			for (Element e2 : list2) {
				List<Element> list3 = e2.getChildren();//���µ�������
				for( Element e3 : list3 ){
					List<Element> list4 = e3.getChild("Source").getChildren();
					
					e3.getChild("DF").setAttribute("name", String.valueOf(list4.size()));
				}
					
			}
			
			int df = 0;
			//�����е�DF��������ɾ��С��3�Ĵ�
			for (Element e2 : list2) {
				List<Element> list3 = e2.getChildren();//���µ�������
				for( Element e3 : list3 ){
					df += Integer.valueOf(e3.getChild("DF").getAttributeValue("name"));
				}
				if( df < maxcount ){
					remove.add(e2);
				}
				df = 0;
			}
			
			
			for(int i = 0; i < remove.size(); i++){ // ɾ���ڵ�elem
				root2.removeContent(remove.get(i)); }
			 

			// �ļ�����
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
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�
			List<Element> list = root.getChildren();//������еĴ�
			setMassege1("������������!");
			setSum(list.size());
			setMassege2("��������:"+list.size()+"��");
			for (Element e : list) {
				setMassege3("�����"+i+"��");
				setCurrent(i);
				i++;
				wordName = e.getAttributeValue("name");
				
				List<Element> list2 = e.getChildren();//���µ�������
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
			Document doc = builder.build(file);// ����ĵ�����
			Element root = doc.getRootElement();// ��ø��ڵ�
			List<Element> list = root.getChildren();
			for (Element e : list) {
				// ��ȡIDֵ
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
			 *��ʽ�����,����IE���һ�� ָ��XML����
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
		// ʹ��ѵ��Ԥ�ϲ��������� 
		Path = "E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\SourceFiles\\Sample2\\";
		SplitWords testSplit = new SplitWords();
		Generate_Test_Vector generate_Test_Vector = new Generate_Test_Vector();
		Generate_Train_Vector TrainVector = new Generate_Train_Vector();
//		testSplit.SplitEachFile(Path, "FirstFileXMLFolder", 0);
//		testSplit.CreateWordXML("","",5);
//		testSplit.CreatePropertyXML();
		
		// ʹ��KNN�㷨���з���
		SplitWords testSplit_KNN = new SplitWords();
//		TrainVector.Create_TrainVector_XML("");
//		generate_Test_Vector.SplitTestEachFile("E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\SourceFiles\\TestFile\\", "E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\ResultFiles\\", 3);
		
		// ʹ��Rocchio�㷨���з���
		RocchioClassify rocchioClassify = new RocchioClassify();
		Generate_Rocchio_Train_Vector trainVector = new Generate_Rocchio_Train_Vector();
//		trainVector.Create_TrainVector_XML("");
//		rocchioClassify.Rocchio("E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\SourceFiles\\TestFile\\", "E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\ResultFiles\\", 4);
		
		// ʹ��Naive Bayes�㷨���з���
//      NaiveBayes bayes = new NaiveBayes();
//		bayes.SplitTestEachFile("E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\SourceFiles\\TestFile\\", "E:\\JAVA\\ProgramFiles\\ZZ_FileSeperate_TestFiles\\ResultFiles\\", 4);
	}

}

package GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFileChooser; 

import com.swtdesigner.SwingResourceManager;

import Calculate.Calculate_Evaluate;
import FileOperate.GetAllFilePath;
import InterfaceMain.SplitWords;
import KNN.Generate_Test_Vector;
import KNN.Generate_Train_Vector;
import NaiveBayes.NaiveBayes;
import Rocchio.Generate_Rocchio_Train_Vector;
import Rocchio.RocchioClassify;

public class Setting extends JPanel {

	
	private final JLabel SettingLabel = new JLabel();
	private final JLabel mainbackLabel = new JLabel();
	
	private final JLabel trainSetting = new JLabel();
	private final JLabel trainTF = new JLabel();
	private final JTextField trainTFtextField = new JTextField();
	private final JLabel trainD = new JLabel();
	private final JTextField trainDFtextField = new JTextField();
	private final JLabel testSetting_1 = new JLabel();
	private final JLabel testTF = new JLabel();
	private final JTextField testTFtextField = new JTextField();
	private final JButton startButton = new JButton();
	private final JButton backButton = new JButton();
	private final JLabel idfLabel = new JLabel();
	private final JLabel textSourceLabel = new JLabel();
	private final JTextField sourceField = new JTextField();
	private final JButton sourcebrowseButton = new JButton();
	private final JLabel testStoreLabel = new JLabel();
	private final JTextField testStoreField = new JTextField();
	private final JButton testStorebrowseButton = new JButton();
	private final JLabel storeLabel = new JLabel();
	private final JTextField storeField = new JTextField();
	private final JButton storebrowseButton = new JButton();
	JFileChooser jfc = new JFileChooser();

	private static int flag = 0;
	private static int steps = 0; //steps == 1:apear, steps == 0:disapear
	private static String trainTFtext = "";
	private static String trainDFtext = "";
	private static String testTFtext = "";
	private static String sourcePathtext = "";
	private static String testStorePathtext = "";
	private static String storePathtext = "";
	private String massege1 = "";
	private String massege2 = "";
	private String massege3 = "";
	
	public void setSteps(int step){		
		steps = step;	
		if( steps == 0 ){
			trainTFtext = trainTFtextField.getText();
			trainDFtext = trainDFtextField.getText();
			testTFtext = testTFtextField.getText();
			sourcePathtext = sourceField.getText() + "\\";
			testStorePathtext = testStoreField.getText() + "\\";
			storePathtext = storeField.getText() + "\\";
			
			DataPrepare dataPrepare = new DataPrepare();
			dataPrepare.setSteps(1);
		}
		if( steps == 2 ){
			steps = 0;
			AlgorithmPanel algorithmPanel = new AlgorithmPanel();
			algorithmPanel.setSteps(1);
		}
	}
	
	public int getSteps(){
		return steps;
	}
	
	public String getTrainTF(){
		return trainTFtext;
	}
	
	public String gettrainD(){
		return trainDFtext;
	}
	
	public String getTestTF(){
		return testTFtext;
	}
	
	public String getSourcePath(){
		return sourcePathtext;
	}
	
	public String getTestStorePathtext(){
		return testStorePathtext;
	}
	
	public String getStorePathtext(){
		return storePathtext;
	}
	
	public int getFlag(){
		return flag;
	}
	
	public void setFlag( int n ){
		flag = n;
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
	 * Create the panel
	 */
	public Setting() {
		super();
		try {
			jbInit();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//
	}
	private void jbInit() throws Exception {
		setBackground(new Color(0, 51, 153));
		setLayout(null);
		setBounds(150, 0, 444, 422);
		
		add(SettingLabel);
		SettingLabel.setBounds(0, 0, 444, 85);
		SettingLabel.setOpaque(true);
		SettingLabel.setBackground(new Color(255, 102, 0));
		SettingLabel.setIcon(SwingResourceManager.getIcon(AlgorithmPanel.class, "/Img/background2.jpg"));
		
		add(trainSetting);
		trainSetting.setForeground(new Color(128, 0, 0));
		trainSetting.setFont(new Font("", Font.BOLD, 16));
		trainSetting.setText("训练语料：");
		trainSetting.setBounds(22, 103, 93, 23);
		
		add(trainTF);
		trainTF.setFont(new Font("", Font.BOLD, 14));
		trainTF.setText("TF   >   ");
		trainTF.setBounds(159, 135, 50, 23);
		
		add(trainTFtextField);
		trainTFtextField.setText("0");
		trainTFtextField.setBounds(207, 136, 54, 22);
		
		add(trainD);
		trainD.setFont(new Font("Dialog", Font.BOLD, 14));
		trainD.setText("D   >  ");
		trainD.setBounds(297, 136, 42, 23);
		
		add(trainDFtextField);
		trainDFtextField.setText("0");
		trainDFtextField.setBounds(338, 137, 54, 22);
		
		add(testSetting_1);
		testSetting_1.setForeground(new Color(128, 0, 0));
		testSetting_1.setFont(new Font("Dialog", Font.BOLD, 16));
		testSetting_1.setText("测试语料：");
		testSetting_1.setBounds(22, 172, 93, 23);
		
		add(testTF);
		testTF.setFont(new Font("Dialog", Font.BOLD, 14));
		testTF.setText("TF   >   ");
		testTF.setBounds(70, 202, 50, 23);
		
		add(testTFtextField);
		testTFtextField.setText("0");
		testTFtextField.setBounds(126, 203, 54, 22);
		
		add(startButton);
		startButton.addMouseListener(new StartButtonMouseListener());
		startButton.setText("Start");
		startButton.setBounds(321, 373, 85, 23);
		
		add(backButton);
		backButton.addMouseListener(new BackButtonMouseListener());
		backButton.setText("Back");
		backButton.setBounds(211, 373, 85, 23);
		
		add(idfLabel);
		idfLabel.setFont(new Font("", Font.BOLD, 14));
		idfLabel.setText("TFIDF:");
		idfLabel.setBounds(70, 137, 68, 18);
		
		add(textSourceLabel);
		textSourceLabel.setForeground(new Color(128, 0, 0));
		textSourceLabel.setFont(new Font("", Font.BOLD, 16));
		textSourceLabel.setText("语料来源：");
		textSourceLabel.setBounds(22, 245, 93, 23);
		
		add(sourceField);
		sourceField.setBounds(124, 247, 189, 22);
		
		add(sourcebrowseButton);
		sourcebrowseButton.addMouseListener(new SourcebrowseButtonMouseListener());
		sourcebrowseButton.setFont(new Font("", Font.BOLD, 11));
		sourcebrowseButton.setText("Browse");
		sourcebrowseButton.setBounds(328, 247, 77, 22);
		
		add(testStoreLabel);
		testStoreLabel.setForeground(new Color(128, 0, 0));
		testStoreLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		testStoreLabel.setText("训练集：");
		testStoreLabel.setBounds(22, 287, 93, 23);
		
		add(testStoreField);
		testStoreField.setBounds(124, 288, 189, 22);
		
		add(testStorebrowseButton);
		testStorebrowseButton.addMouseListener(new TestStorebrowseButtonMouseListener());
		testStorebrowseButton.setFont(new Font("Dialog", Font.BOLD, 11));
		testStorebrowseButton.setText("Browse");
		testStorebrowseButton.setBounds(328, 288, 77, 22);
		
		add(storeLabel);
		storeLabel.setForeground(new Color(128, 0, 0));
		storeLabel.setFont(new Font("", Font.BOLD, 16));
		storeLabel.setText("测试集：");
		storeLabel.setBounds(22, 329, 93, 23);
		
		add(storeField);
		storeField.setBounds(124, 330, 189, 22);
		
		add(storebrowseButton);
		storebrowseButton.addMouseListener(new StorebrowseButtonMouseListener());
		storebrowseButton.setFont(new Font("Dialog", Font.BOLD, 11));
		storebrowseButton.setText("Browse");
		storebrowseButton.setBounds(328, 330, 77, 22);
		
		add(mainbackLabel);
		mainbackLabel.setBounds(0, 89, 444, 333);
		mainbackLabel.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/mainback.jpg"));
	}
	
	
	private class SourcebrowseButtonMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			sourcebrowseButton_mouseClicked(e);
		}
	}
	private class TestStorebrowseButtonMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			testStorebrowseButton_mouseClicked(e);
		}
	}
	private class StorebrowseButtonMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			storebrowseButton_mouseClicked(e);
		}
	}
	private class BackButtonMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			backButton_mouseClicked(e);
		}
	}
	private class StartButtonMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			startButton_mouseClicked(e);
		}
	}
	
	
	protected void sourcebrowseButton_mouseClicked(MouseEvent e) {
		if( e.getSource().equals(sourcebrowseButton)){
			jfc.setFileSelectionMode(1);// 设定只能选择到文件夹   
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句   
            if (state == 1) {  
                return;  
            } else {  
                File file = jfc.getSelectedFile();// f为选择到的目录   
                sourceField.setText(file.getAbsolutePath());  
            }
		}
	}
	
	protected void testStorebrowseButton_mouseClicked(MouseEvent e) {
		if( e.getSource().equals(testStorebrowseButton)){
			jfc.setFileSelectionMode(1);// 设定只能选择到文件夹   
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句   
            if (state == 1) {  
                return;  
            } else {  
                File file = jfc.getSelectedFile();// f为选择到的目录   
                testStoreField.setText(file.getAbsolutePath());  
            }
		}
	}
	
	protected void storebrowseButton_mouseClicked(MouseEvent e) {
		if( e.getSource().equals(storebrowseButton)){
			jfc.setFileSelectionMode(1);// 设定只能选择到文件夹   
            int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句   
            if (state == 1) {  
                return;  
            } else {  
                File file = jfc.getSelectedFile();// f为选择到的目录   
                storeField.setText(file.getAbsolutePath());  
            }
		}
	}
	protected void backButton_mouseClicked(MouseEvent e) {
		if( e.getSource().equals(backButton)){
			setSteps(2);
		}
	}
	protected void startButton_mouseClicked(MouseEvent e) {
		if( e.getSource().equals(startButton)){
			
			if( sourceField.getText().equals("") || storeField.getText().equals("") ){
				JOptionPane.showMessageDialog(null, "信息未填写完整！", null,
						JOptionPane.YES_NO_CANCEL_OPTION);
			}else{
				//修改显示界面到数据准备
				setSteps(0);
				
				ClassifyStatus classifyStatus = new ClassifyStatus();
				classifyStatus.start();
			}	
		}
	}
	
	class ClassifyStatus extends Thread {
		public void run() {
			Setting setting = new Setting();
			SplitWords testSplit = new SplitWords();
			AlgorithmPanel algorithmPanel = new AlgorithmPanel();
			GetAllFilePath getAllFilePath = new GetAllFilePath();
			
			int knn = 0;
			int rocchio = 0;
			int bayes = 0;
			List<String> pathList = new ArrayList<String>();// saving all files'
			ArrayList<ArrayList<String>> randomList = new ArrayList<ArrayList<String>>();
			
			
			knn = algorithmPanel.getKnnCheckBox();
			rocchio = algorithmPanel.getRocchioCheckBox();
			bayes = algorithmPanel.getBayesCheckBox();
			
			/*
			 * 开始语料预处理
			 * 
			*/
			
			pathList = getAllFilePath.getAllFilePath(sourcePathtext, pathList); 
			randomList = getAllFilePath.getRandomFilePath(pathList, randomList);
			
			for( int i = 0; i < randomList.get(0).size(); i++ ){
				File file = new File(randomList.get(0).get(i));
				String mdirsName = file.getParent();
				mdirsName = mdirsName.substring(mdirsName.lastIndexOf("\\"), mdirsName.length());
				File file2 = new File(testStorePathtext + "\\" + mdirsName );
				if( !file2.exists() ) {
					file2.mkdirs();
				}
				setting.copyFile(randomList.get(0).get(i),testStorePathtext + "\\" + mdirsName + "\\" + file.getName());
			}
			
			testSplit.SplitEachFile( randomList.get(0) , "FirstFileXMLFolder", 0);
			System.out.println("testSplit.SplitEachFile( randomList.get(0) , 'FirstFileXMLFolder', 0)完成!");
			testSplit.CreateWordXML("","",Integer.valueOf(trainDFtext));
			System.out.println("testSplit.CreateWordXML('','',Integer.valueOf(trainDFtext))完成!");
			testSplit.CreatePropertyXML(randomList.get(0));
			System.out.println("testSplit.CreatePropertyXML()完成!");
			
			DataPrepare DataPrepare = new DataPrepare();
			DataPrepare.setSteps(0);
			ClassifyPanel ClassifyPanel = new ClassifyPanel();
			ClassifyPanel.setSteps(1);
			 
			/*
			 * 开始分类
			 */
			
			if( knn == 1 ){
				setFlag(1);
				Generate_Test_Vector generate_Test_Vector = new Generate_Test_Vector();
				Generate_Train_Vector TrainVector = new Generate_Train_Vector();
				TrainVector.Create_TrainVector_XML("");
				generate_Test_Vector.SplitTestEachFile(randomList.get(1), storePathtext, Integer.valueOf(testTFtext));
			}
			
			if( rocchio == 1 ){
				setFlag(2);
				RocchioClassify rocchioClassify = new RocchioClassify();
				Generate_Rocchio_Train_Vector trainVector = new Generate_Rocchio_Train_Vector();
				trainVector.Create_TrainVector_XML("");
				rocchioClassify.Rocchio(randomList.get(1), storePathtext, Integer.valueOf(testTFtext));
			}
			
			if( bayes == 1 ){
				setFlag(3);
				NaiveBayes naiveBayes = new NaiveBayes();
				naiveBayes.SplitTestEachFile(randomList.get(1), storePathtext, Integer.valueOf(testTFtext));
			}
			
			
			
			String mes = "";
			if( knn == 1 ){
				mes = "KNN ";
			}
			if( rocchio == 1 ){
				mes += "ROCCHI ";
			}
			if( bayes == 1 ){
				mes += "BAYES ";
			}
			setMassege1(mes);
			setMassege2("分类结束!");
			//修改显示界面到finish			
			ClassifyPanel.setSteps(0);
			FinishPanel finishPanel = new FinishPanel();
			finishPanel.setSteps(1);
		}
	}
	
	// 复制单个文件
	public void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[5555];
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
}

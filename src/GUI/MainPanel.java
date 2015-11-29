package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.jar.JarFile;

import javax.swing.*;

import com.swtdesigner.SwingResourceManager;

import Calculate.Calculate_Evaluate;
import InterfaceMain.SplitWords;
import KNN.Generate_Test_Vector;
import NaiveBayes.NaiveBayes;
import Rocchio.RocchioClassify;

public class MainPanel {
	
	
	public JFrame frame;	
	AlgorithmPanel algorithmPanel = new AlgorithmPanel();
	Setting setting = new Setting();
	DataPrepare dataPrepare = new DataPrepare();
	ClassifyPanel classifyPanel = new ClassifyPanel();
	FinishPanel finishPanel = new FinishPanel();
	
	private final JPanel panel_left = new JPanel();
	private final JLabel label = new JLabel();
	private final JLabel label_2 = new JLabel();
	private final JLabel label_3 = new JLabel();
	private final JLabel label_4 = new JLabel();
	private final JLabel label_5 = new JLabel();
	private final JLabel algorithmLabel = new JLabel();
	private final JLabel algorithmLabel_1 = new JLabel();
	private final JLabel algorithmLabel_2 = new JLabel();
	private final JLabel algorithmLabel_3 = new JLabel();
	private final JLabel algorithmLabel_4 = new JLabel();
	public final JProgressBar progressBar = new JProgressBar();
	
	public PanelChange panelChange = null;
	
	private final JLabel leftbackground = new JLabel();
	public final JLabel massege1 = new JLabel();
	public final JLabel massege2 = new JLabel();
	public final JLabel massege3 = new JLabel();

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			MainPanel window = new MainPanel();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application
	 */
	public MainPanel() {
		jbInit();
	}
	
	/**
	 * Initialize the contents of the frame
	 */
	private void jbInit() {
		
		frame = new JFrame();
		frame.setTitle("Text classification of minority");
		frame.setIconImage(SwingResourceManager.getImage(MainPanel.class, "/Img/BASE.jpg"));
		
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setBounds(100, 100, 600, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(panel_left);
		panel_left.setName("panel_left");
		panel_left.setLayout(null);
		panel_left.setBackground(new Color(102, 102, 204));
		panel_left.setBounds(0, 0, 150, 422);
		
		panel_left.add(label);
		label.setBackground(new Color(255, 0, 0));
		label.setBounds(20, 30, 18, 18);
		label.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_r.gif"));
		
		panel_left.add(label_2);
		label_2.setBackground(new Color(255, 0, 0));
		label_2.setBounds(20, 65, 18, 18);
		label_2.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
		
		panel_left.add(label_3);
		label_3.setBackground(new Color(255, 0, 0));
		label_3.setBounds(20, 100, 18, 18);
		label_3.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
		
		panel_left.add(label_4);
		label_4.setBackground(new Color(255, 0, 0));
		label_4.setBounds(20, 135, 18, 18);
		label_4.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
		
		panel_left.add(label_5);
		label_5.setBackground(new Color(255, 0, 0));
		label_5.setBounds(20, 170, 18, 18);
		label_5.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
		
		panel_left.add(algorithmLabel);
		algorithmLabel.setForeground(new Color(192, 192, 192));
		algorithmLabel.setText(" Algorithm");   //step 1
		algorithmLabel.setBounds(44, 30, 89, 18);
		
		panel_left.add(algorithmLabel_1);
		algorithmLabel_1.setForeground(new Color(192, 192, 192));
		algorithmLabel_1.setText(" Setting");   //step 2
		algorithmLabel_1.setBounds(44, 65, 89, 18);
		
		panel_left.add(algorithmLabel_2);
		algorithmLabel_2.setForeground(new Color(192, 192, 192));
		algorithmLabel_2.setText(" Data prepare");   //step 3
		algorithmLabel_2.setBounds(44, 100, 100, 18);
		
		panel_left.add(algorithmLabel_3);
		algorithmLabel_3.setForeground(new Color(192, 192, 192));
		algorithmLabel_3.setText(" Classify");   //step 4
		algorithmLabel_3.setBounds(44, 135, 89, 18);
		
		panel_left.add(algorithmLabel_4);
		algorithmLabel_4.setForeground(new Color(192, 192, 192));
		algorithmLabel_4.setText(" Finish");   //step 5
		algorithmLabel_4.setBounds(44, 170, 89, 18);
		
		panel_left.add(massege1);
		massege1.setForeground(new Color(192, 192, 192));
		massege1.setFont(new Font("@Œ¢»Ì—≈∫⁄", Font.BOLD, 11));
		massege1.setText("");
		massege1.setBounds(20, 295, 113, 18);
		
		panel_left.add(massege2);
		massege2.setForeground(new Color(192, 192, 192));
		massege2.setFont(new Font("@Œ¢»Ì—≈∫⁄", Font.BOLD, 11));
		massege2.setText("");
		massege2.setBounds(20, 317, 113, 18);
		
		panel_left.add(massege3);
		massege3.setForeground(new Color(192, 192, 192));
		massege3.setFont(new Font("@Œ¢»Ì—≈∫⁄", Font.BOLD, 11));
		massege3.setText("");
		massege3.setBounds(20, 339, 113, 18);
		
		panel_left.add(progressBar);
		progressBar.setBounds(20, 369, 113, 18);
		
		panel_left.add(leftbackground);
		leftbackground.setBounds(0, 0, 150, 422);
		leftbackground.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/leftback.jpg"));
		
		
		
		frame.getContentPane().add(finishPanel);
		frame.getContentPane().add(classifyPanel);
		frame.getContentPane().add(dataPrepare);
		frame.getContentPane().add(setting);
		frame.getContentPane().add(algorithmPanel);
//		panel_right_1.setVisible(false);	
		algorithmPanel.setVisible(true);
		setting.setVisible(false);
		dataPrepare.setVisible(false);
		classifyPanel.setVisible(false);
		finishPanel.setVisible(false);
		
		panelChange = new PanelChange();
		panelChange.start();
	}

	
	class PanelChange extends Thread {
		public void run() {
			
			while(true){
				if( algorithmPanel.getSteps() == 1 ){
					algorithmPanel.setVisible(true);
					setting.setVisible(false);
					dataPrepare.setVisible(false);
					classifyPanel.setVisible(false);
					finishPanel.setVisible(false);
					
					label.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_r.gif"));
					label_2.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_3.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_4.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_5.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
				}
				
				if( setting.getSteps() == 1 ){
					setting.setVisible(true);
					algorithmPanel.setVisible(false);
					dataPrepare.setVisible(false);
					classifyPanel.setVisible(false);
					finishPanel.setVisible(false);
					
					label.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_2.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_r.gif"));
					label_3.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_4.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_5.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
				}
				
				if( dataPrepare.getSteps() == 1 ){
					dataPrepare.setVisible(true);
					algorithmPanel.setVisible(false);
					setting.setVisible(false);
					classifyPanel.setVisible(false);
					finishPanel.setVisible(false);
					
					label.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_2.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_3.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_r.gif"));
					label_4.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_5.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					
					SplitWords splitWords = new SplitWords();
					progressBar.setMinimum(0);
					progressBar.setMaximum(splitWords.getSum());
					progressBar.setValue(splitWords.getCurrent());
					massege1.setText(splitWords.getMassege1());
					massege2.setText(splitWords.getMassege2());
					massege3.setText(splitWords.getMassege3());
				}
				
				if( classifyPanel.getSteps() == 1 ){
					classifyPanel.setVisible(true);
					algorithmPanel.setVisible(false);
					setting.setVisible(false);
					dataPrepare.setVisible(false);
					finishPanel.setVisible(false);
					
					if( setting.getFlag() == 1 ){
						Generate_Test_Vector Generate_Test_Vector = new Generate_Test_Vector();
						progressBar.setMinimum(0);
						progressBar.setMaximum(Generate_Test_Vector.getSum());
						progressBar.setValue(Generate_Test_Vector.getCurrent());
						massege1.setText(Generate_Test_Vector.getMassege1());
						massege2.setText(Generate_Test_Vector.getMassege2());
						massege3.setText(Generate_Test_Vector.getMassege3());
					}else if( setting.getFlag() == 2 ){
						RocchioClassify RocchioClassify = new RocchioClassify();
						progressBar.setMinimum(0);
						progressBar.setMaximum(RocchioClassify.getSum());
						progressBar.setValue(RocchioClassify.getCurrent());
						massege1.setText(RocchioClassify.getMassege1());
						massege2.setText(RocchioClassify.getMassege2());
						massege3.setText(RocchioClassify.getMassege3());
					}else if( setting.getFlag() == 3 ){
						NaiveBayes NaiveBayes = new NaiveBayes();
						progressBar.setMinimum(0);
						progressBar.setMaximum(NaiveBayes.getSum());
						progressBar.setValue(NaiveBayes.getCurrent());
						massege1.setText(NaiveBayes.getMassege1());
						massege2.setText(NaiveBayes.getMassege2());
						massege3.setText(NaiveBayes.getMassege3());
					}
					
					
					label.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_2.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));;
					label_3.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_4.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_r.gif"));
					label_5.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
				}
				
				if( finishPanel.getSteps() == 1 ){

					double[] KNNresult;
					double[] ROCCHIOresult;
					double[] BAYESresult;
					
					Calculate_Evaluate c_Evaluate = new Calculate_Evaluate();
					//º∆À„∆¿π¿
					KNNresult = c_Evaluate.getKnnEvaluate();
					ROCCHIOresult = c_Evaluate.getROCCHIOEvaluate();
					BAYESresult = c_Evaluate.getBAYESEvaluate();
					
					finishPanel.setVisible(true);
					algorithmPanel.setVisible(false);
					setting.setVisible(false);
					dataPrepare.setVisible(false);
					classifyPanel.setVisible(false);
					
					massege1.setText(setting.getMassege1());
					massege2.setText(setting.getMassege2());
					
					finishPanel.changeKnnEvaluate(String.valueOf(KNNresult[0]),String.valueOf(KNNresult[1]),String.valueOf(KNNresult[2]),String.valueOf(KNNresult[3]));
					finishPanel.changeRocchioEvaluate(String.valueOf(ROCCHIOresult[0]),String.valueOf(ROCCHIOresult[1]),String.valueOf(ROCCHIOresult[2]),String.valueOf(ROCCHIOresult[3]));
					finishPanel.changeBayesEvaluate(String.valueOf(BAYESresult[0]),String.valueOf(BAYESresult[1]),String.valueOf(BAYESresult[2]),String.valueOf(BAYESresult[3]));
					
					label.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_2.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_3.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_4.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_w.gif"));
					label_5.setIcon(SwingResourceManager.getIcon(MainPanel.class, "/Img/point_r.gif"));
				}
			}
		}
	}
	
}

package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.swtdesigner.SwingResourceManager;

public class FinishPanel extends JPanel {

	
	private final JLabel FinishLabel = new JLabel();
	private final JLabel mainbackLabel = new JLabel();
	
	
	private final JLabel ResultLabel = new JLabel();
	private final JLabel knnlabel_1 = new JLabel();
	private final JLabel knnlabel_2 = new JLabel();
	private final JLabel knnlabel_3 = new JLabel();
	private final JLabel knnlabel_4 = new JLabel();
	private JLabel knnlabel_1_1 = new JLabel();
	private JLabel knnlabel_2_2 = new JLabel();
	private JLabel knnlabel_3_3 = new JLabel();
	private JLabel knnlabel_4_4 = new JLabel();
	private final JButton finishButton = new JButton();
	private final JButton continueButton = new JButton();
	
	
	private static int steps = 0; 
	
	
	private final JLabel knnLabel = new JLabel();
	private final JLabel rocchioLabel = new JLabel();
	private final JLabel rocchiolabel_1 = new JLabel();
	private final JLabel rocchiolabel_2 = new JLabel();
	private final JLabel rocchiolabel_3 = new JLabel();
	private final JLabel rocchiolabel_4 = new JLabel();
	public JLabel rocchiolabel_1_1 = new JLabel();
	public JLabel rocchiolabel_2_2 = new JLabel();
	public JLabel rocchiolabel_3_3 = new JLabel();
	public JLabel rocchiolabel_4_4 = new JLabel();
	private final JLabel naiveBayesLabel = new JLabel();
	private final JLabel bayeslabel_1 = new JLabel();
	private final JLabel bayeslabel_2 = new JLabel();
	private final JLabel bayeslabel_3 = new JLabel();
	private final JLabel bayeslabel_4 = new JLabel();
	public JLabel bayeslabel_1_1 = new JLabel();
	public JLabel bayeslabel_2_2 = new JLabel();
	public JLabel bayeslabel_3_3 = new JLabel();
	public JLabel bayeslabel_4_4 = new JLabel();
	public void setSteps(int step){
		steps = step;
		
		if( steps == 0 ){
			AlgorithmPanel algorithmPanel = new AlgorithmPanel();
			algorithmPanel.setSteps(1);
		}
	}
	
	public int getSteps(){
		return steps;
	}
	
	public void changeKnnEvaluate(String R, String P, String F, String A){
		this.knnlabel_1_1.setText(R);
		this.knnlabel_2_2.setText(P);
		this.knnlabel_3_3.setText(F);
		this.knnlabel_4_4.setText(A);
	}
	
	public void changeRocchioEvaluate(String R, String P, String F, String A){
		this.rocchiolabel_1_1.setText(R);
		this.rocchiolabel_2_2.setText(P);
		this.rocchiolabel_3_3.setText(F);
		this.rocchiolabel_4_4.setText(A);
	}
	
	public void changeBayesEvaluate(String R, String P, String F, String A){
		this.bayeslabel_1_1.setText(R);
		this.bayeslabel_2_2.setText(P);
		this.bayeslabel_3_3.setText(F);
		this.bayeslabel_4_4.setText(A);
	}
	
	/**
	 * Create the panel
	 */
	public FinishPanel() {
		super();
		try {
			jbInit();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//
	}
	private void jbInit() throws Exception {
		setLayout(null);
		setBackground(new Color(0, 51, 153));
		setBounds(150, 0, 444, 422);
		
		add(FinishLabel);
		FinishLabel.setBounds(0, 0, 444, 85);
		FinishLabel.setOpaque(true);
		FinishLabel.setBackground(new Color(255, 102, 0));
		FinishLabel.setIcon(SwingResourceManager.getIcon(AlgorithmPanel.class, "/Img/background5.jpg"));
		
		add(ResultLabel);
		ResultLabel.setForeground(new Color(128, 0, 0));
		ResultLabel.setFont(new Font("", Font.BOLD, 15));
		ResultLabel.setText("分类结果：");
		ResultLabel.setBounds(20, 100, 80, 25);
		
		add(knnlabel_1);
		knnlabel_1.setFont(new Font("", Font.BOLD, 12));
		knnlabel_1.setText("查全率：");
		knnlabel_1.setBounds(94, 149, 60, 18);
		
		add(knnlabel_2);
		knnlabel_2.setFont(new Font("", Font.BOLD, 12));
		knnlabel_2.setText("查准率：");
		knnlabel_2.setBounds(251, 149, 66, 18);
		
		add(knnlabel_3);
		knnlabel_3.setFont(new Font("", Font.BOLD, 12));
		knnlabel_3.setText("F值：");
		knnlabel_3.setBounds(94, 174, 60, 18);
		
		add(knnlabel_4);
		knnlabel_4.setFont(new Font("", Font.BOLD, 12));
		knnlabel_4.setText("正确率：");
		knnlabel_4.setBounds(251, 174, 52, 18);
		
		add(knnlabel_1_1);
		knnlabel_1_1.setFont(new Font("", Font.BOLD, 12));
		knnlabel_1_1.setText("0%");
		knnlabel_1_1.setBounds(160, 150, 30, 18);
		
		add(knnlabel_2_2);
		knnlabel_2_2.setText("0%");
		knnlabel_2_2.setBounds(318, 149, 66, 18);
		
		add(knnlabel_3_3);
		knnlabel_3_3.setText("0%");
		knnlabel_3_3.setBounds(160, 175, 66, 18);
		
		add(knnlabel_4_4);
		knnlabel_4_4.setText("0%");
		knnlabel_4_4.setBounds(318, 173, 66, 18);
		
		add(finishButton);
		finishButton.addMouseListener(new FinishButtonMouseListener());
		finishButton.setText("Finish");
		finishButton.setBounds(321, 373, 85, 23);
		
		add(continueButton);
		continueButton.addMouseListener(new ContinueButtonMouseListener());
		continueButton.setText("Reclassify");
		continueButton.setBounds(211, 373, 93, 23);
		
		add(knnLabel);
		knnLabel.setText("KNN:");
		knnLabel.setBounds(60, 125, 37, 18);
		
		add(rocchioLabel);
		rocchioLabel.setText("ROCCHIO:");
		rocchioLabel.setBounds(60, 198, 66, 18);
		
		add(rocchiolabel_1);
		rocchiolabel_1.setFont(new Font("Dialog", Font.BOLD, 12));
		rocchiolabel_1.setText("查全率：");
		rocchiolabel_1.setBounds(94, 222, 60, 18);
		
		add(rocchiolabel_2);
		rocchiolabel_2.setFont(new Font("Dialog", Font.BOLD, 12));
		rocchiolabel_2.setText("查准率：");
		rocchiolabel_2.setBounds(251, 222, 60, 18);
		
		add(rocchiolabel_3);
		rocchiolabel_3.setFont(new Font("Dialog", Font.BOLD, 12));
		rocchiolabel_3.setText("F值：");
		rocchiolabel_3.setBounds(94, 249, 60, 18);
		
		add(rocchiolabel_4);
		rocchiolabel_4.setFont(new Font("Dialog", Font.BOLD, 12));
		rocchiolabel_4.setText("正确率：");
		rocchiolabel_4.setBounds(251, 249, 60, 18);
		
		add(rocchiolabel_1_1);
		rocchiolabel_1_1.setFont(new Font("Dialog", Font.BOLD, 12));
		rocchiolabel_1_1.setText("0%");
		rocchiolabel_1_1.setBounds(160, 222, 52, 18);
		
		add(rocchiolabel_2_2);
		rocchiolabel_2_2.setFont(new Font("Dialog", Font.BOLD, 12));
		rocchiolabel_2_2.setText("0%");
		rocchiolabel_2_2.setBounds(318, 222, 52, 18);
		
		add(rocchiolabel_3_3);
		rocchiolabel_3_3.setFont(new Font("Dialog", Font.BOLD, 12));
		rocchiolabel_3_3.setText("0%");
		rocchiolabel_3_3.setBounds(160, 249, 52, 18);
		
		add(rocchiolabel_4_4);
		rocchiolabel_4_4.setFont(new Font("Dialog", Font.BOLD, 12));
		rocchiolabel_4_4.setText("0%");
		rocchiolabel_4_4.setBounds(318, 249, 52, 18);
		
		add(naiveBayesLabel);
		naiveBayesLabel.setText("NAIVE BAYES:");
		naiveBayesLabel.setBounds(60, 273, 94, 18);
		
		add(bayeslabel_1);
		bayeslabel_1.setFont(new Font("Dialog", Font.BOLD, 12));
		bayeslabel_1.setText("查全率：");
		bayeslabel_1.setBounds(94, 298, 60, 18);
		
		add(bayeslabel_2);
		bayeslabel_2.setFont(new Font("Dialog", Font.BOLD, 12));
		bayeslabel_2.setText("查准率：");
		bayeslabel_2.setBounds(251, 298, 60, 18);
		
		add(bayeslabel_3);
		bayeslabel_3.setFont(new Font("Dialog", Font.BOLD, 12));
		bayeslabel_3.setText("F值：");
		bayeslabel_3.setBounds(94, 322, 60, 18);
		
		add(bayeslabel_4);
		bayeslabel_4.setFont(new Font("Dialog", Font.BOLD, 12));
		bayeslabel_4.setText("正确率：");
		bayeslabel_4.setBounds(251, 322, 60, 18);
		
		add(bayeslabel_1_1);
		bayeslabel_1_1.setFont(new Font("Dialog", Font.BOLD, 12));
		bayeslabel_1_1.setText("0%");
		bayeslabel_1_1.setBounds(160, 298, 52, 18);
		
		add(bayeslabel_2_2);
		bayeslabel_2_2.setFont(new Font("Dialog", Font.BOLD, 12));
		bayeslabel_2_2.setText("0%");
		bayeslabel_2_2.setBounds(318, 298, 52, 18);
		
		add(bayeslabel_3_3);
		bayeslabel_3_3.setFont(new Font("Dialog", Font.BOLD, 12));
		bayeslabel_3_3.setText("0%");
		bayeslabel_3_3.setBounds(160, 322, 52, 18);
		
		add(bayeslabel_4_4);
		bayeslabel_4_4.setFont(new Font("Dialog", Font.BOLD, 12));
		bayeslabel_4_4.setText("0%");
		bayeslabel_4_4.setBounds(318, 322, 52, 18);
		
		add(mainbackLabel);
		mainbackLabel.setBounds(0, 89, 444, 333);
		mainbackLabel.setIcon(SwingResourceManager.getIcon(AlgorithmPanel.class, "/Img/mainback.jpg"));
	}
	private class ContinueButtonMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			continueButton_mouseClicked(e);
		}
	}
	private class FinishButtonMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			finishButton_mouseClicked(e);
		}
	}
	protected void continueButton_mouseClicked(MouseEvent e) {
		if( e.getSource().equals(continueButton)){
			File file1 = new File("FirstFileXMLFolder");
			File fileList1[] = file1.listFiles();
			for( int i = 0; i < fileList1.length; i++ ){
				fileList1[i].delete();
			}
			File file2 = new File("KNN_VectorXML.xml");
			file2.delete();
			File file3 = new File("Rocchio_VectorXML.xml");
			file3.delete();
			File file4 = new File("PropertyXML.xml");
			file4.delete();
			File file5 = new File("WordXML.xml");
			file5.delete();
			Setting setting = new Setting();
			setting.setMassege1("");
			setting.setMassege2("");
			setSteps(0);
		}
	}
	protected void finishButton_mouseClicked(MouseEvent e) {
		if( e.getSource().equals(finishButton)){
			File file1 = new File("FirstFileXMLFolder");
			File fileList1[] = file1.listFiles();
			for( int i = 0; i < fileList1.length; i++ ){
				fileList1[i].delete();
			}
			File file2 = new File("KNN_VectorXML.xml");
			file2.delete();
			File file3 = new File("Rocchio_VectorXML.xml");
			file3.delete();
			File file4 = new File("PropertyXML.xml");
			file4.delete();
			File file5 = new File("WordXML.xml");
			file5.delete();
			System.exit(0);
		}
	}

}

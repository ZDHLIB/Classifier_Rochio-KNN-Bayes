package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import com.swtdesigner.SwingResourceManager;

public class AlgorithmPanel extends JPanel {
	
	private final JLabel KnnHeadLabel = new JLabel();
	private final JLabel mainbackLabel = new JLabel();
	private final JCheckBox knnCheckBox = new JCheckBox();
	private final JCheckBox ROCCHIOCheckBox = new JCheckBox();
	private final JCheckBox BAYESCheckBox = new JCheckBox();
	private final JButton Algorithm_next = new JButton();
	private final JButton cancel = new JButton();
	
	private static int knn = 0;
	private static int rocchio = 0;
	private static int bayes = 0;
	private static int steps = 0; //0:disapear, 1:apear
	
	
	public void setCheckBox(){
		if( knnCheckBox.isSelected() ){
			knn = 1;
		} else {
			knn = 0;
		}
		if( ROCCHIOCheckBox.isSelected() ){
			rocchio = 1;
		} else {
			rocchio = 0;
		}
		if( BAYESCheckBox.isSelected() ){
			bayes = 1;
		} else {
			bayes = 0;
		}
		Setting setting = new Setting();
		setting.setSteps(1);
		steps = 0;
	}
	
	public void setSteps(int step){
		steps = step;
	}
	
	public int getKnnCheckBox(){
		return knn;
	}
	
	public int getRocchioCheckBox(){
		return rocchio;
	}
	
	public int getBayesCheckBox(){
		return bayes;
	}
	
	public int getSteps(){
		return steps;
	}
	
	
	/**
	 * Create the panel
	 */
	public AlgorithmPanel() {
		super();
		try {
			jbInit();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//
	}
	private void jbInit() throws Exception {
		
		System.currentTimeMillis();
		setBackground(new Color(0, 51, 153));
		setLayout(null);
		setBounds(150, 0, 444, 422);
		
		add(KnnHeadLabel);
		KnnHeadLabel.setOpaque(true);
		KnnHeadLabel.setBackground(new Color(255, 102, 0));
		KnnHeadLabel.setBounds(0, 0, 444, 85);
		KnnHeadLabel.setIcon(SwingResourceManager.getIcon(AlgorithmPanel.class, "/Img/background1.jpg"));
		
		add(knnCheckBox);
		knnCheckBox.setForeground(new Color(0, 0, 255));
		knnCheckBox.setFont(new Font("", Font.BOLD | Font.ITALIC, 22));
		knnCheckBox.setOpaque(false);
		knnCheckBox.setText("KNN");
		knnCheckBox.setBounds(84, 131, 189, 37);
		
		add(ROCCHIOCheckBox);
		ROCCHIOCheckBox.setForeground(new Color(0, 0, 255));
		ROCCHIOCheckBox.setFont(new Font("@华文琥珀", Font.BOLD, 22));
		ROCCHIOCheckBox.setOpaque(false);
		ROCCHIOCheckBox.setText("ROCCHIO");
		ROCCHIOCheckBox.setBounds(84, 182, 189, 37);
		
		add(BAYESCheckBox);
		BAYESCheckBox.setForeground(new Color(0, 0, 255));
		BAYESCheckBox.setFont(new Font("@华文琥珀", Font.BOLD, 22));
		BAYESCheckBox.setOpaque(false);
		BAYESCheckBox.setText("NAIVE BAYES");
		BAYESCheckBox.setBounds(84, 234, 189, 37);
		
		add(Algorithm_next);
		Algorithm_next.addMouseListener(new Algorithm_nextMouseListener());
		Algorithm_next.setText("Next");
		Algorithm_next.setBounds(321, 373, 85, 23);
		
		add(cancel);
		cancel.addMouseListener(new CancelMouseListener());
		cancel.setText("Cancel");
		cancel.setBounds(211, 373, 85, 23);
		
		add(mainbackLabel);
		mainbackLabel.setBounds(0, 89, 444, 333);
		mainbackLabel.setIcon(SwingResourceManager.getIcon(AlgorithmPanel.class, "/Img/mainback.jpg"));
	}
	private class CancelMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			cancel_mouseClicked(e);
		}
	}
	private class Algorithm_nextMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			algorithm_next_mouseClicked(e);
		}
	}
	
	
	protected void cancel_mouseClicked(MouseEvent e) {
		if( e.getSource().equals( cancel ) ){
			System.exit(0);
		}
	}
	protected void algorithm_next_mouseClicked(MouseEvent e) {
		if( e.getSource().equals( Algorithm_next ) ){
			if( !knnCheckBox.isSelected() &&  !ROCCHIOCheckBox.isSelected() && !BAYESCheckBox.isSelected() ){
				JOptionPane.showMessageDialog(null, "至少选择一种算法！", null,
						JOptionPane.YES_NO_CANCEL_OPTION);
			}else{
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
				
				setCheckBox();
			}
			
		}
	}

}

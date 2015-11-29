package GUI;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.swtdesigner.SwingResourceManager;

public class DataPrepare extends JPanel {


	private final JLabel DataPrepareLabel = new JLabel();
	private final JLabel mainbackLabel = new JLabel();
	
	private static int steps = 0; 
	
	
	public void setSteps(int step){
		steps = step;
	}
	
	public int getSteps(){
		return steps;
	}
	
	/**
	 * Create the panel
	 */
	public DataPrepare() {
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
		
		add(DataPrepareLabel);
		DataPrepareLabel.setBounds(0, 0, 444, 85);
		DataPrepareLabel.setOpaque(true);
		DataPrepareLabel.setBackground(new Color(255, 102, 0));
		DataPrepareLabel.setIcon(SwingResourceManager.getIcon(AlgorithmPanel.class, "/Img/background3.jpg"));
		
		add(mainbackLabel);
		mainbackLabel.setBounds(0, 89, 444, 333);
		mainbackLabel.setIcon(SwingResourceManager.getIcon(AlgorithmPanel.class, "/Img/prepareMainBack.png"));
	}

}

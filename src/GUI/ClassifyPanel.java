package GUI;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.swtdesigner.SwingResourceManager;

public class ClassifyPanel extends JPanel {

	private final JLabel ClassifyLabel = new JLabel();
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
	public ClassifyPanel() {
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
		
		add(ClassifyLabel);
		ClassifyLabel.setBounds(0, 0, 444, 85);
		ClassifyLabel.setOpaque(true);
		ClassifyLabel.setBackground(new Color(255, 102, 0));
		ClassifyLabel.setIcon(SwingResourceManager.getIcon(AlgorithmPanel.class, "/Img/background4.jpg"));
		
		add(mainbackLabel);
		mainbackLabel.setBounds(0, 89, 444, 333);
		mainbackLabel.setIcon(SwingResourceManager.getIcon(AlgorithmPanel.class, "/Img/ClassifyMainback.jpg"));
	}

}

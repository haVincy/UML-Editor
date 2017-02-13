package UMLeditor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import UMLeditor_mode.Mode;
import UMLeditor_mode.createLineMode;
import UMLeditor_mode.createObjMode;
import UMLeditor_mode.selectMode;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar{
	private int ToolNum = 6;
	private Color myColor = new Color(50, 171, 175);
	private JButton holdBtn = null;
	private Canvas canvas;

	public ToolBar() {
		canvas = Canvas.getInstance();   // Canvas is singleton 
		setLayout(new GridLayout(ToolNum, 1, 2, 2));
		this.setBackground(new Color(83, 85, 87));
		
		ImageIcon selectIcon = new ImageIcon("img/select.png");
		ToolBtn selectBtn = new ToolBtn("select", selectIcon, new selectMode());
		add(selectBtn);
		
		ImageIcon associateIcon = new ImageIcon("img/associate.png");
		ToolBtn associateBtn = new ToolBtn("associate", associateIcon, new createLineMode("associate"));
		add(associateBtn);
		
		ImageIcon generalIcon = new ImageIcon("img/general.png");
		ToolBtn generalBtn = new ToolBtn("general", generalIcon, new createLineMode("general"));
		add(generalBtn);
		
		ImageIcon compositeIcon = new ImageIcon("img/composite.png");
		ToolBtn compositeBtn = new ToolBtn("composite", compositeIcon, new createLineMode("composite"));
		add(compositeBtn);
		
		ImageIcon classIcon = new ImageIcon("img/class.png");
		ToolBtn classBtn = new ToolBtn("class", classIcon, new createObjMode("class"));
		add(classBtn);
		
		ImageIcon usecaseIcon = new ImageIcon("img/usecase.png");
		ToolBtn usecaseBtn = new ToolBtn("usecase", usecaseIcon, new createObjMode("usecase"));
		add(usecaseBtn);

	}
	private class ToolBtn extends JButton{
		Mode ToolMode;
		public ToolBtn(String ToolName, ImageIcon icon, Mode ToolMode) {
			this.ToolMode = ToolMode;
			setToolTipText(ToolName);
			setIcon(icon);
			setFocusable(false);
			setBackground(new Color(0, 0, 0));
			setBorderPainted(false);
			setRolloverEnabled(true);
			addActionListener(new toolListener());
		}
		class toolListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(holdBtn != null)
					holdBtn.setBackground(new Color(0, 0, 0));
				holdBtn = (JButton) e.getSource();
				holdBtn.setBackground(myColor);
				canvas.currentMode = ToolMode;
				canvas.setCurrentMode();
				canvas.reset();
				canvas.repaint();
			}
		}
	}
}

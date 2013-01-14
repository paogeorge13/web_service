package grafiko;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class GUI extends JFrame {

	private static GUI instance = null;
	private static JButton quitButton;
	private JLabel deviceLabel;
	private JTabbedPane jtp;
	private PcTab jp1;
	private MobileTab jp2;
	private JComboBox deviceBox;

	/* Create main window*/
	public GUI() {
		/* Create frame with name App*/
		super("Network Radar");
		/* Define frame size*/
		this.setSize(1138, 575);
		/* Terminates when user pushes the X button*/
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/* No resizeable*/
		this.setResizable(false);
		/* Change background color*/
		getContentPane().setBackground(Color.BLACK);
		// Titlebar icon.Specify the correct url path	
		Image icon = Toolkit.getDefaultToolkit().getImage("images/radar.jpg");
		setIconImage(icon);
		/* Style*/
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
//            UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/*    public static synchronized GUI getInstance() {
	 if (instance == null) {
	 instance = new GUI();
	 }
	 return instance;
	 }*/

	/* Add menu bar*/
	public void addMenu() {
		/* Menu Bar*/
		JMenu File = new JMenu("File");
		JMenu Edit = new JMenu("Edit");
		JMenu View = new JMenu("View");
		/* Menu items*/
		JMenuItem newF = new JMenuItem("New");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem saveAs = new JMenuItem("Save As");
		JMenuItem exit = new JMenuItem("Exit");
		/* Add items*/
		File.add(newF);
		File.add(open);
		File.add(new JSeparator());
		File.add(save);
		File.add(saveAs);
		File.add(new JSeparator());
		File.add(exit);
		File.add(new JSeparator());
		/* Add bar*/
		JMenuBar bar = new JMenuBar();
		bar.add(File);
		bar.add(Edit);
		bar.add(View);
		this.setJMenuBar(bar);
		/* All disabled*/
		Edit.setEnabled(false);
		View.setEnabled(false);
		newF.setEnabled(false);
		open.setEnabled(false);
		save.setEnabled(false);
		saveAs.setEnabled(false);
		exit.setEnabled(true);
		/* Exit button*/
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
	}

	/* Add pc and mobile tab*/
	public void addTabs() {
		jtp = new JTabbedPane();
		getContentPane().add(jtp);
		jp1 = new PcTab();
		jp1.customizePCTab();
		jtp.addTab("PC/Laptops", jp1);
		jp2 = new MobileTab();
		jtp.addTab("Mobiles", jp2);
	}
}

/* TODO dokimastiko*/
class Device {

	private String name;

	public Device(String name) {
		this.name = name;
	}

	public String toString() {
		return getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

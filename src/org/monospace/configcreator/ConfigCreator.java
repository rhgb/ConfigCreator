package org.monospace.configcreator;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.UnexpectedException;

public class ConfigCreator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5059373169601854784L;
	/* backend variables */
	private File currentFile;
	private boolean modified;
	private ConfigSet configSet;
	/* frontend variables */
	private JPanel contentPane;
	private JPanel statusBar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigCreator frame = new ConfigCreator();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConfigCreator() {
		/* initialize backend */
		currentFile = null;
		modified = false;
		configSet = new ConfigSet();
		/* initialize UI */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLoad = new JMenuItem("Load...");
		mnFile.add(mntmLoad);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mnFile.add(mntmSaveAs);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		mnFile.add(mntmQuit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		statusBar = new JPanel();
		contentPane.add(statusBar, BorderLayout.SOUTH);
	}
	private void newFile() {
		if (modified) {
			int result = JOptionPane.showConfirmDialog(this, "The current file have not been saved. Do you want to save it?");
			switch (result) {
			case JOptionPane.YES_OPTION:
				saveFile();
				break;
			case JOptionPane.NO_OPTION:
				closeFile();
				break;
			case JOptionPane.CANCEL_OPTION:
				return;
			default:
				throw new UnknownError("Unexpected JOptionPane returns");
			}	
		}
		configSet = new ConfigSet();
		currentFile = null;
		modified = false;
	}
	private void readFile() {
		//TODO
	}
	private void saveFile() {
		//TODO
	}
	private void saveAs() {
		//TODO
	}
	private void closeFile() {
		//TODO
	}
	private void exit() {
		System.exit(0);
	}
}

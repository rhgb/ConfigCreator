package org.monospace.configcreator;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ConfigCreator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5059373169601854784L;
	/* backend variables */
	private File currentFile;
	private boolean modified;
	private ConfigSet configSet;
	private ConfigTemplate template;
	private static final String templateFileName = "C:\\Users\\rhgb\\git\\ConfigCreator\\template.conf";
	/* frontend variables */
	private JPanel contentPane;
	private JPanel statusBar;
	private JFileChooser fileChooser;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
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
		template = new ConfigTemplate();
		try {
			template.parse(new File(templateFileName));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		configSet = new ConfigSet(template);
		
		
		/* initialize UI */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New...");
		mntmNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newFile();
			}
		});
		mnFile.add(mntmNew);
		
		JMenuItem mntmLoad = new JMenuItem("Load...");
		mntmLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openFile();
			}
		});
		mnFile.add(mntmLoad);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		});
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
		
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "Config file (system.conf)";
			}
			@Override
			public boolean accept(File arg0) {
				return (arg0.getName().equals("system.conf") && arg0.isFile()) || arg0.isDirectory();
			}
		});
	}
	private void newFile() {
		closeFile();
	}
	private void openFile() {
		int res = fileChooser.showOpenDialog(this);
		if (res != JFileChooser.APPROVE_OPTION) return;
		File file = fileChooser.getSelectedFile();
		try {
			configSet.loadFromFile(file);
		} catch (IOException | RuntimeException e) {
			e.printStackTrace();
			return;
		}
		modified = false;
		currentFile = file;
	}
	private void saveFile() {
		if (!modified) return;
		if (currentFile == null || !currentFile.canWrite()) {
			saveAs();
		} else {
			try {
				configSet.writeToFile(currentFile);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			modified = false;
		}
	}
	private void saveAs() {
		int res = fileChooser.showSaveDialog(this);
		if (res != JFileChooser.APPROVE_OPTION) return;
		File file = fileChooser.getSelectedFile();
		try {
			configSet.writeToFile(file);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		modified = false;
		currentFile = file;
	}
	private boolean closeFile() {
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
				return false;
			default:
				throw new UnknownError("Unexpected JOptionPane returns");
			}
		}
		configSet.clear();
		currentFile = null;
		modified = false;
		return true;
	}
	private void exit() {
		if (closeFile()) {
			System.exit(0);
		}
	}
}

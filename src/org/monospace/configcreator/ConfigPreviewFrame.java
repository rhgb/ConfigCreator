package org.monospace.configcreator;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import java.awt.BorderLayout;

public class ConfigPreviewFrame extends JFrame {
	private static final long serialVersionUID = -6043751542326053006L;
	private JTextPane previewPane;
	public ConfigPreviewFrame(ConfigModel set) {
		setTitle("Preview");
		setLocationByPlatform(true);
		setSize(400, 400);
		previewPane = new JTextPane();
		previewPane.setEditable(false);
		getContentPane().add(previewPane, BorderLayout.CENTER);
	}
	public void setContent(String content) {
		previewPane.setText(content);
	}
}
